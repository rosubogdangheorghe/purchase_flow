package com.roki.purchase.controller.webcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainPageController {

    @GetMapping("/web/main-page")
    public ModelAndView showMainPage() {
        ModelAndView modelAndView = new ModelAndView("main-page");
        return modelAndView;
    }
}
