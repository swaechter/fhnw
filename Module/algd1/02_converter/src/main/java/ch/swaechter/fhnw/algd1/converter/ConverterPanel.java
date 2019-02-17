package ch.swaechter.fhnw.algd1.converter;

import ch.swaechter.fhnw.algd1.converter.base.ConverterModel;
import ch.swaechter.fhnw.algd1.converter.character.CharDocFilter;
import ch.swaechter.fhnw.algd1.converter.character.CharDocListener;
import ch.swaechter.fhnw.algd1.converter.character.CharUpdater;
import ch.swaechter.fhnw.algd1.converter.decimal.DecDocFilter;
import ch.swaechter.fhnw.algd1.converter.decimal.DecDocListener;
import ch.swaechter.fhnw.algd1.converter.decimal.DecUpdater;
import ch.swaechter.fhnw.algd1.converter.hex.HexDocFilter;
import ch.swaechter.fhnw.algd1.converter.hex.HexDocListener;
import ch.swaechter.fhnw.algd1.converter.hex.HexUpdater;
import ch.swaechter.fhnw.algd1.converter.utf8.UTF8DocFilter;
import ch.swaechter.fhnw.algd1.converter.utf8.UTF8DocListener;
import ch.swaechter.fhnw.algd1.converter.utf8.UTF8Updater;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author Wolfgang Weck
 */
@SuppressWarnings("serial")
public class ConverterPanel extends Panel {
    private static final int nofBits = 21;
    private static final Font fnt = new Font("", Font.PLAIN, 20);
    private final JTextField ch, dec, hex, utf8;
    private final ConverterModel<Integer> model = new ConverterModel<>();

    ConverterPanel() {
        setLayout(new GridLayout(4, 2, 10, 10));
        final int min = 0, max = (1 << (nofBits)) - 1;
        ch = newTextField(new CharDocFilter(), new CharDocListener(model), "");
        model.add(new CharUpdater(ch.getDocument()));
        add(newLabel("Character"));
        add(ch);
        dec = newTextField(new DecDocFilter(min, max), new DecDocListener(model),
                "0");
        model.add(new DecUpdater(dec.getDocument()));
        add(newLabel("Decimal"));
        add(dec);
        hex = newTextField(new HexDocFilter(min, max), new HexDocListener(model),
                "0");
        model.add(new HexUpdater(hex.getDocument()));
        add(newLabel("Hexadecimal"));
        add(hex);
        utf8 = newTextField(new UTF8DocFilter(),
                new UTF8DocListener(model), "0");
        model.add(new UTF8Updater(utf8.getDocument()));
        add(newLabel("UTF-8"));
        add(utf8);
    }

    private JTextField newTextField(DocumentFilter filter,
                                    DocumentListener listener, String initVal) {
        final JTextField f = new JTextField(initVal);
        f.setFont(fnt);
        f.setFocusable(true);
        f.setHorizontalAlignment(SwingConstants.RIGHT);
        f.setDoubleBuffered(true);
        f.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == (char) 23) System.exit(0);
            }
        });
        final PlainDocument doc = (PlainDocument) f.getDocument();
        doc.setDocumentFilter(filter);
        doc.addDocumentListener(listener);
        return f;
    }

    private Label newLabel(String text) {
        final Label l = new Label(text);
        l.setFont(fnt);
        return l;
    }
}
