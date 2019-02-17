/*
 * Copyright (c) 2017 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.framework;

import java.util.EventObject;

/**
 * An event which indicates that a draw model has changed. This event is fired
 * if new figures are added to a model or if figures are removed from a model.
 * The event is also fired if one figure in a draw model changes (e.g. its size
 * or its position).
 * 
 * @see FigureListener
 * @author Dominik Gruntz &amp; Christoph Denzler
 * @version 2.5
 */

@SuppressWarnings("serial")
public class DrawModelEvent extends EventObject {
	
	/** This enum describes the type of event. */
	public enum Type {
		/**
		 * Indicates that a figure was added to the draw model.
		 */
		FIGURE_ADDED,

		/**
		 * Indicates that a figure was removed from the draw model.
		 */
		FIGURE_REMOVED,

		/**
		 * Indicates that the position or the size of a figure was changed in
		 * the draw model.
		 */
		FIGURE_CHANGED,
		
		/**
		 * This event indicates that the whole drawing was cleared, i.e.\ all
		 * figures were removed at once.
		 */
		DRAWING_CLEARED,

		/**
		 * This event indicates that more than one figure has changed, e.g.\
		 * that the order of the figures has changed. The view has to redraw the
		 * whole scene. However, no figure was added or removed.
		 */
		DRAWING_CHANGED
	}

	/**
	 * The affected figure upon which the event is reporting. If the event type
	 * is either DRAWING_CLEARED or DRAWING_CHANGED, then this field is
	 * <code>null</code>.
	 */
	private Figure figure;

	/**
	 * The actual type of the event that is being reported. See the enum
	 * declared in this class for details.
	 */
	private Type type;

	/**
	 * Constructs a DrawModelEvent object with the specified model.
	 * 
	 * @param source model which changed
	 * @param figure the affected figure
	 * @param type the event type
	 */
	public DrawModelEvent(DrawModel source, Figure figure, Type type) {
		super(source);
		this.figure = figure;
		this.type = type;
	}

	/**
	 * Returns the draw model which changed.
	 * 
	 * @return changed model
	 */
	public DrawModel getModel() {
		return (DrawModel)getSource();
	}

	/**
	 * Returns the figure which initiated the event. In case of a
	 * <code>DRAWING_CLEARED</code> or <code>DRAWING_CHANGED</code> event this
	 * method returns <code>null</code>.
	 * 
	 * @return added, removed or changed figure (depending on event type)
	 */
	public Figure getFigure() {
		return figure;
	}

	/**
	 * Returns the event type.
	 * 
	 * @return type of event
	 */
	public Type getType() {
		return type;
	}
}
