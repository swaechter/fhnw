package ch.fhnw.edu.rental.gui.about;

import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.net.URI;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

/**
 * Mouse click handler class.
 * 
 */
class MouseClickHandler extends MouseAdapter {

    /**
     * web page not opened.
     */
    private static final String ABOUT_ERR_MSG = "Error attempting to launch web browser.";

    /**
     * mouse click handler method.
     * 
     * @param event mouse event
     */
    public void mouseClicked(MouseEvent event) {
        homepageTextLabelMouseClicked(event);
    }

    /**
     * Handle click on homepage link. This opens the default web browser as an external application.
     * 
     * @param evt The mouse event triggering this action.
     */
    private void homepageTextLabelMouseClicked(java.awt.event.MouseEvent evt) {
        try {
            Desktop.getDesktop().browse(new URI("http://web.fhnw.ch/plattformen/swc/"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, ABOUT_ERR_MSG + ":\n" + e.getLocalizedMessage());
        }
    }

}
