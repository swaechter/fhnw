package jdraw.figures;

import jdraw.framework.DrawContext;
import jdraw.framework.DrawTool;
import jdraw.framework.DrawView;
import jdraw.framework.Figure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * The abstract draw tool represents a draw tool with some common, inbuilt functionality.
 *
 * @author Simon Wächter
 */
public abstract class AbstractDrawTool implements DrawTool {

    /**
     * the image resource path.
     */
    private static final String IMAGES = "/images/";

    /**
     * The context we use for drawing.
     */
    private final DrawContext context;

    /**
     * The context's view. This variable can be used as a shortcut, i.e.
     * instead of calling context.getView().
     */
    private final DrawView view;

    /**
     * Name of the figure.
     */
    private final String name;

    /**
     * Icon file name of the figure located in the images resource path.
     */
    private final String iconname;

    /**
     * Cursor used for the figure.
     */
    private final Cursor cursor;

    /**
     * Figure that is used for the draw tool.
     */
    private Figure figure;

    /**
     * Point to save the first mouse click
     */
    private Point anchor;

    /**
     * Create a new abstract draw tool for the given context.
     *
     * @param context  Draw context
     * @param name     Name of the draw tool
     * @param iconmame Icon file name
     * @param cursor   Cursor of the draw tool
     */
    public AbstractDrawTool(DrawContext context, String name, String iconmame, Cursor cursor) {
        this.context = context;
        this.view = context.getView();
        this.name = name;
        this.iconname = iconmame;
        this.cursor = cursor;
    }

    /**
     * Deactivates the current mode by resetting the cursor
     * and clearing the status bar.
     *
     * @see jdraw.framework.DrawTool#deactivate()
     */
    @Override
    public void deactivate() {
        this.context.showStatusText("");
    }

    /**
     * Activates the figure mode. There will be a
     * specific menu added to the menu bar that provides settings for
     * this attributes
     */
    @Override
    public void activate() {
        this.context.showStatusText(name + " Mode");
    }

    /**
     * Handles mouse down events in the drawing view.
     *
     * @param x x coordinate of mouse position
     * @param y y coordinate of mouse position
     * @param e mouse event, contains state of modifiers
     */
    @Override
    public void mouseDown(int x, int y, MouseEvent e) {
        if (figure != null) {
            throw new IllegalStateException();
        }
        anchor = new Point(x, y);
        figure = createFigure(x, y);
        view.getModel().addFigure(figure);
    }

    /**
     * Handles mouse drag events in the drawing view.
     *
     * @param x x coordinate of mouse position
     * @param y y coordinate of mouse position
     * @param e mouse event, contains state of modifiers
     */
    @Override
    public void mouseDrag(int x, int y, MouseEvent e) {
        figure.setBounds(anchor, new Point(x, y));
        java.awt.Rectangle r = figure.getBounds();
        this.context.showStatusText("w: " + r.width + ", h: " + r.height);
    }

    /**
     * Handles mouse up in the drawing view.
     *
     * @param x x coordinate of mouse position
     * @param y y coordinate of mouse position
     * @param e mouse event, contains state of modifiers
     */
    @Override
    public void mouseUp(int x, int y, MouseEvent e) {
        // Remove one pixel figures (Click and don't drag/change bounds)
        if (figure.getBounds().width == 0 && figure.getBounds().height == 0) {
            view.getModel().removeFigure(figure);
        }

        anchor = null;
        figure = null;
        this.context.showStatusText(getName() + " Mode");
    }

    /**
     * Get the cursor of the figure.
     *
     * @return Cursor of the figure
     */
    @Override
    public Cursor getCursor() {
        return cursor;
    }

    /**
     * Get the icon of the figure.
     *
     * @return Icon of the figure
     */
    @Override
    public Icon getIcon() {
        return new ImageIcon(getClass().getResource(IMAGES + iconname));
    }

    /**
     * Get the name of the figure.
     *
     * @return Name of the figure
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Create a draw tool specific figure.
     *
     * @param x X coordinate of the initial mouse click
     * @param y Y coordinate of the initial mouse click
     * @return Draw tool specific figure
     */
    protected abstract Figure createFigure(int x, int y);
}
