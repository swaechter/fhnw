package ch.swaechter.fhnw.algd1.converter.utf8;

import ch.swaechter.fhnw.algd1.converter.base.Updater;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/**
 * @author Wolfgang Weck
 */
public class UTF8Updater implements Updater<Integer> {
    private final Document doc;

    public UTF8Updater(Document doc) {
        this.doc = doc;
    }

    @Override
    public Document getDocument() {
        return doc;
    }

    @Override
    public void update(Integer val) {
        try {
            ((PlainDocument) doc).replace(0, doc.getLength(), hexBytes(UTF8Converter
                    .codePointToUTF(val)), doc.getDefaultRootElement().getAttributes());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private String hexBytes(byte[] b) {
        StringBuilder s = new StringBuilder();
        if (b != null) {
            for (int x : b) {
                if (x < 0) x = x + 256;
                s.append(hexDigit(x / 16));
                s.append(hexDigit(x % 16));
            }
            return s.toString();
        } else return "";
    }

    private char hexDigit(int x) {
        if (x < 10) return (char) ('0' + x);
        else return (char) ('A' + x - 10);
    }
}
