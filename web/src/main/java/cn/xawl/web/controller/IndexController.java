package cn.xawl.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
    @GetMapping
    public ModelAndView index() {
        ModelAndView md = new ModelAndView("index");
        return md;
    }
}
