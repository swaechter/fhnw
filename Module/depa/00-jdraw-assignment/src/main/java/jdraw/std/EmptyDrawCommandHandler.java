/*
 * Copyright (c) 2017 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.std;

import jdraw.framework.DrawCommand;
import jdraw.framework.DrawCommandHandler;

/**
 * Provides an empty command handler. This class provides an empty dummy implementation of the draw command
 * handler. It enables the application to start up correctly and to behave meaningful, but with the limitation
 * that it does not provide any undo/redo behavior. 
 * @author Christoph. Denzler
 *
 */
public class EmptyDrawCommandHandler implements DrawCommandHandler {

	@Override
	public void addCommand(DrawCommand cmd) { /* do nothing. */ }
	
	@Override
	public void undo() { /* do nothing. */ }

	@Override
	public void redo() { /* do nothing. */ }

	@Override
	public boolean undoPossible() { return false; }

	@Override
	public boolean redoPossible() { return false; }

	@Override
	public void beginScript() { /* do nothing. */ }

	@Override
	public void endScript() { /* do nothing. */ }

	@Override
	public void clearHistory() { /* do nothing. */ }
}
