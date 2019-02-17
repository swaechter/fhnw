package ch.swaechter.fhnw.algd1.converter.character;

import ch.swaechter.fhnw.algd1.converter.base.Updater;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/**
 * @author Wolfgang Weck
 */
public class CharUpdater implements Updater<Integer> {
    private final Document doc;

    public CharUpdater(Document doc) {
        this.doc = doc;
    }

    @Override
    public Document getDocument() {
        return doc;
    }

    @Override
    public void update(Integer val) {
        if (val > 0x10ffff || val == 0xffff) val = 0;
        try {
            ((PlainDocument) doc).replace(0, doc.getLength(), CharConverter
                    .toString(val), doc.getDefaultRootElement().getAttributes());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
