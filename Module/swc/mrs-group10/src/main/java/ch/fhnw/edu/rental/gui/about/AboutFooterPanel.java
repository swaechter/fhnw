package ch.fhnw.edu.rental.gui.about;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The footer information for the about dialog.
 * 
 */
public class AboutFooterPanel extends JPanel {
    /**
     * serial version ID.
     */
    private static final long serialVersionUID = -710058712214586895L;

    /**
     * reference to the containing dialog.
     */
    private JDialog parent;

    /**
     * constructs the header with the information.
     * 
     * @param parent parent window
     * @param bColor background color
     */
    public AboutFooterPanel(JDialog parent, Color bColor) {

        this.parent = parent;

        JButton cancelAboutButton = new JButton();
        JLabel copyright = new JLabel();

        copyright.setText("Copyright (c) 2016-" + currentYear());

        cancelAboutButton.setText("Close");
        cancelAboutButton.setName("CloseButton");
        cancelAboutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                System.out.println("close click");
                System.err.println("close click");
                cancelAboutButtonActionPerformed(evt);
            }
        });

        setBackground(bColor);

        setLayout(new BorderLayout(40, 30));
        add(copyright, BorderLayout.LINE_START);
        add(cancelAboutButton, BorderLayout.LINE_END);

    }

    /**
     * Get rid of the about dialog.
     * 
     * @param evt The action event triggering disposal.
     */
    private void cancelAboutButtonActionPerformed(java.awt.event.ActionEvent evt) {
        closePanelContainer();
    }

    /**
     * @return the current year as a 4 digit string.
     */
    private String currentYear() {
        return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    }

    /**
     * close the containing window.
     */
    private void closePanelContainer() {
        parent.setVisible(false);
        parent.setEnabled(false);
    }

}
