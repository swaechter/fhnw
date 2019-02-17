package ch.fhnw.webfr.flashcard.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class IndexController implements ErrorController {

    @GetMapping("/")
    public ModelAndView showIndex() {
        return new ModelAndView("redirect:questionnaires");
    }

    @GetMapping("/error")
    public ModelAndView showError() {
        return new ModelAndView("error");
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
