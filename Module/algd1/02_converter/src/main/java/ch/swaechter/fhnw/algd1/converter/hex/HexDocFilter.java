package ch.swaechter.fhnw.algd1.converter.hex;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

/**
 * @author Wolfgang Weck
 */
public class HexDocFilter extends DocumentFilter {
    private final int min, max;

    public HexDocFilter() {
        this(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public HexDocFilter(int min, int max) {
        this.min = min;
        this.max = max;
    }

    private String curText(FilterBypass fb) throws BadLocationException {
        Document d = fb.getDocument();
        return d.getText(0, d.getLength());
    }

    private boolean isOK(String s) {
        s = normalized(s);
        if (s.equals("") || (s.equals("-") && min < 0)) return true;
        try {
            int i = Integer.parseInt(s, 16);
            return s.equals("" + Integer.toString(i, 16).toLowerCase()) && i >= min
                    && i <= max;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private String normalized(String s) {
        int i = 0, beg = 0, l = s.length();
        if (l > 0) {
            char c = s.charAt(0);
            if (c == '-') {
                beg = 1;
                i = 1;
            }
            while (i < l && s.charAt(i) == '0') {
                i++;
            }
            return (s.substring(0, beg) + s.substring(i)).toLowerCase();
        } else return s.toLowerCase();
    }

    @Override
    public void remove(FilterBypass fb, int offset, int length)
            throws BadLocationException {
        StringBuilder s = new StringBuilder(curText(fb));
        s.delete(offset, offset + length);
        if (isOK(s.toString())) super.remove(fb, offset, length);
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string,
                             AttributeSet attr) throws BadLocationException {
        String s0 = curText(fb);
        StringBuilder s;
        if (s0.equals("0") && offset == 1) {
            s = new StringBuilder();
            offset--;
        } else if (s0.equals("-0") && offset == 2) {
            s = new StringBuilder("-");
            offset--;
        } else s = new StringBuilder(s0);
        s.insert(offset, string.toUpperCase());
        if (isOK(s.toString())) super.insertString(fb, offset,
                string.toUpperCase(), attr);
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text,
                        AttributeSet attrs) throws BadLocationException {
        String s0 = curText(fb);
        StringBuilder s;
        if (s0.equals("0") && offset == 1) {
            s = new StringBuilder();
            offset--;
            length++;
        } else if (s0.equals("-0") && offset == 2) {
            s = new StringBuilder("-");
            offset--;
            length++;
        } else s = new StringBuilder(s0);
        s.replace(offset, offset + length, text.toUpperCase());
        if (isOK(s.toString())) super.replace(fb, offset, length,
                text.toUpperCase(), attrs);
    }
}