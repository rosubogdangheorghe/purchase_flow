package com.roki.purchase.controller.webcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DashBoardController {

    @GetMapping("/web/dashboard")
    public ModelAndView showDashboard() {
        ModelAndView modelAndView = new ModelAndView("/dashboard/dashboard");
        return modelAndView;
    }
}
