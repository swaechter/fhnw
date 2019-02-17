/*
 * Copyright (c) 2017 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.std;

import jdraw.framework.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Provide a standard behavior for the drawing model. This class initially does not implement the methods
 * in a proper way.
 * It is part of the course assignments to do so.
 *
 * @author Simon Wächter
 */
public class StdDrawModel implements DrawModel, FigureListener {

    /**
     * List that contains all figures.
     */
    private final List<Figure> figures;

    /**
     * List that contains all listener for the draw model.
     */
    private final List<DrawModelListener> modellisteners;

    /**
     * Create a new draw model that holds all figures and provides an observer mechanism to keep in touch will all changes.
     */
    public StdDrawModel() {
        this.figures = new ArrayList<>();
        this.modellisteners = new CopyOnWriteArrayList<>();
    }

    @Override
    public void addFigure(Figure f) {
        if (figures.contains(f)) {
            return;
        }

        figures.add(f);
        f.addFigureListener(this);

        updateModel(f, DrawModelEvent.Type.FIGURE_ADDED);
    }

    @Override
    public Iterable<Figure> getFigures() {
        return figures;
    }

    @Override
    public void removeFigure(Figure f) {
        if (!figures.contains(f)) {
            return;
        }

        figures.remove(f);
        f.removeFigureListener(this);

        updateModel(f, DrawModelEvent.Type.FIGURE_REMOVED);
    }

    @Override
    public void addModelChangeListener(DrawModelListener listener) {
        if (modellisteners.contains(listener) || listener == null) {
            return;
        }

        modellisteners.add(listener);
    }

    @Override
    public void removeModelChangeListener(DrawModelListener listener) {
        modellisteners.remove(listener);
    }

    /**
     * The draw command handler. Initialized here with a dummy implementation.
     */
    // TODO initialize with your implementation of the undo/redo-assignment.
    private DrawCommandHandler handler = new EmptyDrawCommandHandler();

    /**
     * Retrieve the draw command handler in use.
     *
     * @return the draw command handler.
     */
    @Override
    public DrawCommandHandler getDrawCommandHandler() {
        return handler;
    }

    @Override
    public void setFigureIndex(Figure f, int index) {
        if (!figures.contains(f)) {
            throw new IllegalArgumentException();
        }

        if (index < 0 || index >= figures.size()) {
            throw new IndexOutOfBoundsException();
        }

        figures.remove(f);
        figures.add(index, f);

        updateModel(f, DrawModelEvent.Type.DRAWING_CHANGED);
    }

    @Override
    public void removeAllFigures() {
        for (Figure f : figures) {
            f.removeFigureListener(this);
        }

        figures.clear();

        updateModel(null, DrawModelEvent.Type.DRAWING_CLEARED);
    }

    @Override
    public void figureChanged(FigureEvent e) {
        Figure f = e.getFigure();

        updateModel(f, DrawModelEvent.Type.FIGURE_CHANGED);
    }

    private void updateModel(Figure f, DrawModelEvent.Type t) {
        DrawModelEvent event = new DrawModelEvent(this, f, t);
        for (DrawModelListener modellistener : modellisteners) {
            modellistener.modelChanged(event);
        }
    }
}
