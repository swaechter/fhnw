/*
 * Copyright (c) 2017 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.std;

import java.awt.AWTKeyStroke;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import jdraw.JDraw;
import jdraw.framework.DrawContext;
import jdraw.framework.DrawModel;
import jdraw.framework.DrawTool;
import jdraw.framework.DrawToolFactory;
import jdraw.framework.DrawView;

/**
 * Standard implementation of interface DrawContext.
 * This implementation uses a JFrame as container element. It provides common initialization and behaviour for all
 * implementations based on a JFrame.
 * 
 * @see DrawView
 * @author Dominik Gruntz & Christoph Denzler
 * @version 2.6, 24.09.09
 */
public abstract class AbstractContext extends JFrame implements DrawContext {

	/** Default width of view. */
	private static final int DEFAULT_WIDTH = 300;
	/** Default height of view. */
	private static final int DEFAULT_HEIGHT = 200;

	
	/** The view contains the drawing shown in this editor. */
	private DrawView view;
	
	/** This field is used to assert that the initGUI method is called exactly once. */
	private boolean guiInitialized = false;

	/** list of the figure factories. */
	private List<DrawToolFactory> toolFactories = new LinkedList<>();
	
	/** width of this window in pixels. */
	private int width = DEFAULT_WIDTH;

	/** height of this window in pixels. */
	private int height = DEFAULT_HEIGHT;

	/** global counter of displayed windows. */
	private static int windowCounter = 0;

	/** number of this window. */
	private int windowNr;

	/** the status bar represented by a text field. */
	private JTextField statusField = new JTextField();

	/** the editor's menu bar. */
	private JMenuBar menuBar = new JMenuBar();

	/** the figure menu is needed to add or remove tools. */
	private JMenu figureMenu;

	/** the editor's tool bar. */
	private JToolBar toolBar = new JToolBar();

	/** draw tools stored together with corresponding buttons. */
	private Map<AbstractButton, DrawTool> tools = new HashMap<>();

	/** the currently active tool. */
	private DrawTool currentTool;

	/** The default tool used at startup time. */
	private DrawTool defaultTool;

	/** keystrokes which pass the focus to parent view. */
	private Set<AWTKeyStroke> keystrokes = new HashSet<>();
	{
		keystrokes.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_ESCAPE, 0));
	}

	/**
	 * Constructs a standard context by creating and initializing a GUI. A
	 * default set of DrawTools is used.
	 * 
	 * @param view
	 *            the view that displays the figures within the editor.
	 */
	public AbstractContext(DrawView view) {
		this(view, null);
	}

	/**
	 * Constructs a standard context by creating and initializing a GUI. The
	 * drawing tools available can be parameterized using
	 * <code>toolFactories</code>.
	 * 
	 * @param view
	 *            the view that displays the figures within the editor.
	 * @param toolFactories
	 *            list of factories used to create tools.
	 */
	public AbstractContext(DrawView view, List<DrawToolFactory> toolFactories) {
		super("JDraw Editor");

		this.view = view;
		this.view.setDrawContext(this);
		
		this.toolFactories = toolFactories;

		this.defaultTool = new StdSelectionTool(view, this);
	}

	/** 
	 * Get the gui built and initialized. This method is called as init-method when 
	 * this object is created by Spring.
	 */
	public void initGUI() {
		if (this.guiInitialized) { throw new IllegalStateException(); }
		this.guiInitialized = true;

		JComponent drawPanel = (JComponent) view;
		final JScrollPane scrollPane = new JScrollPane(drawPanel,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		final JPanel content = new JPanel(new BorderLayout());

		windowNr = windowCounter++;
		if (windowNr > 0) {
			this.setTitle("<" + this.getTitle() + ":" + windowNr + ">");
		}

		content.add(toolBar, BorderLayout.NORTH);
		content.add(scrollPane, BorderLayout.CENTER);
		this.getContentPane().add(content, BorderLayout.CENTER);
		this.getContentPane().add(statusField, BorderLayout.SOUTH);

		drawPanel.setFocusTraversalKeys(
				KeyboardFocusManager.UP_CYCLE_TRAVERSAL_KEYS, keystrokes);
		toolBar.setFocusTraversalKeys(
				KeyboardFocusManager.UP_CYCLE_TRAVERSAL_KEYS, keystrokes);

		this.pack();

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				close();
			}
		});

		this.requestFocusInWindow();
		this.addKeyListener(new KeyAdapter() {
			// keyPressed is used to gain focus
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) { // if ESC key pressed
					setDefaultTool(); // switch back to default tool
				}
			}
		});

		statusField.setEditable(false);

		this.setJMenuBar(menuBar);
		initMenu(menuBar);
		
		setDefaultTool();
	}

	/** 
	 * Builds and initializes the menus of the editor. 
	 *
	 * @param aMenuBar use this menu bar to insert menus.
	 */
	private void initMenu(JMenuBar aMenuBar) {
		aMenuBar.add(createFileMenu());
		aMenuBar.add(createEditMenu());

		figureMenu = new JMenu("Figures");
		aMenuBar.add(figureMenu);
		addTool(defaultTool);
		addTool(null);
		doRegisterDrawTools();
		
		List<JMenu> menus = createAdditionalMenus();
		if (menus != null) {
			for (JMenu m : menus) {
				aMenuBar.add(m);
			}
		}

		aMenuBar.add(createWindowMenu());
	}

	/**
	 * Create tools and populate figure menu and toolbar with them.
	 */
	protected abstract void doRegisterDrawTools();

	/**
	 * Create and initialize the complete Edit menu.
	 * @return Edit menu ready to use.
	 */
	protected abstract JMenu createEditMenu();

	/**
	 * Create and initialize the complete File menu.
	 * @return File menu ready to use.
	 */
	protected abstract JMenu createFileMenu();

	/**
	 * Create and initialize additional menus.
	 * @return a list of additional menus that are ready to use.
	 */
	protected List<JMenu> createAdditionalMenus() {
		return null;
	}

	/**
	 * Create and initialize the complete Window menu.
	 * @return Window menu ready to use.
	 */
	private JMenu createWindowMenu() {
		JMenu windowMenu = new JMenu("Window");
		JMenuItem newWindow = new JMenuItem("New Window");
		windowMenu.add(newWindow);
		newWindow.addActionListener(e -> {
			JDraw.getContext().showView();
		});
		return windowMenu;
	}

	/**
	 * Adds a new tool to the editor. This method is called during
	 * initialization and is used to add tools to the editor that where
	 * specified in the config.
	 * 
	 * @param tool the tool itself.
	 */
	@Override
	public void addTool(final DrawTool tool) {

		if (tool == null) {
			figureMenu.addSeparator();
			toolBar.addSeparator();
			return;
		}
		
		Action action = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setTool(tool);
			}
		};

		String name = tool.getName();
		Icon icon = tool.getIcon();

		action.putValue(Action.SMALL_ICON, icon);
		action.putValue(Action.SHORT_DESCRIPTION, name);
		// action.putValue(Action.NAME, name);
		Dimension dim = new Dimension(icon.getIconWidth(), icon.getIconHeight());
		JToggleButton button = new JToggleButton(action);
		button.setHorizontalAlignment(SwingConstants.CENTER);
		button.setPreferredSize(dim);
		button.setMinimumSize(dim);
		button.setMaximumSize(dim);
		toolBar.add(button);
		tools.put(button, tool);
		// add tool as entry in Figure menu
		final JMenuItem m = new JRadioButtonMenuItem(action);
		m.setModel(button.getModel());
		m.setText(name);
		figureMenu.add(m);
		m.setSelected(tool.equals(getTool()));
	}

	@Override
	public final void addMenu(JMenu menu) {
		menuBar.add(menu);
		this.setJMenuBar(menuBar); // this call is necessary if the menubar was changed
	}

	@Override
	public final void removeMenu(JMenu menu) {
		menuBar.remove(menu);
		this.setJMenuBar(menuBar);
	}

	@Override
	public final void setDefaultTool() {
		setTool(defaultTool);
	}

	@Override
	public final void setTool(DrawTool tool) {
		if (tool == null) {
			throw new IllegalArgumentException("DrawTool must not be null");
		}

		if (currentTool != tool) {
			if (currentTool != null) {
				currentTool.deactivate();
				showStatusText(""); // empty status line
			}
			currentTool = tool;
			tool.activate();

			view.clearSelection();
			view.setCursor(tool.getCursor());

			toolChanged();
			view.repaint();
		}
	}

	@Override
	public final DrawTool getTool() {
		return currentTool;
	}

	/**
	 * Select the tool button which corresponds to the currently selected tool.
	 */
	private void toolChanged() {
		DrawTool current = getTool();
		for (AbstractButton b : tools.keySet()) {
			b.setSelected((tools.get(b).equals(current)));
		}
	}


	@Override
	public final void showStatusText(String msg) {
		statusField.setText(msg);
	}


	@Override
	public final void showView() {
		if (!guiInitialized) { throw new IllegalStateException(); }
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	/**
	 * Close the window. This method will also dispose the frame and it will
	 * exit the JVM if it is the last open frame to be closed.
	 */
	private void close() {
		view.close();
		if (windowNr == 0) {
			System.exit(0);
		} else {
			this.dispose();
		}
	}

	/**
	 * Set the width of the displayed window.
	 * @param width in pixels
	 */
	public final void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Set the height of the displayed window.
	 * @param height in pixels
	 */
	public final void setHeight(int height) {
		this.height = height;
	}

	@Override
	public final DrawModel getModel() {
		return view.getModel();
	}

	@Override
	public final DrawView getView() {
		return view;
	}

	/**
	 * Retrieve the tool factories. This method can be used e.g. in the doPopulateTools method.
	 * @return a list of tool factories to use in this instance of JDraw.
	 */
	protected final List<DrawToolFactory> getToolFactories() {
		return toolFactories;
	}

}
