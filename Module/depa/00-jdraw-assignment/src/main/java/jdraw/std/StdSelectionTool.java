/*
 * Copyright (c) 2017 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.std;

import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import jdraw.framework.DrawContext;
import jdraw.framework.DrawTool;
import jdraw.framework.DrawView;
import jdraw.framework.Figure;
import jdraw.framework.FigureHandle;

/**
 * The default tool is used for figure selection and general manipulation of one or more figures.
 * @author Christoph Denzler
 */
public class StdSelectionTool implements DrawTool {

	/** the image resource path. */
	private static final String IMAGES = "/images/";

	/** the view this tool operates on.*/
	private DrawView view;
	/** the context in which this tool is used. */
	private DrawContext context;

	/** stores the original (unconstrained) mouse position. */
	private int originalX, originalY;
	/** helper variables. */
	private int tempX,  tempY;
	/** indicates whether the tool is in selection mode, ie. whether a selection is in progress. */
	private boolean selMode = false;
	/** Selection start when dragging mouse. */
	private int sx0, sy0; 
	/** Current position of mouse when dragging mouse for selection. */
	private int sx1, sy1;
	/** figure that was last selected. */
	private Figure lastSelectedFigure;
	/** handle that is currently used. */
	private FigureHandle currentHandle;
	/** the rubber band during selection. */
	//private Rectangle selRectangle;

	/** This tool enables selection behavior in the given view.
	 * 
	 * @param aView a view this new tool shall operate on.
	 * @param aContext a context in which this selection tool operates.
	 */
	public StdSelectionTool(DrawView aView, DrawContext aContext) {
		view = aView;
		context = aContext;
	}

	@Override
	public void activate() {
		context.showStatusText("Selection mode");
	}

	@Override
	public void deactivate() {
	   context.showStatusText("");
	}

	/**
	 * Is the given position near an already selected figure?
	 * @param x x-coordinate of point to check
	 * @param y y-coordinate of point to check
	 * @return whether the checked point is near an already selected figure.
	 */
	private boolean isNearSelected(int x, int y) {
		boolean result = false;
		for (Figure f : view.getModel().getFigures()) {
			if (view.getSelection().contains(f) && f.contains(x, y)) {
				result = true;
				break;
			}
		}
		return result;
	}

	@Override
	public Cursor getCursor() {
		if (currentHandle != null) {
			return currentHandle.getCursor();
		} else {
			return Cursor.getDefaultCursor();
		}
	}

	@Override
	public void mouseDown(int constrainedX, int constrainedY, MouseEvent e) {
		originalX = e.getX();
		originalY = e.getY();
		tempX = constrainedX;
		tempY = constrainedY;

		// 0. check, whether a handle is selected
		lastSelectedFigure = null;
		currentHandle = view.getHandle(originalX, originalY, e);
		if (currentHandle != null) {
			currentHandle.startInteraction(constrainedX, constrainedY, e, view);
			return;
		}

		// 1. check, whether mouse position is near by an already
		//    selected figure; in this case keep selection
		if (!isNearSelected(originalX, originalY)) {

			// 2. if click is outside of existing selection then
			//    deselect all figures - except if shift is down
			//    (modifier used to extend selection)
			if (!e.isShiftDown()) {
				view.clearSelection();
			}

			// 3. look for new figures (which are not already
			//    selected) and select them. Only one figure.
			List<Figure> figures = new LinkedList<Figure>();
			for (Figure f : view.getModel().getFigures()) {
				figures.add(0, f);
			}
			for (Figure f : figures) {
				if (f.contains(originalX, originalY) && !view.getSelection().contains(f)) {
					view.addToSelection(f);
					lastSelectedFigure = f;
					break;
				}	        		
			}

			// 4. if dragging mouse for spanning a selection, remember starting position (sx0, sy0) and initialize
			//    current position (sx1, sy1).
			if (lastSelectedFigure == null && !e.isShiftDown()) {
				sx0 = originalX; sy0 = originalY;
				sx1 = originalX; sy1 = originalY;
				selMode = true;
			}
		}
		view.repaint();
	}

	/**	
	 * Makes Rectangles with negative width and/or height non-empty. According to the API specification, a
	 * Rectangle is empty if it has a width or a height that is smaller or equals 0. This method adjusts the
	 * Dimension of a Rectangle such that it becomes non-empty 
	 * 
	 * @param r a potentially empty Rectangle
	 * @return a non-empty Rectangle with the same Dimension as the input parameter. <b>Note:</b>The returned
	 * reference refers to the same Rectangle as the input parameter.
	 */
	private Rectangle makePositiveSize(Rectangle r) {
		if (r.width < 0) {
			r.width = -r.width;
			r.x -= r.width; 
		}
		if (r.height < 0) {
			r.height = -r.height;
			r.y -= r.height;
		}
		return r;
	}

	/** 
	 * Checks whether the outer rectangle fully contains the inner one.
	 * 
	 * @param outer rectangle supposed to fully contain the inner rectangle.
	 * @param inner rectangle supposed to be fully surrounded by outer rectangle
	 * @return whether outer fully encloses inner.
	 */
	private boolean contains(Rectangle outer, Rectangle inner) {
		makePositiveSize(inner);
		if (inner.height == 0) {
			return inner.y > outer.y && inner.y <= outer.y + outer.height 
			&& inner.x >= outer.x && (inner.x + inner.width) <= outer.x + outer.width;
		}
		if (inner.width == 0) {
			return inner.x > outer.x && inner.x <= outer.x + outer.width
			&& inner.y >= outer.y && (inner.y + inner.height) <= outer.y + outer.height;
		}
		return outer.contains(inner);
	}
	
	/**
	 * Minimum of two ints.
	 * @param x first int
	 * @param y second int
	 * @return smaller value of x and y
	 */
	private static int min(int x, int y) {
		return x < y ? x : y;
	}
	
	/**
	 * Absolute value of an integer.
	 * @param x integer to abs.
	 * @return abs value.
	 */
	private static int abs(int x) {
		return x < 0 ? -x : x;
	}

	@Override
	public void mouseDrag(int i, int j, java.awt.event.MouseEvent e) {
		if (currentHandle != null) {
			currentHandle.dragInteraction(i, j, e, view);
			return;
		}

		if (selMode) {
			sx1 = e.getX(); sy1 = e.getY();
			Set<Figure> sel = new HashSet<Figure>();
			Rectangle selRectangle = 
				new Rectangle(min(sx0, sx1), min(sy0, sy1), abs(sx1 - sx0), abs(sy1 - sy0));
							  
			for (Figure f : view.getModel().getFigures()) {
				if (contains(selRectangle, f.getBounds())) {
					sel.add(f);					
				}
			}
			view.setSelectionRubberBand(selRectangle);
			view.clearSelection();
			for (Figure f : sel) {
				view.addToSelection(f);
			}
			view.repaint();
			return;
		}

		int k = i - tempX;
		int l = j - tempY;

		for (Figure f : view.getSelection()) {
			f.move(k, l);
			view.getModel().getDrawCommandHandler().addCommand(new MoveCommand(f, k, l));			
		}

		tempX = i;
		tempY = j;
		view.repaint();
	}

	@Override
	public void mouseUp(int i, int j, MouseEvent e) {
		if (currentHandle != null) {
			currentHandle.stopInteraction(i, j, e, view);
			currentHandle = null;
			return;
		}

		if  (selMode) {
			selMode = false;
			view.setSelectionRubberBand(null);
			view.repaint();
		}

		if (e.isShiftDown() && e.getX() == originalX && e.getY() == originalY)	{
			for (Figure f : view.getModel().getFigures()) {
				if (f.contains(originalX, originalY)) {
					if (view.getSelection().contains(f) && f != lastSelectedFigure) {
						view.removeFromSelection(f);
					}
					view.repaint();
					break;
				}					
			}
		}
	}

	@Override
	public Icon getIcon() {
		return new ImageIcon(getClass().getResource(IMAGES + "selection.png"));
	}

	@Override
	public String getName() {
		return "Selection";
	}
}
