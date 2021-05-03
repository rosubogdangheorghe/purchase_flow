package com.roki.purchase.controller.webcontroller;

import com.roki.purchase.entity.DepartmentEntity;
import com.roki.purchase.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DepartmentController {


    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping("/web/department/list")

    public ModelAndView getAllDepartments(){
        ModelAndView modelAndView = new ModelAndView("/dashboard/department/departments");
        modelAndView.addObject("departmentList",departmentRepository.findAll());
        return modelAndView;
    }

    @GetMapping("/web/department/add")
    public ModelAndView addDepartment() {
        ModelAndView modelAndView = new ModelAndView("/dashboard/department/department-form");
        modelAndView.addObject("departmentObject",new DepartmentEntity());
        return modelAndView;
    }

    @PostMapping("/web/department/save")
    public ModelAndView saveDepartment(@ModelAttribute("departmentObject") DepartmentEntity department){
        ModelAndView modelAndView = new ModelAndView("redirect:/web/department/list");
        departmentRepository.save(department);
        return modelAndView;
    }

    @GetMapping("/web/department/edit/{departmentId}")

    public ModelAndView editDepartment(@PathVariable Integer departmentId){
        ModelAndView modelAndView = new ModelAndView("/dashboard/department/department-form");
        modelAndView.addObject("departmentObject",departmentRepository.findById(departmentId).get());
        return modelAndView;

    }

}
