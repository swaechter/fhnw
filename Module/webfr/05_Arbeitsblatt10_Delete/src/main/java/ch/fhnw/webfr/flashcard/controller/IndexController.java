package ch.fhnw.webfr.flashcard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping("/")
    public ModelAndView showIndex() {
        return new ModelAndView("redirect:questionnaires");
    }
}
