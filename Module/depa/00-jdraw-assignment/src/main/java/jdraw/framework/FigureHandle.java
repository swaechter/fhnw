/*
 * Copyright (c) 2017 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.framework;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

/**
 * Handles are used to change a figure by direct manipulation. Handles know
 * their owning figure and they provide methods to ask the handle's bounds and
 * to track changes.
 * 
 * @see Figure
 * 
 * @author Dominik Gruntz &amp; Christoph Denzler
 * @version 2.5
 */
public interface FigureHandle {

	/**
	 * Gets the handle's owner.
	 * 
	 * @return the figure which owns the handle
	 */
	Figure getOwner();

	/**
	 * Returns the location of this handle. The result depends on the location
	 * of the owning figure.
	 * 
	 * @return location of this handle
	 */
	Point getLocation();

	/**
	 * Draws this handle.
	 * 
	 * @param g the graphics context to use for painting.
	 */
	void draw(Graphics g);

	/**
	 * Returns a curser which should be displayed when the mouse is over the
	 * handle. Signals the type of operation which can be performed using this
	 * handle.
	 * <P>
	 * A default implementation may return Cursor.getDefaultCursor().
	 * 
	 * @return handle's Cursor
	 */
	Cursor getCursor();

	/**
	 * Tests if a point is contained in the handle.
	 * 
	 * @param x x-coordinate of mouse position
	 * @param y y-coordinate of mouse position
	 * @return <tt>true</tt>, if coordinates are contained in the figure,
	 *         <tt>false</tt> otherwise
	 */
	boolean contains(int x, int y);

	/**
	 * Tracks the start of an interaction. Usually, the position where an
	 * interaction starts is stored.
	 * 
	 * @param x the x position where the interaction started
	 * @param y the y position where the interaction started
	 * @param e the mouse event which initiated the start interaction
	 * @param v the view in which the interaction is performed
	 */
	void startInteraction(int x, int y, MouseEvent e, DrawView v);

	/**
	 * Tracks a step of a started interaction.
	 * 
	 * @param x the current x position
	 * @param y the current y position
	 * @param e the mouse event which initiated the drag interaction
	 * @param v the view in which the interaction is performed
	 */
	void dragInteraction(int x, int y, MouseEvent e, DrawView v);

	/**
	 * Tracks the end of a running interaction.
	 * 
	 * @param x the current x position
	 * @param y the current y position
	 * @param e the mouse event which stoped the start interaction
	 * @param v the view in which the interaction is performed
	 */
	void stopInteraction(int x, int y, MouseEvent e, DrawView v);
}
