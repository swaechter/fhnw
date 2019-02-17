package ch.fhnw.webfr.flashcard.web;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.PrintWriter;
import java.io.Reader;

public class I18NResponseWrapper extends HttpServletResponseWrapper {

    private final CharArrayWriter writer;

    public I18NResponseWrapper(HttpServletResponse response) {
        super(response);
        writer = new CharArrayWriter();
    }

    @Override
    public PrintWriter getWriter() {
        return new PrintWriter(writer);
    }

    public Reader getReader() {
        writer.flush();
        writer.close();
        return new CharArrayReader(writer.toCharArray());
    }
}
