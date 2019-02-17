/*
 * Copyright (c) 2017 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.framework;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.List;

/**
 * Base interface for all figures implemented in the graphic editor.
 * Every Figure-type has to implement this interface.
 *
 * @author  Dominik Gruntz &amp; Christoph Denzler
 * @version 2.5
 */
public interface Figure extends Serializable, Cloneable {

	/**
	 * draw is called when the figure has to be drawn.
	 * 
	 * @param g Graphics object on which figure has to be drawn.
	 * @see java.awt.Graphics
	 */
	void draw(Graphics g);

	/**
	 * Moves the figure. The figure has to adjust its coordinates when this
	 * method is called, and registered figure listeners have to be notified.
	 * 
	 * @param dx move distance in x direction (argument in pixels)
	 * @param dy move distance in y direction (argument in pixels)
	 * @see #addFigureListener
	 */
	void move(int dx, int dy);

	/**
	 * Tests whether the mouse coordinates are contained in the figure. contains
	 * is called when the mouse is pressed in the grafic in order to decide
	 * which figure has to be selected.
	 * 
	 * @param x x-coordinate of mouse position
	 * @param y y-coordinate of mouse position
	 * @return <tt>true</tt>, if coordinates are contained in the figure,
	 *         <tt>false</tt> otherwise
	 */
	boolean contains(int x, int y);

	/**
	 * Changes the bounds of the figure. The figure has to adjust its size and
	 * position when this method is called, and registered figure listeners have
	 * to be notified.
	 * 
	 * @param origin the new origin
	 * @param corner the new corner
	 * @see #addFigureListener
	 */
	void setBounds(Point origin, Point corner);

	/**
	 * Returns the bounds of a figure. The bounds of a figure is a rectangle
	 * which contains the figure.
	 * 
	 * @return bounds of the figure
	 */
	Rectangle getBounds();

	/**
	 * Returns a list of handles. Handles are used to manipulate a figure. If
	 * the figure does not support handles, <tt>null</tt> may be returned as
	 * result.
	 * 
	 * @return list of handles (may be null if handles are not supported)
	 * @see FigureHandle
	 */
	List<FigureHandle> getHandles();

	/**
	 * Adds the specified figure listener to receive figure events from this
	 * figure. If listener is null, no exception is thrown and no action is
	 * performed.
	 * 
	 * @param listener the figure listener.
	 * @see FigureListener
	 */
	void addFigureListener(FigureListener listener);

	/**
	 * Removes the specified figure listener so that it no longer receives
	 * figure events from this figure. This method performs no function, nor
	 * does it throw an exception, if the listener specified by the argument was
	 * not previously added to this figure. If listener is null, no exception is
	 * thrown and no action is performed.
	 * 
	 * @param listener the figure listener.
	 * @see FigureListener
	 */
	void removeFigureListener(FigureListener listener);

	/**
	 * Returns a clone of this figure.
	 * 
	 * @return clone of figure
	 */
	Figure clone();
}
