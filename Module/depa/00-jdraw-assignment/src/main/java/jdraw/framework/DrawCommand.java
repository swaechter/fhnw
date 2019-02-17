/*
 * Copyright (c) 2017 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.framework;

import java.io.Serializable;

/**
 * The interface DrawCommand defines the functionality provided by
 * commands which can be stored in the command history.
 *
 * @see DrawCommandHandler
 *
 * @author  Dominik Gruntz &amp; Christoph Denzler
 * @version 2.5
 */

public interface DrawCommand extends Serializable {
	
	/**
	 * Executes a command.
	 */
	void redo();

	/**
	 * Undoes the action performed by execute. 
	 */
	void undo();
}

