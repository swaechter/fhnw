package ch.swaechter.fhnw.algd1.converter.character;

import ch.swaechter.fhnw.algd1.converter.base.AbstractDocListener;
import ch.swaechter.fhnw.algd1.converter.base.ConverterModel;

import javax.swing.event.DocumentEvent;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * @author Wolfgang Weck
 */
public class CharDocListener extends AbstractDocListener<Integer> {
    public CharDocListener(ConverterModel<Integer> model) {
        super(model);
    }

    @Override
    protected void anyUpdate(DocumentEvent e) {
        try {
            final Document doc = e.getDocument();
            final String s = doc.getText(0, doc.getLength());
            final int x = s.length() == 0 ? 0 : CharConverter.fromString(s);
            updateModel(e, x);
        } catch (BadLocationException e1) {
            e1.printStackTrace();
        }
    }
}
