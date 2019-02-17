/*
 * Copyright (c) 2017 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.framework;


/**
 * The interface DrawContext represents the context which contains and 
 * presents the draw view. It contains methods to access the contained
 * view and a convenience method to access the associated model.
 * 
 * DrawContext provides methods to communicate with the context, e.g. 
 * in order to display status messages and to add Menu Actions and
 * tool factories.
 * 
 * @see DrawView
 * @see DrawModel
 *
 * @author  Dominik Gruntz &amp; Christoph Denzler
 * @version 2.5
 */
public interface DrawContext {

	/**
	 * Returns the context's drawing view.
	 * 
	 * @return the draw view controlled by this context
	 */
	DrawView getView();

	/**
	 * Returns the draw model presented by this context's view. Returns the same
	 * result as getView().getModel().
	 * 
	 * @return the draw model presented by this context's view.
	 */
	DrawModel getModel();
	
	/**
	 * Shows a status message in the context's user interface.
	 * 
	 * @param msg the status message to be displayed
	 */
	void showStatusText(String msg);

	/**
	 * Adds a menu in the editor's user interface.
	 * 
	 * @param menu the menu to be added
	 */
	void addMenu(javax.swing.JMenu menu);

	/**
	 * Removes a menu in the editor's user interface.
	 * 
	 * @param menu the menu to be removed
	 */
	void removeMenu(javax.swing.JMenu menu);

	/**
	 * Adds a draw tool to the editor's capabilities. An editor has a list of
	 * tools that are available to the user. This list must guarantee that any
	 * tool is contained only once in the list.
	 * 
	 * @param tool a new draw tool to add to the editor.
	 */
	void addTool(DrawTool tool);

	/**
	 * Returns the currently active tool.
	 * 
	 * @return active tool
	 */
	DrawTool getTool();

	/**
	 * Sets the tool to be used.
	 * 
	 * @param tool tool to be used by the draw view
	 */
	void setTool(DrawTool tool);

	/**
	 * Sets the default tool (selection and moving of figures).
	 */
	void setDefaultTool();

	/**
	 * Make the view visible.
	 */
	void showView();

}
