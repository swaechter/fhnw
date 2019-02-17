package ch.fhnw.edu.rental.gui.about;

import java.awt.Color;

import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

/**
 * constructs the product info part.
 */
public class ProductInfoPanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = -5851803100597615354L;

    /**
     * constructs the product and version information panel.
     * 
     * @param bColor background color
     */
    public ProductInfoPanel(Color bColor) {

        JLabel productLabel = new JLabel();
        JLabel vendorLabel = new JLabel();
        JLabel authorsLabel = new JLabel();
        JLabel homepageLabel = new JLabel();
        JLabel versionNrLabel = new JLabel();
        JLabel vendorTextLabel = new JLabel();
        JLabel authorsTextLabel = new JLabel();
        JLabel homepageTextLabel = new JLabel();

        setBackground(bColor);

        productLabel.setFont(new java.awt.Font("Tahoma", 1, 11));
        productLabel.setText("Product Version:");

        vendorLabel.setFont(new java.awt.Font("Tahoma", 1, 11));
        vendorLabel.setText("Vendor:");

        authorsLabel.setFont(new java.awt.Font("Tahoma", 1, 11));
        authorsLabel.setText("Authors:");

        homepageLabel.setFont(new java.awt.Font("Tahoma", 1, 11));
        homepageLabel.setText("Homepage:");

        versionNrLabel.setText("2.0");

        vendorTextLabel.setText("University of Applied Sciences Northwestern Switzerland (2016)");

        authorsTextLabel.setText("Martin Kropp | Christoph Denzler");

        homepageTextLabel.setText("http://web.fhnw.ch/plattformen/swc/");
        homepageTextLabel.addMouseListener(new MouseClickHandler());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);

        layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(
                layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                                layout.createParallelGroup(Alignment.LEADING)
                                        .addGroup(
                                                layout.createSequentialGroup()
                                                        .addComponent(productLabel)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 313,
                                                                Short.MAX_VALUE).addComponent(versionNrLabel)
                                                        .addContainerGap())
                                        .addGroup(
                                                layout.createSequentialGroup()
                                                        .addComponent(vendorLabel)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 108,
                                                                Short.MAX_VALUE).addComponent(vendorTextLabel)
                                                        .addContainerGap())
                                        .addGroup(
                                                layout.createSequentialGroup()
                                                        .addComponent(authorsLabel)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 115,
                                                                Short.MAX_VALUE).addComponent(authorsTextLabel)
                                                        .addContainerGap())
                                        .addGroup(
                                                layout.createSequentialGroup()
                                                        .addComponent(homepageLabel)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 258,
                                                                Short.MAX_VALUE).addComponent(homepageTextLabel)
                                                        .addContainerGap()))));

        layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(
                layout.createSequentialGroup()
                        .addGroup(
                                layout.createParallelGroup(Alignment.BASELINE).addComponent(productLabel)
                                        .addComponent(versionNrLabel))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(
                                layout.createParallelGroup(Alignment.BASELINE).addComponent(vendorLabel)
                                        .addComponent(vendorTextLabel))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(
                                layout.createParallelGroup(Alignment.BASELINE).addComponent(authorsLabel)
                                        .addComponent(authorsTextLabel))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(
                                layout.createParallelGroup(Alignment.BASELINE).addComponent(homepageLabel)
                                        .addComponent(homepageTextLabel))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE)));

    }

}
