package ch.fhnw.webfr.flashcard.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ControllerExceptionHandler {
	private static final Log logger = LogFactory.getLog(ControllerExceptionHandler.class);

	@ExceptionHandler(DataAccessException.class)
	public ModelAndView handleSystemNotFoundException(DataAccessException ex, HttpServletRequest req) {
		logger.debug("handling DataAccessExcpetion: " + ex.getMessage());
	    ModelAndView mav = new ModelAndView();
		mav.addObject("exception", ex);
		mav.addObject("url", req.getRequestURL());
	    mav.setViewName("500");
		return mav;
	}

}
