package com.roki.purchase.controller.webcontroller;

import com.roki.purchase.entity.PurchaseHeaderEntity;
import com.roki.purchase.entity.PurchaseLineEntity;
import com.roki.purchase.entity.StatusEntity;
import com.roki.purchase.repository.PurchaseHeaderRepository;
import com.roki.purchase.repository.PurchaseLineRepository;
import com.roki.purchase.repository.StatusRepository;
import com.roki.purchase.service.DataSupplyService;
import com.roki.purchase.service.EmailBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/web")
public class AccountingController {

    @Autowired
    private PurchaseHeaderRepository purchaseHeaderRepository;

    @Autowired
    private PurchaseLineRepository purchaseLineRepository;

    @Autowired
    private StatusRepository statusRepository;


    @Autowired
    private DataSupplyService dataSupplyService;

    @Autowired
    private EmailBusinessService emailBusinessService;


    @GetMapping("/accounting/issued-list")
    public ModelAndView getAllIssuedPurchaseRequests() {

        ModelAndView modelAndView = new ModelAndView("/purchase/accounting/accounting-purchase-requests-list");

        List<PurchaseHeaderEntity> purchaseHeadersList = purchaseHeaderRepository.findAllByStatusEquals(statusRepository.findByStatus("issued"));
        modelAndView.addObject("purchaseHeadersList", purchaseHeadersList);
        modelAndView.addObject("editAndPromoteButtons", true);

        return modelAndView;
    }

    @GetMapping("/accounting/checked-list")
    public ModelAndView getAllCheckedPurchaseRequests() {
        ModelAndView modelAndView = new ModelAndView("/purchase/accounting/accounting-purchase-requests-list");
        List<PurchaseHeaderEntity> purchaseHeadersList = purchaseHeaderRepository.findAllByStatusEquals(statusRepository.findByStatus("checked"));
        modelAndView.addObject("purchaseHeadersList", purchaseHeadersList);
        modelAndView.addObject("editAndPromoteButtons", false);
        return modelAndView;
    }

    @GetMapping("/accounting/accounting-purchase-request/{purchaseHeaderId}")
    public ModelAndView performAccountingCheck(@PathVariable Integer purchaseHeaderId) {
        ModelAndView modelAndView = new ModelAndView("/purchase/accounting/accounting-purchase-request");

        dataSupplyService.dataSupplyForPurchaseRequest(purchaseHeaderId, modelAndView);

        modelAndView.addObject("accountingCheckForm", false);
        modelAndView.addObject("purchaseLineObject", new PurchaseLineEntity());

        return modelAndView;
    }

    @GetMapping("/accounting/accounting-purchase-line/edit/{purchaseHeaderId}/{purchaseLineId}")
    ModelAndView editPurchaseLineForAccountingCheck(@PathVariable Integer purchaseLineId, @PathVariable Integer purchaseHeaderId) {

        ModelAndView modelAndView = new ModelAndView("/purchase/accounting/accounting-purchase-request");
        modelAndView.addObject("accountingCheckForm", true);

        dataSupplyService.dataSupplyForPurchaseRequest(purchaseHeaderId, modelAndView);

        dataSupplyService.dataSupplyForPurchaseLine(purchaseLineId, modelAndView);

        return modelAndView;

    }


    @PostMapping("/accounting/accounting-purchase-line/save")
    public ModelAndView savePurchaseLine(@ModelAttribute("purchaseLineObject") PurchaseLineEntity purchaseLine, HttpServletRequest request) {

        String string = request.getParameter("purchaseHeaderId");
        int purchaseHeaderId = Integer.parseInt(string);

        PurchaseHeaderEntity purchaseHeader = purchaseHeaderRepository.findByPurchaseHeaderId(purchaseHeaderId);
        purchaseLine.setPurchaseHeader(purchaseHeader);
        purchaseLineRepository.save(purchaseLine);

        ModelAndView modelAndView = new ModelAndView("redirect:/web/accounting/accounting-purchase-request/" + purchaseHeader.getPurchaseHeaderId());
        return modelAndView;
    }

    @GetMapping("/accounting/promote/{purchaseHeaderId}")
    public ModelAndView promotePurchaseRequest(@PathVariable Integer purchaseHeaderId) {
        ModelAndView modelAndView = new ModelAndView("redirect:/web/accounting/checked-list");

        PurchaseHeaderEntity purchaseHeader = purchaseHeaderRepository.findByPurchaseHeaderId(purchaseHeaderId);
        List<PurchaseLineEntity> purchaseLines = purchaseLineRepository.findAllByPurchaseHeaderAndIsBudgetedIsNotNullAndBudgetLineNotNull(purchaseHeader);

        if (purchaseLines.isEmpty()) {
            modelAndView.setViewName("redirect:/web/accounting/accounting-purchase-request/" + purchaseHeader.getPurchaseHeaderId());
        } else {
            StatusEntity status = statusRepository.findByStatus("checked");
            purchaseHeader.setStatus(status);
            purchaseHeaderRepository.save(purchaseHeader);


            String to = purchaseHeader.getDepartment().getUser().getEmail();
            System.out.println(to);
            String action = statusRepository.findByStatus("approved").getStatus();
            emailBusinessService.purchaseRequestPromotionToManager(to,purchaseHeader.getPurchaseNumber(),action);

            String emailOfInitiator = purchaseHeader.getUser().getEmail();
            emailBusinessService.emailInitiatorNotification(emailOfInitiator,purchaseHeader.getPurchaseNumber(), status.getStatus());

        }
        return modelAndView;
    }
}
