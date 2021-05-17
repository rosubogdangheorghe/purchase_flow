package com.roki.purchase.controller.webcontroller;

import com.roki.purchase.entity.BudgetLineEntity;
import com.roki.purchase.entity.PurchaseHeaderEntity;
import com.roki.purchase.entity.PurchaseLineEntity;
import com.roki.purchase.entity.StatusEntity;
import com.roki.purchase.repository.BudgetLineRepository;
import com.roki.purchase.repository.PurchaseHeaderRepository;
import com.roki.purchase.repository.PurchaseLineRepository;
import com.roki.purchase.repository.StatusRepository;
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
    private BudgetLineRepository budgetLineRepository;


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

        dataSupplyForPurchaseRequest(purchaseHeaderId, modelAndView);

        modelAndView.addObject("accountingCheckForm", false);
        modelAndView.addObject("purchaseLineObject", new PurchaseLineEntity());

        return modelAndView;
    }

    @GetMapping("/accounting/accounting-purchase-line/edit/{purchaseHeaderId}/{purchaseLineId}")
    ModelAndView editPurchaseLineForAccountingCheck(@PathVariable Integer purchaseLineId, @PathVariable Integer purchaseHeaderId) {

        ModelAndView modelAndView = new ModelAndView("/purchase/accounting/accounting-purchase-request");
        modelAndView.addObject("accountingCheckForm", true);

        dataSupplyForPurchaseRequest(purchaseHeaderId, modelAndView);


        PurchaseLineEntity purchaseLine = purchaseLineRepository.findById(purchaseLineId).get();
        modelAndView.addObject("purchaseLineObject", purchaseLine);

        List<BudgetLineEntity> budgetLines = budgetLineRepository.findAll();
        modelAndView.addObject("budgetLines", budgetLines);


        return modelAndView;

    }


    private void dataSupplyForPurchaseRequest(@PathVariable Integer purchaseHeaderId, ModelAndView modelAndView) {
        PurchaseHeaderEntity purchaseHeader = purchaseHeaderRepository.findById(purchaseHeaderId).get();
        modelAndView.addObject("purchaseHeaderObject", purchaseHeader);

        List<PurchaseLineEntity> purchaseLines = purchaseLineRepository.findAllByPurchaseHeader(purchaseHeader);
        modelAndView.addObject("purchaseLines", purchaseLines);

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
            modelAndView.setViewName("redirect:/web/accounting-purchase-request/" + purchaseHeader.getPurchaseHeaderId());
        } else {
            StatusEntity status = statusRepository.findByStatus("checked");
            purchaseHeader.setStatus(status);
            purchaseHeaderRepository.save(purchaseHeader);
        }
        return modelAndView;
    }
}
