/*
 * Copyright (c) 2017 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.framework;

/**
 * This interface provides access to figure parts for those figures 
 * which consist of several parts, as e.g. for group figures.
 *
 * @author  Dominik Gruntz &amp; Christoph Denzler
 * @version 2.5
 */
public interface FigureGroup {
	
	/**
	 * Returns the parts of a figure which consists of several parts.
	 * 
	 * @return parts of a figure
	 */
	Iterable<Figure> getFigureParts();
}
