/*
 * Copyright (c) 2000-2016 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package ch.fhnw.vesys.client;

import ch.fhnw.vesys.client.tests.BankTest;
import ch.fhnw.vesys.shared.api.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


public class BankGUI extends JFrame {

    private BankDriver driver;
    private Bank bank;

    private JComboBox<String> accountcombo = new JComboBox<>();
    private Map<String, Account> accounts = new HashMap<>();

    private JTextField fld_owner = new JTextField();
    private JTextField fld_balance = new JTextField();

    private JButton btn_refresh = new JButton("Refresh");
    private JButton btn_deposit = new JButton("Deposit Money");
    private JButton btn_withdraw = new JButton("Withdraw Money");
    private JButton btn_transfer = new JButton("Transfer Money");

    private JMenuItem item_new = new JMenuItem("New Account...");
    private JMenuItem item_close = new JMenuItem("Close Account");
    private JMenuItem item_exit = new JMenuItem("Exit");
    private JMenuItem item_about = new JMenuItem("About");

    private List<BankTest> tests = new LinkedList<>();
    private Map<BankTest, JMenuItem> testMenuItems = new HashMap<>();

    private boolean ignoreItemChanges = false;

    private BankTest loadTest(String name) {
        try {
            return (BankTest) Class.forName(name).newInstance();
        } catch (InstantiationException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        } catch (ClassNotFoundException e) {
            return null;
        }
    }


    public BankGUI(BankDriver server) {
        this.driver = server;
        this.bank = server.getBank();

        if (server instanceof BankDriver2) {
            final AtomicBoolean refreshRegistered = new AtomicBoolean(false);
            try {
                ((BankDriver2) server).registerUpdateHandler(new BankDriver2.UpdateHandler() {
                    @Override
                    public void accountChanged(String number) {
                        if (refreshRegistered.compareAndSet(false, true)) {
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    refreshRegistered.set(false);
                                    refreshDialog();
                                }
                            });
                        }
                    }
                });
            } catch (IOException e1) {
                throw new RuntimeException(e1);
            }
        }

        setTitle("ClientBank Application");
        setBackground(Color.lightGray);

        BankTest test;
        test = loadTest("ch.fhnw.vesys.client.tests.EfficiencyTestDS");
        if (test != null) {
            tests.add(test);
        }
        test = loadTest("ch.fhnw.vesys.client.tests.EfficiencyTestCONPR");
        if (test != null) {
            tests.add(test);
        }
        test = loadTest("ch.fhnw.vesys.client.tests.WarmUp");
        if (test != null) {
            tests.add(test);
        }
        test = loadTest("ch.fhnw.vesys.client.tests.ThreadingTest");
        if (test != null) {
            tests.add(test);
        }
        test = loadTest("ch.fhnw.vesys.client.tests.FunctionalityTest");
        if (test != null) {
            tests.add(test);
        }
        test = loadTest("ch.fhnw.vesys.client.tests.TransferTest");
        if (test != null) {
            tests.add(test);
        }
        test = loadTest("ch.fhnw.vesys.client.tests.ConcurrentReads");
        if (test != null) {
            tests.add(test);
        }

        // define menus
        JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);

        JMenu menu_file = new JMenu("File");
        menubar.add(menu_file);
        menu_file.add(item_new);
        menu_file.add(item_close);
        menu_file.addSeparator();
        menu_file.add(item_exit);

        JMenu menu_test = new JMenu("Test");
        menubar.add(menu_test);

        for (BankTest t : tests) {
            final BankTest tt = t;
            JMenuItem m = new JMenuItem(t.getName());
            testMenuItems.put(t, m);
            menu_test.add(m);
            m.addActionListener(e -> {
                try {
                    tt.runTests(BankGUI.this, bank, currentAccountNumber());
                    refreshDialog();
                } catch (Exception ex) {
                    error(ex);
                }
            });
        }

        JMenu menu_help = new JMenu("Help");
        menubar.add(menu_help);
        menu_help.add(item_about);

        item_new.addActionListener(e -> addAccount());
        item_close.addActionListener(e -> closeAccount());
        item_exit.addActionListener(e -> exit());
        item_about.addActionListener(e -> about());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                refreshDialog();
            }
        });

        accountcombo.addItemListener(e -> {
            if (ignoreItemChanges) return;
            if (e.getStateChange() == ItemEvent.SELECTED)
                updateCustomerInfo();
        });

        // create layout

        setResizable(false);

        JPanel center = new JPanel(new GridLayout(3, 2, 5, 5));
        center.add(new JLabel("Account Nr: ", SwingConstants.RIGHT));
        center.add(accountcombo);
        center.add(new JLabel("Owner: ", SwingConstants.RIGHT));
        center.add(fld_owner);
        center.add(new JLabel("Balance: ", SwingConstants.RIGHT));
        center.add(fld_balance);

        // set text fields read only
        fld_owner.setEditable(false);
        fld_balance.setEditable(false);

        JPanel east = new JPanel(new GridLayout(3, 1, 5, 5));
        east.add(btn_deposit);
        east.add(btn_withdraw);
        east.add(btn_transfer);


        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.add(new JLabel(""), BorderLayout.NORTH);
        p.add(center, BorderLayout.CENTER);
        p.add(east, BorderLayout.EAST);
        if (!(driver instanceof BankDriver2)) {
            p.add(btn_refresh, BorderLayout.SOUTH);
        } else {
            p.add(new JLabel(""), BorderLayout.SOUTH);
        }
        add(p);

        // Add ActionListeners
        btn_refresh.addActionListener(e -> refreshDialog());
        btn_deposit.addActionListener(e -> deposit());
        btn_withdraw.addActionListener(e -> withdraw());
        btn_transfer.addActionListener(e -> transfer());

        Dimension d = accountcombo.getPreferredSize();
        d.setSize(Math.max(d.getWidth(), 130), d.getHeight());
        accountcombo.setPreferredSize(d);

        refreshDialog();
    }

    public String currentAccountNumber() {
        return (String) accountcombo.getSelectedItem();
    }

    public void addAccount() {
        AddAccountDialog addaccount = new AddAccountDialog(this, "Add Account");

        Point loc = getLocation();
        addaccount.setLocation(loc.x + 50, loc.y + 50);
        addaccount.setModal(true);
        addaccount.setVisible(true);

        if (!addaccount.canceled()) {
            String number = null;
            try {
                number = bank.createAccount(addaccount.getOwnerName());
            } catch (Exception e) {
                error(e);
            }

            if (number == null) {
                JOptionPane.showMessageDialog(this, "Account could not be created",
                    "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    Account acc = bank.getAccount(number);
                    accounts.put(number, acc);

                    String str = addaccount.getBalance().trim();
                    double amount;
                    if (str.equals("")) amount = 0;
                    else amount = Double.parseDouble(str);
                    acc.deposit(amount);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Illegal Format",
                        "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(this, "Illegal Argument",
                        "Error", JOptionPane.ERROR_MESSAGE);
                } catch (InactiveException e) {
                    JOptionPane.showMessageDialog(this, "Account is inactive",
                        "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception e) {
                    error(e);
                }
                ignoreItemChanges = true;
                accountcombo.addItem(number);
                accountcombo.setSelectedItem(number);
                ignoreItemChanges = false;
                refreshDialog();
            }
        }
    }

    public void closeAccount() {
        String number = currentAccountNumber();
        if (number != null) {
            int res = JOptionPane.showConfirmDialog(this, "Really close account " + number + "?",
                "Confirm closing", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (res == 0) {
                try {
                    boolean done = bank.closeAccount(number);
                    if (done) {
                        refreshDialog();
                    } else {
                        JOptionPane.showMessageDialog(this, "Account could not be closed",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    error(e);
                }
            }
        }
    }

    public void deposit() {
        String number = currentAccountNumber();
        if (number != null) {
            String s = JOptionPane.showInputDialog(this, "Enter amount to deposit:",
                "Deposit Money", JOptionPane.QUESTION_MESSAGE);
            if (s != null) {
                try {
                    double amount = Double.parseDouble(s);
                    Account a = accounts.get(number);
                    a.deposit(amount);
                    fld_balance.setText(currencyFormat(a.getBalance()));
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Illegal Value",
                        "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(this, "Illegal Argument",
                        "Error", JOptionPane.ERROR_MESSAGE);
                } catch (InactiveException e) {
                    JOptionPane.showMessageDialog(this, "Account is inactive",
                        "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception e) {
                    error(e);
                }
            }
        }
    }

    public void withdraw() {
        String number = currentAccountNumber();
        if (number != null) {
            String s = JOptionPane.showInputDialog(this, "Enter amount to draw:", "Draw Money",
                JOptionPane.QUESTION_MESSAGE);
            if (s != null) {
                try {
                    double amount = Double.parseDouble(s);
                    Account a = accounts.get(number);
                    a.withdraw(amount);
                    fld_balance.setText(currencyFormat(a.getBalance()));
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Illegal Value",
                        "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(this, "Illegal Argument",
                        "Error", JOptionPane.ERROR_MESSAGE);
                } catch (InactiveException e) {
                    JOptionPane.showMessageDialog(this, "Account is inactive",
                        "Error", JOptionPane.ERROR_MESSAGE);
                } catch (OverdrawException e) {
                    JOptionPane.showMessageDialog(this, "Account must not be overdrawn",
                        "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception e) {
                    error(e);
                }
            }
        }
    }


    public void transfer() {
        String number = currentAccountNumber();
        if (number != null) {
            try {
                Set<String> s = new HashSet<>(accounts.keySet());
                s.remove(number);

                TransferDialog trans = new TransferDialog(this, "Transfer Money", number, s);
                Point loc = getLocation();
                trans.setLocation(loc.x + 50, loc.y + 50);
                trans.setModal(true);
                trans.setVisible(true);

                if (!trans.canceled()) {
                    if (number.equals(trans.getAccountNumber())) {
                        JOptionPane.showMessageDialog(this, "Both Accounts are the same!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        try {
                            double amount = Double.parseDouble(trans.getBalance());
                            Account from = accounts.get(number);
                            Account to = accounts.get(trans.getAccountNumber());
                            bank.transfer(from, to, amount);

                            // after transfer adjust value of displayed account
                            fld_balance.setText(currencyFormat(from.getBalance()));

                            JOptionPane.showMessageDialog(this, "Transfer successfull",
                                "Information", JOptionPane.INFORMATION_MESSAGE);
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(this, "Illegal Balance",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        } catch (IllegalArgumentException e) {
                            JOptionPane.showMessageDialog(this, "Illegal Argument",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        } catch (InactiveException e) {
                            JOptionPane.showMessageDialog(this, "At least one account is inactive",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        } catch (OverdrawException e) {
                            JOptionPane.showMessageDialog(this, "Account must not be overdrawn",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            } catch (Exception e) {
                error(e);
            }
        }
    }

    public void exit() {
        try {
            driver.disconnect();
        } catch (IOException e) {
            // TODO what to do with IOException upon disconnection
            e.printStackTrace();
        }
        System.exit(0);
    }

    private void refreshDialog() {
        String nr = currentAccountNumber();
        accountcombo.removeAllItems();
        if (bank != null) {
            try {
                Set<String> s = bank.getAccountNumbers();
                ArrayList<String> accnumbers = new ArrayList<>(s);
                Collections.sort(accnumbers);
                ignoreItemChanges = true;
                for (String item : accnumbers) {
                    accountcombo.addItem(item);
                    if (item.equals(nr)) accountcombo.setSelectedItem(item);
                }
                ignoreItemChanges = false;

                // clean up local accounts map
                for (String key : s) {
                    if (!accounts.containsKey(key)) {
                        accounts.put(key, bank.getAccount(key));
                    }
                }
                Iterator<String> it = accounts.keySet().iterator();
                while (it.hasNext()) {
                    if (!s.contains(it.next())) it.remove();
                }

                int size = s.size();
                btn_deposit.setEnabled(size > 0);
                btn_withdraw.setEnabled(size > 0);
                btn_transfer.setEnabled(size > 1);
                item_close.setEnabled(size > 0);

                for (BankTest t : tests) {
                    JMenuItem m = testMenuItems.get(t);
                    m.setEnabled(t.isEnabled(size));
                }

                updateCustomerInfo();
            } catch (Exception e) {
                error(e);
            }
        }
    }

    private void updateCustomerInfo() {
        String nr = currentAccountNumber();
        try {
            if (nr != null) {
                Account a = accounts.get(nr);
                if (a != null) {
                    fld_owner.setText(a.getOwner());
                    fld_balance.setText(currencyFormat(a.getBalance()));
                } else {
                    JOptionPane.showMessageDialog(this, "Account not found",
                        "Error", JOptionPane.ERROR_MESSAGE);
                    refreshDialog();
                }
            } else {
                fld_owner.setText("");
                fld_balance.setText("");
            }
        } catch (Exception e) {
            error(e);
        }
    }

    public String currencyFormat(double val) {
        return NumberFormat.getCurrencyInstance().format(val);
    }

    public void error(Exception e) {
        JDialog dlg = new ErrorBox(this, e);
        dlg.setModal(true);
        dlg.setVisible(true);
    }


    public void about() {
        AboutBox dlg = new AboutBox(this);
        Dimension dlgSize = dlg.getPreferredSize();
        Dimension frmSize = getSize();
        Point loc = getLocation();
        dlg.setLocation(
            (frmSize.width - dlgSize.width) / 2 + loc.x,
            (frmSize.height - dlgSize.height) / 2 + loc.y);
        dlg.setModal(true);
        dlg.setVisible(true);
    }

    static class ErrorBox extends JDialog {
        public ErrorBox(Frame parent, Exception e) {
            super(parent);
            setTitle("Exception");
            setResizable(true);

            //JTextField msg1 = new JTextField();
            //msg1.setText(e.getMessage());

            JTextArea trace = new JTextArea(10, 50);
            java.io.StringWriter buf = new java.io.StringWriter();
            java.io.PrintWriter wr = new java.io.PrintWriter(buf);
            e.printStackTrace(wr);
            trace.setText(buf.toString());
            trace.setCaretPosition(0);
            trace.setEditable(false);

            JScrollPane msg = new JScrollPane(trace);

            JButton ok = new JButton("OK");

            ok.addActionListener(ev -> dispose());

            //getContentPane().add(msg1,   BorderLayout.NORTH);
            getContentPane().add(msg, BorderLayout.CENTER);
            getContentPane().add(ok, BorderLayout.SOUTH);
            getRootPane().setDefaultButton(ok);
            ok.requestFocus();
            pack();
        }
    }


    static class AboutBox extends JDialog {

        public AboutBox(Frame parent) {
            super(parent);
            setTitle("About Bank Client");
            setResizable(false);

            JPanel p_text = new JPanel(new GridLayout(0, 1));
            p_text.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
            p_text.add(new JLabel("Distributed Systems", SwingConstants.CENTER));
            p_text.add(new JLabel("Bank Client", SwingConstants.CENTER));
            p_text.add(new JLabel("", SwingConstants.CENTER));
            p_text.add(new JLabel("(c) D. Gruntz, 2001-2017", SwingConstants.CENTER));

            JButton ok = new JButton("OK");
            ok.addActionListener(e -> dispose());

            getContentPane().add(p_text, BorderLayout.CENTER);
            getContentPane().add(ok, BorderLayout.SOUTH);
            pack();
        }
    }

    static class AddAccountDialog extends JDialog {
        private JTextField ownerfield = new JTextField(12);
        private JTextField balancefield = new JTextField(12);

        private boolean canceled = true;

        AddAccountDialog(Frame owner, String title) {

            super(owner, title);

            // Create Layout
            JButton btn_ok = new JButton("Ok");
            JButton btn_cancel = new JButton("Cancel");
            JPanel p = new JPanel(new GridLayout(3, 2, 10, 10));
            p.add(new JLabel("Owner:", JLabel.RIGHT));
            p.add(ownerfield);
            p.add(new JLabel("Balance:", JLabel.RIGHT));
            p.add(balancefield);
            p.add(btn_ok);
            p.add(btn_cancel);

            getContentPane().add(p);

            btn_ok.setDefaultCapable(true);
            getRootPane().setDefaultButton(btn_ok);

            // Add ActionListeners
            btn_ok.addActionListener(e -> {
                canceled = false;
                setVisible(false);
            });
            btn_cancel.addActionListener(e -> {
                canceled = true;
                setVisible(false);
            });
            pack();
        }

        public boolean canceled() {
            return canceled;
        }

        public String getOwnerName() {
            return ownerfield.getText();
        }

        public String getBalance() {
            return balancefield.getText();
        }
    }

    static class TransferDialog extends JDialog {
        private JTextField balancefield = new JTextField(12);
        private JComboBox<String> accountcombo;

        private boolean canceled = true;

        TransferDialog(Frame owner, String title, String account, Set<String> accounts) {
            super(owner, title);

            JButton btn_ok = new JButton("Ok");
            JButton btn_cancel = new JButton("Cancel");
            ArrayList<String> accnumbers = new ArrayList<>(accounts);
            Collections.sort(accnumbers);
            accountcombo = new JComboBox<>(accnumbers.toArray(new String[]{}));

            // Create Layout
            JPanel p = new JPanel(new GridLayout(4, 2, 10, 10));
            p.add(new JLabel("From Account:", JLabel.RIGHT));
            p.add(new JLabel(account));
            p.add(new JLabel("To Account:", JLabel.RIGHT));
            p.add(accountcombo);
            p.add(new JLabel("Amount:", JLabel.RIGHT));
            p.add(balancefield);
            p.add(btn_ok);
            p.add(btn_cancel);

            getContentPane().add(p);
            btn_ok.setDefaultCapable(true);
            getRootPane().setDefaultButton(btn_ok);

            // Add ActionListeners
            btn_ok.addActionListener(e -> {
                canceled = false;
                setVisible(false);
            });
            btn_cancel.addActionListener(e -> {
                canceled = true;
                setVisible(false);
            });
            pack();
        }

        public boolean canceled() {
            return canceled;
        }

        public String getAccountNumber() {
            return (String) accountcombo.getSelectedItem();
        }

        public String getBalance() {
            return balancefield.getText();
        }
    }
}
