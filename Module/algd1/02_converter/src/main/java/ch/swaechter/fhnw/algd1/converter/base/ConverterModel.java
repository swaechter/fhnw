package ch.swaechter.fhnw.algd1.converter.base;

import javax.swing.text.Document;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Wolfgang Weck
 */
public class ConverterModel<E> {
    private E value;
    private boolean isUpdating = false;
    private final List<Updater<E>> updaters = new LinkedList<>();

    @SuppressWarnings("unchecked")
    public void updateAllExceptSource(Document sourceDoc, E value) {
        if (!isUpdating) try {
            isUpdating = true;
            if (this.value == null || !this.value.equals(value)) {
                this.value = value;
                for (Updater<E> upd : updaters.toArray(new Updater[updaters.size()])) {
                    if (upd.getDocument() != sourceDoc) {
                        try {
                            upd.update(value);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } finally {
            isUpdating = false;
        }
    }

    public void add(Updater<E> upd) {
        if (!updaters.contains(upd)) {
            updaters.add(upd);
        }
    }

    public void remove(Updater<E> upd) {
        updaters.remove(upd);
    }
}
