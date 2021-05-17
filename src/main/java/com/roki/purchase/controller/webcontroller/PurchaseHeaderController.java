package com.roki.purchase.controller.webcontroller;

import com.roki.purchase.entity.*;
import com.roki.purchase.repository.*;
import com.roki.purchase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/web")
public class PurchaseHeaderController {

    @Autowired
    private PurchaseHeaderRepository purchaseHeaderRepository;

    @Autowired
    private PurchaseLineRepository purchaseLineRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusRepository statusRepository;

    @GetMapping("/purchase-header/in-progress-list")
            public ModelAndView getAllPurchaseHeaders() {

        ModelAndView modelAndView = new ModelAndView("/purchase/purchase-header/purchase-headers");

        Optional<User> user = userService.getLoggedInUser();
        if(user.isPresent()) {
            UserEntity userEntity = userRepository.findByUsername(user.get().getUsername());
            modelAndView.addObject("purchaseHeadersList",purchaseHeaderRepository.findAllByUserIdAndStatusEquals(userEntity.getUserId(),
                    statusRepository.findByStatus("in progress")));
            modelAndView.addObject("editAndPromoteButtons", true);
        }

        return modelAndView;
    }


    @GetMapping("/purchase-header/issued-list")
    public ModelAndView getAllPromotedPurchaseHeaders() {

        ModelAndView modelAndView = new ModelAndView("/purchase/purchase-header/purchase-headers");

        Optional<User> user = userService.getLoggedInUser();
        if(user.isPresent()) {
            UserEntity userEntity = userRepository.findByUsername(user.get().getUsername());
            modelAndView.addObject("purchaseHeadersList",purchaseHeaderRepository.findAllByUserIdAndStatusEquals(userEntity.getUserId(),
                    statusRepository.findByStatus("issued")));
            modelAndView.addObject("editAndPromoteButtons", false);
        }

        return modelAndView;
    }


    @GetMapping("/purchase-header/add")
    public ModelAndView addPurchaseHeader() {
        ModelAndView modelAndView = new ModelAndView("/purchase/purchase-header/purchase-header-form");
        modelAndView.addObject("purchaseHeaderObject",new PurchaseHeaderEntity());


        getPurchaseHeaderData(modelAndView);

        return modelAndView;
    }

    @PostMapping("/purchase-header/save")
    public ModelAndView savePurchaseHeader(@ModelAttribute("purchaseHeaderObject") PurchaseHeaderEntity purchaseHeader,
                                           HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("redirect:/web/purchase-header/in-progress-list");

        purchaseHeader = purchaseHeaderRepository.save(purchaseHeader);
        String purchaseDate = new StringBuilder().append(purchaseHeader.getPurchaseDate().getYear()).append("-").append(purchaseHeader.getPurchaseDate().getMonth()).append("-").
                append(purchaseHeader.getPurchaseDate().getDayOfMonth()).toString();

        String purchaseHeaderNumber = purchaseHeader.getPurchaseHeaderId() + "-" + purchaseDate;
        purchaseHeader.setPurchaseNumber(purchaseHeaderNumber);
        purchaseHeaderRepository.save(purchaseHeader);


        return modelAndView;
    }

    @GetMapping("/purchase-header/edit/{purchaseHeaderId}")
    public ModelAndView editPurchaseHeader(@PathVariable Integer purchaseHeaderId) {

        ModelAndView modelAndView = new ModelAndView("/purchase/purchase-header/purchase-header-form");
        PurchaseHeaderEntity purchaseHeader = purchaseHeaderRepository.findById(purchaseHeaderId).get();
        modelAndView.addObject("purchaseHeaderObject",purchaseHeader);

        if(purchaseHeader.getStatus().getStatus().equals("in progress")) {
            getPurchaseHeaderData(modelAndView);
        } else {
            modelAndView.setViewName("redirect:/web/purchase-header/in-progress-list");
        }
        return modelAndView;

    }


    @GetMapping("/purchase-header/promote/{purchaseHeaderId}")
    public ModelAndView promotePurchaseRequest(@PathVariable Integer purchaseHeaderId) {
        ModelAndView modelAndView = new ModelAndView("redirect:/web/purchase-header/issued-list");
        PurchaseHeaderEntity purchaseHeader = purchaseHeaderRepository.findById(purchaseHeaderId).get();
        List<PurchaseLineEntity> purchaseLines = purchaseLineRepository.findAllByPurchaseHeader(purchaseHeader);
        if(purchaseLines.isEmpty()) {
            modelAndView.setViewName("redirect:/web/purchase-request/" + purchaseHeader.getPurchaseHeaderId());
        }
        else {
            StatusEntity status = statusRepository.findByStatus("issued");
            purchaseHeader.setStatus(status);
            purchaseHeaderRepository.save(purchaseHeader);
        }

        return modelAndView;
    }


    private void getPurchaseHeaderData(ModelAndView modelAndView) {
        List<SupplierEntity> suppliersList = supplierRepository.findAllByIsBlockedIsFalse();
        modelAndView.addObject("suppliersList",suppliersList);

        List<DepartmentEntity> departmentsList = departmentRepository.findAll();
        modelAndView.addObject("departmentsList",departmentsList);

        List<CurrencyEntity> currenciesList = currencyRepository.findAll();
        modelAndView.addObject("currenciesList",currenciesList);

        Optional<User> user = userService.getLoggedInUser();
        if(user.isPresent()) {
            UserEntity userEntity = userRepository.findByUsername(user.get().getUsername());
            modelAndView.addObject("userEntity",userEntity);
        }

        List<StatusEntity> statusesList = statusRepository.findAllByStatus("in progress");
        modelAndView.addObject("statusesList",statusesList);
    }


}
