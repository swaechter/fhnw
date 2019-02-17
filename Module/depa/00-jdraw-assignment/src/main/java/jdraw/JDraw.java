/*
 * Copyright (c) 2017 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw;

import jdraw.framework.DrawContext;

import org.apache.log4j.BasicConfigurator;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * The class JDraw is the main class of the graphic editor. Its main method
 * accesses an implementation of JDrawDriver and invokes the show method. This
 * method creates a new JFrame and initializes it. 
 * 
 * Changes in the configuration of the main window (e.g. new menus, tools etc.)
 * have to be added in the class which implements the interface JDrawDriver. 
 * 
 * The name of the class to be used is configured in file jdraw-context.xml
 * <P>
 * The application can be started with the command
 * 
 * <PRE>
 * java jdraw.JDraw
 * </PRE>
 * 
 * @see jdraw.framework.DrawView
 * @author Dominik Gruntz
 * @version 2.1, 12.03.2006
 */

public final class JDraw {

	/** Default xml configuration file for Spring. */
	private static final String DEFAULT_CONTEXT = "jdraw-context.xml";

	/** Selected xml configuration file for Spring. */
	private static String springContext = DEFAULT_CONTEXT;

	/** Application context of Spring. */
	private static ClassPathXmlApplicationContext ctx;
	
	/**
	 * Starts the JDraw application. Usage: <br>
	 * <code>jdraw [config]</code> where config is an XML file that the Spring
	 * framework uses for its setup.
	 * 
	 * @param args any command line argument. See usage above.
	 */
	public static void main(final String[] args) {
		BasicConfigurator.configure(); 	// configure log4j according to a
										// log4j.properties file found in
										// classpath

		if (args.length > 0) {
			springContext = args[0];
		}

		DrawContext drawContext = getContext();
		drawContext.showView();
	}

	/**
	 * Private constructor prevents the instantiation of this class.
	 */
	private JDraw() {
		// prevent instantiation.
	}

	/**
	 * Get an interface which represents the context.
	 * 
	 * @return a DrawContext implementation
	 */
	public static DrawContext getContext() {
		if (ctx == null) {
			ctx = new ClassPathXmlApplicationContext(springContext);
		}
		return (DrawContext) ctx.getBean("drawContext");
	}

}
