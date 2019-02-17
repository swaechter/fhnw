/*
 * Copyright (c) 2017 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.framework;

/**
 * The interface DrawCommandHandler defines the handler which allows to
 * access the command history provided by a draw model. The list of
 * commands can be iterated through using methods undo and redo.
 *
 * @see DrawCommandHandler
 *
 * @author  Dominik Gruntz &amp; Christoph Denzler
 * @version 2.5
 */

public interface DrawCommandHandler {

	/**
	 * Adds a new command to the command list at the current position. The
	 * added command is not executed by this method.
	 * Commands which were stored behind the current position are discarded.
	 * @param cmd draw command which is added to command list
	 */
	void addCommand(DrawCommand cmd);

	/**
	 * Undoes the last command in the command list. This command can be
	 * redone with operation redo unless new commands are added to the
	 * command list.
	 * @see #redo
	 */
	void undo();

	/**
	 * Redoes the next command in the command list.
	 * @see #undo
	 */
	void redo();

	/**
	 * Indicates whether an undo operation is possible.
	 * @return whether undo is possible
	 * @see #undo
	 */
	boolean undoPossible();

	/**
	 * Indicates whether a redo operation is possible.
	 * @return whether redo is possible
	 * @see #redo
	 */
	boolean redoPossible();

	/**
	 * Opens a script. Commands added with method addCommand after a script has
	 * been opened are collected until endScript is called. These command are
	 * then combined to a macro command.
	 * @see #endScript
	 */
	void beginScript();

	/**
	 * Closes a script. All commands added since the last open beginScript call
	 * are combined to a macro command.
	 * @see #beginScript
	 */
	void endScript();

	/**
	 * Clears the command list.
	 */
	void clearHistory();
}

