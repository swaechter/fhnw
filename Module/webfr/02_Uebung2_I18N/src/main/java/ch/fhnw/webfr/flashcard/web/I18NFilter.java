package ch.fhnw.webfr.flashcard.web;

import ch.fhnw.webfr.flashcard.util.Globals;
import jdk.internal.util.xml.impl.ReaderUTF8;
import org.apache.log4j.Logger;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Map;
import java.util.Properties;

@WebFilter(urlPatterns = {"/*"})
public class I18NFilter extends HttpFilter {

    private static final Logger logger = Logger.getLogger(I18NFilter.class);

    private Properties properties;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext servletContext = filterConfig.getServletContext();

        String fileName = servletContext.getInitParameter(Globals.KEY_MESSAGE_FILENAME);
        if (fileName == null || fileName.isEmpty()) {
            logger.debug("No message filename found. Using fallback message filename " + Globals.VALUE_MESSAGE_FILENAME);
            fileName = Globals.VALUE_MESSAGE_FILENAME;
        }

        try (Reader reader = new ReaderUTF8(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName))) {
            properties = new Properties();
            properties.load(reader);
            logger.debug("I18N filter created");
        } catch (IOException exception) {
            throw new ServletException(exception.getMessage());
        }
    }

    @Override
    public void doFilter(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        I18NResponseWrapper i18NResponseWrapper = new I18NResponseWrapper(servletResponse);
        filterChain.doFilter(servletRequest, i18NResponseWrapper);

        Writer servletResponseWriter = servletResponse.getWriter();
        try (BufferedReader bufferedWrapperReader = new BufferedReader(i18NResponseWrapper.getReader())) {
            for (String line; (line = bufferedWrapperReader.readLine()) != null; ) {
                servletResponseWriter.append(internationalizeLine(line));
            }
        }
        servletResponseWriter.flush();
    }

    @Override
    public void destroy() {
        logger.debug("I18N filter destroyed");
    }

    private String internationalizeLine(String line) {
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            line = line.replace((String) entry.getKey(), (String) entry.getValue());
        }
        return line;
    }
}
