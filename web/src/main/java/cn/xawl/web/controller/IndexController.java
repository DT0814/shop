package cn.xawl.web.controller;

import cn.xawl.web.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
    @Autowired
    private IndexService indexService;

    @GetMapping
    public ModelAndView index() {
        ModelAndView md = new ModelAndView("index");
        String idnexAd1 = indexService.queryIndexAD1();
        md.addObject("indexAd1", idnexAd1);
        String idnexAd2 = indexService.queryIndexAD2();
        md.addObject("indexAd2", idnexAd2);
        return md;
    }
}
