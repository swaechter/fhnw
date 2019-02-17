package ch.swaechter.fhnw.algd1.converter.base;

import javax.swing.text.Document;

/**
 * @author Wolfgang Weck
 */
public interface Updater<E> {
    Document getDocument();

    void update(E value);
}
