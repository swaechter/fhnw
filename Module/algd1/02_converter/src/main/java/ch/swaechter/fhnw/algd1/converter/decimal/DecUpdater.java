package ch.swaechter.fhnw.algd1.converter.decimal;

import ch.swaechter.fhnw.algd1.converter.base.Updater;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/**
 * @author Wolfgang Weck
 */
public class DecUpdater implements Updater<Integer> {
    private final Document doc;

    public DecUpdater(Document doc) {
        this.doc = doc;
    }

    @Override
    public Document getDocument() {
        return doc;
    }

    @Override
    public void update(Integer val) {
        try {
            ((PlainDocument) doc).replace(0, doc.getLength(), DecConverter
                    .toString(val), doc.getDefaultRootElement().getAttributes());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
