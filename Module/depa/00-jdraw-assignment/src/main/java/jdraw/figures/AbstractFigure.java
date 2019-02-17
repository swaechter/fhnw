package jdraw.figures;

import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureHandle;
import jdraw.framework.FigureListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The abstract figure represents a figure with inbuilt observer functionality that notifies the model about figure changes.
 *
 * @author Simon Wächter
 */
public abstract class AbstractFigure implements Figure {

    /**
     * List with all listener of the figure.
     */
    private final List<FigureListener> listeners = new CopyOnWriteArrayList<>();

    /**
     * List with all figure handles.
     */
    private final List<FigureHandle> handles = new CopyOnWriteArrayList<>();

    @Override
    public final void addFigureListener(FigureListener listener) {
        if (listeners.contains(listener) || listener == null) {
            return;
        }

        listeners.add(listener);
    }

    @Override
    public final void removeFigureListener(FigureListener listener) {
        listeners.remove(listener);
    }

    @Override
    public final List<FigureHandle> getHandles() {
        return (!handles.isEmpty()) ? new CopyOnWriteArrayList<>(handles) : null;
    }

    /**
     * Add a new figure handle.
     *
     * @param handle New figure handle
     */
    protected final void addFigureHandle(FigureHandle handle) {
        if (handles.contains(handle) || handle == null) {
            return;
        }

        handles.add(handle);
    }

    /**
     * Update all listeners about the figure change.
     */
    protected final void notifyListeners() {
        FigureEvent event = new FigureEvent(this);
        for (FigureListener listener : listeners) {
            listener.figureChanged(event);
        }
    }

    abstract public Figure clone();
}
