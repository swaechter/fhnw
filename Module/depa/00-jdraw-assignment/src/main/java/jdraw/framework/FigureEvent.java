/*
 * Copyright (c) 2017 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.framework;

import java.util.EventObject;

/**
 * An event which indicates that a figure event occurred in a figure. This may
 * be a change of position or a change of the figure's size.
 * 
 * @see FigureListener
 * @author Dominik Gruntz &amp; Christoph Denzler
 * @version 2.5
 */
@SuppressWarnings("serial")
public class FigureEvent extends EventObject {

	/**
	 * Constructs a FigureEvent object with the specified figure.
	 * 
	 * @param source figure which changed
	 */
	public FigureEvent(Figure source) {
		super(source);
	}

	/**
	 * Returns the figure which changed.
	 * 
	 * @return changed figure
	 */
	public Figure getFigure() {
		return (Figure)getSource();
	}
}
