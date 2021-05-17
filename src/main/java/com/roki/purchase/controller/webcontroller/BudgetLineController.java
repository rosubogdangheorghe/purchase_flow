package com.roki.purchase.controller.webcontroller;

import com.roki.purchase.entity.BudgetLineEntity;
import com.roki.purchase.repository.BudgetLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/web")
public class BudgetLineController {

    @Autowired
    private BudgetLineRepository budgetLineRepository;


    @GetMapping("/budget-line/list")
    public ModelAndView getAllBudgetLines() {
        ModelAndView modelAndView = new ModelAndView("/dashboard/budget-line/budget-lines");
        modelAndView.addObject("budgetLineList",budgetLineRepository.findAll());
        return modelAndView;
    }

    @GetMapping("/budget-line/add")
    public ModelAndView addBudgetLine() {
        ModelAndView modelAndView = new ModelAndView("/dashboard/budget-line/budget-line-form");
        modelAndView.addObject("budgetLineObject",new BudgetLineEntity());
        return modelAndView;
    }

    @PostMapping("/budget-line/save")
    public ModelAndView saveBudgetLine(@ModelAttribute("budgetLineObject") BudgetLineEntity budgetLine) {
        ModelAndView modelAndView = new ModelAndView("redirect:/web/budget-line/list");
        budgetLineRepository.save(budgetLine);
        return modelAndView;
    }
    @GetMapping("/budget-line/edit/{budgetLineId}")
    public ModelAndView editBudgetLine(@PathVariable Integer budgetLineId) {
        ModelAndView modelAndView = new ModelAndView("/dashboard/budget-line/budget-line-form");
        BudgetLineEntity budgetLine = budgetLineRepository.findById(budgetLineId).get();
        modelAndView.addObject("budgetLineObject",budgetLine);

        return modelAndView;
    }
}
