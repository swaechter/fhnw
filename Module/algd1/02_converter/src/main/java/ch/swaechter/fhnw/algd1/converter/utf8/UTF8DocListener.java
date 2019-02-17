package ch.swaechter.fhnw.algd1.converter.utf8;

import ch.swaechter.fhnw.algd1.converter.base.AbstractDocListener;
import ch.swaechter.fhnw.algd1.converter.base.ConverterModel;

import javax.swing.event.DocumentEvent;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * @author Wolfgang Weck
 */
public class UTF8DocListener extends AbstractDocListener<Integer> {
    public UTF8DocListener(ConverterModel<Integer> model) {
        super(model);
    }

    @Override
    protected void anyUpdate(DocumentEvent e) {
        try {
            final Document doc = e.getDocument();
            final byte[] b = bytes(doc.getText(0, doc.getLength()).toUpperCase());
            final int x = b.length > 0 ? UTF8Converter.UTFtoCodePoint(b) : 0;
            updateModel(e, x);
        } catch (BadLocationException e1) {
            e1.printStackTrace();
        }
    }

    private byte[] bytes(String s) {
        byte[] b = new byte[(s.length() + 1) / 2];
        int i = 0, j = 0;
        if (s.length() % 2 == 1) {
            b[j] = (byte) hexDigitAt(s, i);
            i++;
            j++;
        }
        while (i < s.length()) {
            b[j] = (byte) (hexDigitAt(s, i) * 16 + hexDigitAt(s, i + 1));
            i = i + 2;
            j++;
        }
        return b;
    }

    private int hexDigitAt(String s, int i) {
        char c = s.charAt(i);
        if ('0' <= c && c <= '9') return c - '0';
        else return c - 'A' + 10;
    }
}
