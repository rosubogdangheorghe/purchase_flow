package com.roki.purchase.controller.webcontroller;

import com.roki.purchase.data.ResponseData;
import com.roki.purchase.entity.SupplierEntity;
import com.roki.purchase.repository.SupplierRepository;
import com.roki.purchase.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping("/web")
public class SupplierController {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private SupplierService supplierService;

    private static final int DEFAULT_PAGE_NUMBER = 1;
    private static final int DEFAULT_PAGE_SIZE = 5;
    private static final String DEFAULT_SORT ="supplierCode";

    @GetMapping("/supplier/list")
    public ModelAndView getAllSuppliers() {
        ModelAndView modelAndView = new ModelAndView("redirect:/web/supplier/list/"+DEFAULT_PAGE_NUMBER +"/"+DEFAULT_PAGE_SIZE);
        return modelAndView;
    }


    @GetMapping("/supplier/list/{page}/{page-size}")
    public ModelAndView getAllSuppliersByPage(
            @PathVariable(name = "page") Integer pageNumber,
            @PathVariable(name = "page-size") Integer pageSize
    ){
        ModelAndView modelAndView = new ModelAndView("/dashboard/supplier/suppliers");
        Page<SupplierEntity> paginatedSuppliers = supplierService.findAllByPage(pageNumber,pageSize, DEFAULT_SORT);
        modelAndView.addObject("supplierList",createResponseDto(paginatedSuppliers,pageNumber));
        return modelAndView;
    }

    private ResponseData createResponseDto(Page<SupplierEntity> supplierPage, Integer pageNumber) {
        final Map<String,Integer> page = new HashMap<>();
        page.put("currentPage",pageNumber);
        page.put("totalPages",supplierPage.getTotalPages());
        page.put("totalElements",(int) supplierPage.getTotalElements());
        return ResponseData.create(supplierPage.getContent(),page);
    }

    @GetMapping("/supplier/add")
    public ModelAndView addSupplier(){
        ModelAndView modelAndView = new ModelAndView("/dashboard/supplier/supplier-form");
        Map<String, String> countryList = getCountryFullList();
        modelAndView.addObject("countryList",countryList);
        modelAndView.addObject("supplierObject",new SupplierEntity());

        return modelAndView;
    }


    @PostMapping("/supplier/save")
    public ModelAndView saveSupplier(@ModelAttribute("supplierObject") SupplierEntity supplier){
        ModelAndView modelAndView = new ModelAndView("redirect:/web/supplier/list");
        supplier.setBlocked(false);
        supplierRepository.save(supplier);
        return modelAndView;
    }


    @GetMapping("/supplier/edit/{supplierId}")
    public ModelAndView editSupplier(@PathVariable Integer supplierId) {
        ModelAndView modelAndView = new ModelAndView("/dashboard/supplier/supplier-form");
        modelAndView.addObject("supplierObject",supplierRepository.findById(supplierId).get());
        Map<String, String> countryList = getCountryFullList();
        modelAndView.addObject("countryList",countryList);
        return modelAndView;
    }

    @GetMapping("/supplier/block/{supplierId}")
    public ModelAndView deleteSupplier(@PathVariable Integer supplierId){
        ModelAndView modelAndView = new ModelAndView("redirect:/web/supplier/list");
        Optional<SupplierEntity> supplier = supplierRepository.findById(supplierId);
       if(supplier.isPresent()) {
           supplier.get().setBlocked(true);
           supplierRepository.save(supplier.get());
       }
       return modelAndView;
    }
    @GetMapping("/supplier/unBlock/{supplierId}")
    public ModelAndView unDeleteSupplier(@PathVariable Integer supplierId){
        ModelAndView modelAndView = new ModelAndView("redirect:/web/supplier/list");
        Optional<SupplierEntity> supplier = supplierRepository.findById(supplierId);
        if(supplier.isPresent()) {
            supplier.get().setBlocked(false);
            supplierRepository.save(supplier.get());
        }
        return modelAndView;
    }

    @GetMapping("/supplier/search")
        public ModelAndView getSupplierByName(@Param("keyword") String keyword){
            ModelAndView modelAndView = new ModelAndView("/dashboard/supplier/search-list");
            List<SupplierEntity> supplierEntityList = supplierService.getAllSuppliersByName(keyword);
            modelAndView.addObject("supplierList",supplierEntityList);
            modelAndView.addObject("keyword",keyword);
            return modelAndView;
        }


    private Map<String, String> getCountryFullList() {
        Map<String, String> countryList = new HashMap<>();
        String[] locales = Locale.getISOCountries();
        for (String countryCode : locales) {
            Locale object = new Locale("", countryCode);
            countryList.put(object.getCountry(), object.getDisplayCountry());
        }
        return countryList;
    }
}
