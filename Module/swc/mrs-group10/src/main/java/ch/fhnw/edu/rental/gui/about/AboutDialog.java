package ch.fhnw.edu.rental.gui.about;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import javax.swing.JDialog;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

/**
 * Display and handle About dialog for MovieRentalSystem.
 * 
 * @author Christoph Denzler
 * 
 */
public class AboutDialog extends JDialog {

    /**
     * 
     */
    private static final long serialVersionUID = -8300151445262974771L;

    /**
     * Create a new About dialog.
     */
    public AboutDialog() {

        setTitle("About ...");
        setMinimumSize(new Dimension(460, 265));
        setResizable(false);
        setBackground(Color.WHITE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                aboutDialogWindowClosing(evt);
            }
        });
        setModal(true);
        setBackground(Color.WHITE);
        setName("AboutDialog");

        getContentPane().setBackground(null);
        Container contentPane = getContentPane();
        contentPane.add(new AboutHeaderPanel(Color.WHITE), BorderLayout.NORTH);
        contentPane.add(new ProductInfoPanel(Color.WHITE), BorderLayout.CENTER);
        contentPane.add(new AboutFooterPanel(this, Color.WHITE), BorderLayout.SOUTH);

    }

    /**
     * Close About dialog when user closed its window (instead of clicking the close button).
     * 
     * @param evt The window event indication the closing of the about dialog window.
     */
    private void aboutDialogWindowClosing(WindowEvent evt) {
        setVisible(false);
        dispose();
    }

}
