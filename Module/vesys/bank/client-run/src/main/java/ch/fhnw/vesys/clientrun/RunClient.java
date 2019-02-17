/*
 * Copyright (c) 2000-2017 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package ch.fhnw.vesys.clientrun;

import ch.fhnw.vesys.client.Client;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.StringTokenizer;
import java.util.Vector;

public class RunClient extends JFrame {

    private static JFrame frame;

    public static void main(String args[]) {
        frame = new RunClient();
        frame.pack();
        frame.setVisible(true);
    }

    private RunClient() {
        super("Choose Bank Driver");
        Vector<String> vect = new Vector<>();

        try {
            URL url = this.getClass().getResource("/Servers.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(url.getFile())));
            String line = in.readLine();
            while (line != null) {
                vect.add(line);
                line = in.readLine();
            }
            in.close();
        } catch (Exception e) {
        }

        final JList<String> list = new JList<>(vect);
        list.setVisibleRowCount(Math.max(10, Math.min(30, vect.size())));

        add(new JScrollPane(list));
        JButton b = new JButton("start");
        add(b, "South");
        b.addActionListener(e -> startBank((String) list.getSelectedValue()));
    }

    private void startBank(String arg) {
        frame.setVisible(false);
        frame.dispose();

        StringTokenizer tok = new StringTokenizer(arg);
        String[] args = new String[tok.countTokens()];
        for (int i = 0; i < args.length; i++) {
            args[i] = tok.nextToken();
        }
        Client.main(args);
    }
}
