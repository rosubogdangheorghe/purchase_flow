package com.roki.purchase.controller.webcontroller;

import com.roki.purchase.entity.*;
import com.roki.purchase.repository.*;
import com.roki.purchase.service.DataSupplyService;
import com.roki.purchase.service.EmailBusinessService;
import com.roki.purchase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Autowired
    private EmailBusinessService emailBusinessService;

    @Autowired
    private DataSupplyService dataSupplyService;


    @GetMapping("/purchase/purchase-header/in-progress-list")
            public ModelAndView getAllPurchaseHeaders() {

        ModelAndView modelAndView = new ModelAndView("/purchase/purchase-header/purchase-headers");

        String status = "in progress";
        dataSupplyService.purchaseHeaderDataByLoggedUserIdAndStatus(modelAndView, status);


        modelAndView.addObject("editAndPromoteButtons", true);
        return modelAndView;
    }


    @GetMapping("/purchase/purchase-header/issued-list")
    public ModelAndView getAllPromotedPurchaseHeaders() {

        ModelAndView modelAndView = new ModelAndView("/purchase/purchase-header/purchase-headers");

        String status = "issued";
        dataSupplyService.purchaseHeaderDataByLoggedUserIdAndStatus(modelAndView, status);

        modelAndView.addObject("editAndPromoteButtons", false);

        return modelAndView;
    }


    @GetMapping("/purchase/purchase-header/add")
    public ModelAndView addPurchaseHeader() {
        ModelAndView modelAndView = new ModelAndView("/purchase/purchase-header/purchase-header-form");
        modelAndView.addObject("purchaseHeaderObject",new PurchaseHeaderEntity());


        dataSupplyService.dataSupplyForPurchaseHeader(modelAndView);

        return modelAndView;
    }

    @PostMapping("/purchase/purchase-header/save")
    public ModelAndView savePurchaseHeader(@ModelAttribute("purchaseHeaderObject") PurchaseHeaderEntity purchaseHeader,
                                           HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("redirect:/web/purchase/purchase-header/in-progress-list");

        purchaseHeader = purchaseHeaderRepository.save(purchaseHeader);
        String purchaseDate = new StringBuilder().append(purchaseHeader.getPurchaseDate().getYear()).append("-").append(purchaseHeader.getPurchaseDate().getMonth()).append("-").
                append(purchaseHeader.getPurchaseDate().getDayOfMonth()).toString();

        String purchaseHeaderNumber = purchaseHeader.getPurchaseHeaderId() + "-" + purchaseDate;
        purchaseHeader.setPurchaseNumber(purchaseHeaderNumber);

        ExchangeRateEntity exchangeRate = exchangeRateRepository.findByCurrencyName(purchaseHeader.getCurrency().getCurrencyName());
        if (exchangeRate.getMultiplier() != null) {
            purchaseHeader.setPurchaseFxRate(exchangeRate.getValue().divide(exchangeRate.getMultiplier()));
        } else {
            purchaseHeader.setPurchaseFxRate(exchangeRate.getValue());
        }

        purchaseHeaderRepository.save(purchaseHeader);


        return modelAndView;
    }

    @GetMapping("/purchase/purchase-header/edit/{purchaseHeaderId}")
    public ModelAndView editPurchaseHeader(@PathVariable Integer purchaseHeaderId) {

        ModelAndView modelAndView = new ModelAndView("/purchase/purchase-header/purchase-header-form");
        PurchaseHeaderEntity purchaseHeader = purchaseHeaderRepository.findById(purchaseHeaderId).get();
        modelAndView.addObject("purchaseHeaderObject",purchaseHeader);

        if(purchaseHeader.getStatus().getStatus().equals("in progress")) {
            dataSupplyService.dataSupplyForPurchaseHeader(modelAndView);
        } else {
            modelAndView.setViewName("redirect:/web/purchase/purchase-header/in-progress-list");
        }
        return modelAndView;

    }


    @GetMapping("/purchase/purchase-header/promote/{purchaseHeaderId}")
    public ModelAndView promotePurchaseRequest(@PathVariable Integer purchaseHeaderId) {
        ModelAndView modelAndView = new ModelAndView("redirect:/web/purchase/purchase-header/issued-list");
        PurchaseHeaderEntity purchaseHeader = purchaseHeaderRepository.findById(purchaseHeaderId).get();
        List<PurchaseLineEntity> purchaseLines = purchaseLineRepository.findAllByPurchaseHeader(purchaseHeader);
        if(purchaseLines.isEmpty()) {
            modelAndView.setViewName("redirect:/web/purchase/purchase-request/" + purchaseHeader.getPurchaseHeaderId());
        }
        else {
            StatusEntity status = statusRepository.findByStatus("issued");
            purchaseHeader.setStatus(status);
            purchaseHeaderRepository.save(purchaseHeader);

            List<UserEntity> userEntityList = userRepository.findAllUserByAuthority("ACCOUNTANT");
            String[] to = new String[userEntityList.size()];
            for(int i = 0; i<userEntityList.size();i++) {
                to[i] = userEntityList.get(i).getEmail();
            }

            String action = statusRepository.findByStatus("checked").getStatus() ;
            emailBusinessService.purchaseRequestPromotionToAccounting(to,purchaseHeader.getPurchaseNumber(),action);

            String emailOfInitiator = purchaseHeader.getUser().getEmail();
            emailBusinessService.emailInitiatorNotification(emailOfInitiator,purchaseHeader.getPurchaseNumber(), status.getStatus());
        }

        return modelAndView;
    }

}
