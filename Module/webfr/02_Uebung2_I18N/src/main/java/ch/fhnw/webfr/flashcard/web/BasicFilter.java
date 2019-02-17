package ch.fhnw.webfr.flashcard.web;

import org.apache.log4j.Logger;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
public class BasicFilter extends HttpFilter {

    private static final Logger logger = Logger.getLogger(BasicServlet.class);

    @Override
    public void init(FilterConfig filterConfig) {
        logger.debug("Basic filter created");
    }

    @Override
    public void doFilter(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String uri = servletRequest.getRequestURI();
        logger.debug("Before request [uri=" + uri + "]");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        logger.debug("Basic filter destroyed");
    }
}
