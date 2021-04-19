package com.roki.purchase.controller.webcontroller;

import com.roki.purchase.dto.ResponseDto;
import com.roki.purchase.entity.SupplierEntity;
import com.roki.purchase.repository.SupplierRepository;
import com.roki.purchase.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Controller
public class SupplierController {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private SupplierService supplierService;

    private static final int DEFAULT_PAGE_NUMBER = 1;
    private static final int DEFAULT_PAGE_SIZE = 5;
    private static final String DEFAULT_SORT ="supplierCode";

    @GetMapping("/web/supplier/list")
    public ModelAndView getAllSuppliers() {
        ModelAndView modelAndView = new ModelAndView("redirect:/web/supplier/list/"+DEFAULT_PAGE_NUMBER +"/"+DEFAULT_PAGE_SIZE);
        return modelAndView;
    }


    @GetMapping("/web/supplier/list/{page}/{page-size}")
    public ModelAndView getAllSuppliersByPage(
            @PathVariable(name = "page") Integer pageNumber,
            @PathVariable(name = "page-size") Integer pageSize
    ){
        ModelAndView modelAndView = new ModelAndView("/dashboard/supplier/suppliers");
        Page<SupplierEntity> paginatedSuppliers = supplierService.findAllByPage(pageNumber,pageSize, DEFAULT_SORT);
        modelAndView.addObject("supplierList",createResponseDto(paginatedSuppliers,pageNumber));
        System.out.println(modelAndView.toString());
        return modelAndView;
    }

    private ResponseDto createResponseDto(Page<SupplierEntity> supplierPage,Integer pageNumber) {
        final Map<String,Integer> page = new HashMap<>();
        page.put("currentPage",pageNumber);
        page.put("totalPages",supplierPage.getTotalPages());
        page.put("totalElements",(int) supplierPage.getTotalElements());
        return ResponseDto.create(supplierPage.getContent(),page);
    }




    @GetMapping("/web/supplier/add")
    public ModelAndView addSupplier(){
        ModelAndView modelAndView = new ModelAndView("/dashboard/supplier/supplier-form");
        Map<String, String> countryList = getCountryFullList();
        modelAndView.addObject("countryList",countryList);
        modelAndView.addObject("supplierObject",new SupplierEntity());

        return modelAndView;
    }


    @PostMapping("/web/supplier/save")
    public ModelAndView saveSupplier(@ModelAttribute("supplierObject") SupplierEntity supplier){
        ModelAndView modelAndView = new ModelAndView("redirect:/web/supplier/list");
        supplier.setBlocked(false);
        supplierRepository.save(supplier);
        return modelAndView;
    }


    @GetMapping("/web/supplier/edit/{supplierId}")
    public ModelAndView editSupplier(@PathVariable Integer supplierId) {
        ModelAndView modelAndView = new ModelAndView("/dashboard/supplier/supplier-form");
        modelAndView.addObject("supplierObject",supplierRepository.findById(supplierId).get());
        Map<String, String> countryList = getCountryFullList();
        modelAndView.addObject("countryList",countryList);
        return modelAndView;
    }

    @GetMapping("/web/supplier/block/{supplierId}")
    public ModelAndView deleteSupplier(@PathVariable Integer supplierId){
        ModelAndView modelAndView = new ModelAndView("redirect:/web/supplier/list");
        Optional<SupplierEntity> supplier = supplierRepository.findById(supplierId);
       if(supplier.isPresent()) {
           supplier.get().setBlocked(true);
           supplierRepository.save(supplier.get());
       }
       return modelAndView;
    }
    @GetMapping("/web/supplier/unBlock/{supplierId}")
    public ModelAndView unDeleteSupplier(@PathVariable Integer supplierId){
        ModelAndView modelAndView = new ModelAndView("redirect:/web/supplier/list");
        Optional<SupplierEntity> supplier = supplierRepository.findById(supplierId);
        if(supplier.isPresent()) {
            supplier.get().setBlocked(false);
            supplierRepository.save(supplier.get());
        }
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
