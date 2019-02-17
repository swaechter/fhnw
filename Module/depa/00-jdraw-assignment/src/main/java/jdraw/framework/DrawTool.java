/*
 * Copyright (c) 2017 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.framework;

import java.awt.Cursor;
import java.awt.event.MouseEvent;

import javax.swing.Icon;

/**
 * A tool defines a mode of the drawing view, intended to be set over a tool
 * bar. An example of a tool is the construction of new figures. Whenever a new
 * tool is activated or deactivated, the corresponding methods are called. This
 * can be used to e.g. adjust the mouse cursor depending on the current tool.
 * <P>
 * All input events targeted to the drawing view are forwarded to its current
 * tool.
 * 
 * @see Figure
 * @author Dominik Gruntz &amp; Christoph Denzler
 * @version 2.5
 */
public interface DrawTool {

	/**
	 * Activates the tool for the given view. This method is called whenever the
	 * user switches to this tool. Use this method to reinitialize a tool.
	 */
	void activate();

	/**
	 * Deactivates the tool. This method is called whenever the user switches to
	 * another tool. Use this method to do some clean-up when the tool is
	 * switched. Subclasses should always call {@code super.deactivate()}.
	 */
	void deactivate();

	/**
	 * Handles mouse down events in the drawing view.
	 * 
	 * @param x x coordinate of mouse position
	 * @param y y coordinate of mouse position
	 * @param e mouse event, contains state of modifiers
	 */
	void mouseDown(int x, int y, MouseEvent e);

	/**
	 * Handles mouse drag events in the drawing view.
	 * 
	 * @param x x coordinate of mouse position
	 * @param y y coordinate of mouse position
	 * @param e mouse event, contains state of modifiers
	 */
	void mouseDrag(int x, int y, MouseEvent e);

	/**
	 * Handles mouse up in the drawing view.
	 * 
	 * @param x x coordinate of mouse position
	 * @param y y coordinate of mouse position
	 * @param e mouse event, contains state of modifiers
	 */
	void mouseUp(int x, int y, MouseEvent e);

	/**
	 * Returns the cursor to be used by the draw view if this draw tool is set.
	 * 
	 * @return cursor for this draw tool.
	 */
	Cursor getCursor();

	/**
	 * Obtain an icon for this tool. This icon will be displayed in the toolbar.
	 * 
	 * @return an icon representing the tool.
	 */
	Icon getIcon();

	/**
	 * Obtain the name of this tool. This name will be used for menu entries.
	 * 
	 * @return the tool's name.
	 */
	String getName();
}
