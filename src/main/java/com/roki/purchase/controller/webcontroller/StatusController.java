package com.roki.purchase.controller.webcontroller;

import com.roki.purchase.entity.StatusEntity;
import com.roki.purchase.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/web")
public class StatusController {

    @Autowired
    private StatusRepository statusRepository;

    @GetMapping("/status/list")
    public ModelAndView getStatusList() {
        ModelAndView modelAndView = new ModelAndView("/dashboard/status/statuses");
        modelAndView.addObject("statusList",statusRepository.findAll());
        return modelAndView;
    }

    @GetMapping("/status/add")
    public ModelAndView addStatus(){
        ModelAndView modelAndView = new ModelAndView("/dashboard/status/status-form");
        modelAndView.addObject("statusObject",new StatusEntity());
        return modelAndView;
    }

    @PostMapping("/status/save")
    public ModelAndView saveStatus(@ModelAttribute("statusObject")StatusEntity status){
        ModelAndView modelAndView = new ModelAndView("redirect:/web/status/list");
        status.setEnable(true);
        statusRepository.save(status);
        return modelAndView;
    }
    @GetMapping("status/edit/{statusId}")
    public ModelAndView editStatusById(@PathVariable Integer statusId){
        ModelAndView modelAndView = new ModelAndView("/dashboard/status/status-form");
        modelAndView.addObject("statusObject",statusRepository.findById(statusId).get());
        return modelAndView;
    }




}
