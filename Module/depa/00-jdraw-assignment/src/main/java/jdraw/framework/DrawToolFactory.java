/*
 * Copyright (c) 2017 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.framework;


/**
 * A DrawTool factory creates an instance of a draw tool and returns an icon
 * which can be used in the tool palette of the graphics editor.
 *
 * @see DrawTool
 *
 * @author  Dominik Gruntz &amp; Christoph Denzler
 * @version 2.5
 */
public interface DrawToolFactory {

	/**
	 * Returns the name of the created tool. If this value is not null, then
	 * it will overwrite the name of the created tool
	 * @see DrawTool#getName()
	 * 
	 * @return draw tool name
	 */
	String getName();

	/**
	 * Sets the name to be used for the created draw tool. This name overwrites
	 * the name of the draw tool implementation.
	 * 
	 * @param name draw tool name
	 */
	void setName(String name);

	/**
	 * Returns the path of the draw tool icon. If this value is not null, then
	 * it will overwrite the path of the created tool.
	 * @see DrawTool#getIcon()
	 * 
	 * @return draw tool name
	 */
	String getIconName();

	/**
	 * Sets the path of the draw tool icon to be used for the created draw tool.
	 * This name overwrites the name of the draw tool implementation.
	 * 
	 * @param name draw tool icon name
	 */
	void setIconName(String name);

	/**
	 * Returns a draw tool operating on the given controller.
	 * 
	 * @param context draw context
	 * @return draw tool
	 */
	DrawTool createTool(DrawContext context);
}
