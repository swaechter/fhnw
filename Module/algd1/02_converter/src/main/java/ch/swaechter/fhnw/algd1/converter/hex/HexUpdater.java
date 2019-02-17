package ch.swaechter.fhnw.algd1.converter.hex;

import ch.swaechter.fhnw.algd1.converter.base.Updater;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/**
 * @author Wolfgang Weck
 */
public class HexUpdater implements Updater<Integer> {
    private final Document doc;

    public HexUpdater(Document doc) {
        this.doc = doc;
    }

    @Override
    public Document getDocument() {
        return doc;
    }

    @Override
    public void update(Integer val) {
        try {
            ((PlainDocument) doc).replace(0, doc.getLength(), HexConverter
                    .toString(val), doc.getDefaultRootElement().getAttributes());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
