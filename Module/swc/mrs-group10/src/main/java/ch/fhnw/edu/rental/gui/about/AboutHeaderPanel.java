package ch.fhnw.edu.rental.gui.about;

import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

/**
 * The header information for the about dialog.
 * 
 * @author martin.kropp
 * 
 */
public class AboutHeaderPanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * constructs the header with the information.
     * 
     * @param bColor background color
     */
    public AboutHeaderPanel(Color bColor) {

        JLabel logoLabel = new JLabel();
        JLabel headingLabel = new JLabel();
        JLabel descriptionLabelAbout = new JLabel();
        JLabel headingTwoLabel = new JLabel();

        setBackground(bColor);

        // NOI18N
        logoLabel.setIcon(new ImageIcon(getClass().getResource("/img/logo.png")));
        logoLabel.setToolTipText("University of Applied Sciences Northwestern Switzerland");
        headingLabel.setFont(new java.awt.Font("Tahoma", 1, 16));
        headingLabel.setText("Software Construction Lab");
        descriptionLabelAbout.setText("<html>A movie rental application for the " + "software construction lab at the "
                + "University of Applied Sciences " + "Northwestern Switzerland (2016).");

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
                layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(
                                                layout.createSequentialGroup()
                                                        .addComponent(descriptionLabelAbout, GroupLayout.DEFAULT_SIZE,
                                                                421, Short.MAX_VALUE).addContainerGap())
                                        .addGroup(
                                                layout.createSequentialGroup()
                                                        .addComponent(headingTwoLabel, GroupLayout.DEFAULT_SIZE, 61,
                                                                Short.MAX_VALUE).addGap(370, 370, 370))
                                        .addComponent(headingLabel)
                                        .addComponent(logoLabel, GroupLayout.Alignment.TRAILING))));

        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
                layout.createSequentialGroup()
                        .addGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(
                                                layout.createSequentialGroup().addContainerGap()
                                                        .addComponent(headingLabel).addGap(18, 18, 18)
                                                        .addComponent(headingTwoLabel)).addComponent(logoLabel))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(descriptionLabelAbout)
                        .addGap(18, 18, 18)));

    }

}
