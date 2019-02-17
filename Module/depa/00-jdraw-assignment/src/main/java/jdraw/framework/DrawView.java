/*
 * Copyright (c) 2017 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.framework;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.List;


/**
 * The class DrawView displays a graphic model. It is responsible for the
 * presentation of the model and the forwarding of the user interaction 
 * to its associated controller.
 *
 * @see DrawModel
 * @see DrawContext
 * @see DrawTool
 *
 * @author  Dominik Gruntz &amp; Christoph Denzler
 * @version 2.5
 */
public interface DrawView  {
	
	/** Default width of view. */
	int DEFAULT_WIDTH = 300;
	/** Default height of view. */
	int DEFAULT_HEIGHT = 200;

	/**
	 * Returns the graphic model of this view.
	 * 
	 * @return the graphic model presented by this view
	 */
	DrawModel getModel();

	/**
	 * Sets the controller to be used by this view.
	 * 
	 * @param controller the controller to be used by this view
	 */
	void setDrawContext(DrawContext controller);

	/**
	 * Returns the controller which was assigned to this view.
	 * 
	 * @return the controller associated with this view
	 */
	DrawContext getDrawContext();

	/**
	 * Sets the current point constrainer. If the constrainer passes is
	 * <code>null</code> then a previously registered constrainer is removed.
	 * 
	 * @param p point constrainer to be used
	 */
	void setConstrainer(PointConstrainer p);

	/**
	 * Gets the current grid setting.
	 * 
	 * @return the current point constrainer
	 */
	PointConstrainer getConstrainer();

	/**
	 * Gets the handle (if any) that is at the mouse position. Although any
	 * information about the mouse's position is already contained in the
	 * <code>MouseEvent e</code>, a pair of coordinates is given to this
	 * procedure. <code>x</code> and <code>y</code> represent the constrained
	 * position of <code>e.getX()</code> and <code>e.getY()</code>.
	 * 
	 * @param x the constrained x-coordinate of the mouse.
	 * @param y the constrained y-coordinate of the mouse.
	 * @param e the mouse event
	 * @return the handle at the mouse position or <code>null</code> if no
	 *         handle is selected.
	 */
	FigureHandle getHandle(int x, int y, MouseEvent e);

	/**
	 * Returns a list which contains the currently selected figures. The figures
	 * are ordered in the order in which they were selected by the user. If no
	 * figures are selected, then an empty list is returned.
	 * 
	 * Changes applied to this list do not change the selection state of 
	 * any figure. For changing the selection see methods 
	 * {@link #addToSelection(Figure) addToSelection}, 
	 * {@link #removeFromSelection(Figure) removeFromSelction} and
	 * {@link #clearSelection() clearSelection}.
	 * 
	 * @return list of selected figures
	 */
	List<Figure> getSelection();

	/**
	 * Clears the selection and removes all selection markers.
	 */
	void clearSelection();

	/**
	 * Adds a figure to the selected figures. The figure is added
	 * at the end of the list of selected figures.
	 * 
	 * @param f the figure to add to the selection.
	 */
	void addToSelection(Figure f);

	/**
	 * Removes a figure from the selected figures.
	 * 
	 * @param f the figure to remove from the selection.
	 */
	void removeFromSelection(Figure f);

	/**
	 * Sets a selection rubber band to be displayed by the draw view.
	 * If the <code>rect</code> parameter passed is <code>null</code>
	 * then new rubber band is displayed.
	 * 
	 * @param rect the selection rubber band rectangle.
	 */
	void setSelectionRubberBand(Rectangle rect);

	/**
	 * Draws the graphic model. The paint method iterates over all figures in
	 * the graphic model and calls their paint method.
	 * 
	 * @param g the graphics context to use when painting.
	 */
	void paint(Graphics g);

	/**
	 * Repaints this view.
	 */
	void repaint();

	/**
	 * Sets the cursor of the DrawingView.
	 * 
	 * @param c the cursor to set.
	 */
	void setCursor(Cursor c);

	/**
	 * Close is called when the window is closed. Should be used to remove
	 * resources, e.g. registered listeners.
	 */
	void close();
}
