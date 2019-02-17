/*
 * Copyright (c) 2017 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.framework;

/**
 * The class DrawModel represents the model of a drawing, i.e.
 * all figures stored in a graphic. Every draw view refers to a
 * model.
 *
 * @see DrawView
 *
 * @author  Dominik Gruntz &amp; Christoph Denzler
 * @version 2.5
 */
public interface DrawModel {

	/**
	 * Adds a new figure to the draw model.
	 * 
	 * @param f figure to be added to draw model.
	 */
	void addFigure(Figure f);

	/**
	 * Removes a given figure from the draw model.
	 * 
	 * @param f figure to be removed from draw model.
	 */
	void removeFigure(Figure f);
	
	/**
	 * Remove all figures from the draw model. This method is used when loading a drawing from a file
	 * in order to clear the old drawing before loading the new one.
	 */
	void removeAllFigures();

	/**
	 * Returns an iterator which can be used to iterate over all figures of a
	 * draw model. The iterator ordering is the order in which elements were
	 * inserted into the model (insertion-order). The order is interpreted by
	 * the view as "back-to-front".
	 * 
	 * @return iterator to iterate over the draw model
	 */
	Iterable<Figure> getFigures();

	/**
	 * Adds the specified model listener to receive model events from this draw
	 * model.
	 * 
	 * @param listener the draw model listener.
	 * @see DrawModelListener
	 */
	void addModelChangeListener(DrawModelListener listener);

	/**
	 * Removes the specified model listener so that it no longer receives model
	 * events from this draw model. This method performs no function, nor does
	 * it throw an exception, if the listener specified by the argument was not
	 * previously added to this figure.
	 * 
	 * @param listener the draw model listener.
	 * @see DrawModelListener
	 */
	void removeModelChangeListener(DrawModelListener listener);

	/**
	 * Returns the draw command handler provided by this model.
	 * 
	 * @see DrawCommandHandler
	 * @return the draw command handler used.
	 */
	DrawCommandHandler getDrawCommandHandler();

	/**
	 * Sets the index of a given figure. The order of the other figures in the
	 * model remains unchanged.
	 * 
	 * If the figure is moved to a new place, then the model sends a
	 * <code>DRAWING_CHANGED</code> event to all registered model listeners.
	 * 
	 * @param f
	 *            the figure whose index has to be set
	 * @param index
	 *            the position at which the new figure should appear. The other
	 *            figures are moved away.
	 * 
	 * @throws IllegalArgumentException
	 *             if the figure is not contained in the model.
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of range (
	 *             <tt>index &lt; 0 || index &ge; size()</tt>) where size() is
	 *             the number of figures contained in the model.
	 */
	void setFigureIndex(Figure f, int index) throws IllegalArgumentException, IndexOutOfBoundsException;

}
