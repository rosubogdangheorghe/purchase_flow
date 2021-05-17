package com.roki.purchase.controller.webcontroller;


import com.roki.purchase.entity.PurchaseHeaderEntity;
import com.roki.purchase.entity.PurchaseLineEntity;
import com.roki.purchase.entity.StatusEntity;
import com.roki.purchase.repository.BudgetLineRepository;
import com.roki.purchase.repository.PurchaseHeaderRepository;
import com.roki.purchase.repository.PurchaseLineRepository;
import com.roki.purchase.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/web")
public class ManagementController {

    @Autowired
    private PurchaseHeaderRepository purchaseHeaderRepository;

    @Autowired
    private PurchaseLineRepository purchaseLineRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private BudgetLineRepository budgetLineRepository;

    @GetMapping("/management/checked-list")
    public ModelAndView getAllCheckedPurchaseRequest() {
        ModelAndView modelAndView = new ModelAndView("/purchase/management/management-purchase-requests-list");

        List<PurchaseHeaderEntity> purchaseHeadersList = purchaseHeaderRepository.findAllByStatusEquals(statusRepository.findByStatus("checked"));
        modelAndView.addObject("purchaseHeadersList", purchaseHeadersList);
        modelAndView.addObject("editAndPromoteButtons", true);
        return modelAndView;
    }

    @GetMapping("/management/approved-list")
    public ModelAndView getAllApprovedPurchaseRequests() {

        ModelAndView modelAndView = new ModelAndView("/purchase/management/management-purchase-requests-list");
        List<PurchaseHeaderEntity> purchaseHeadersList = purchaseHeaderRepository.findAllByStatusEquals(
                statusRepository.findByStatus("approved"));

        modelAndView.addObject("purchaseHeadersList", purchaseHeadersList);
        modelAndView.addObject("editAndPromoteButtons", false);
        return modelAndView;
    }

    @GetMapping("/management/rejected-list")
    public ModelAndView getAllRejectedPurchaseRequests() {

        ModelAndView modelAndView = new ModelAndView("/purchase/management/management-purchase-requests-list");
        List<PurchaseHeaderEntity> purchaseHeadersList = purchaseHeaderRepository.findAllByStatusEquals(
                statusRepository.findByStatus("rejected"));

        modelAndView.addObject("purchaseHeadersList", purchaseHeadersList);
        modelAndView.addObject("editAndPromoteButtons", false);
        return modelAndView;
    }

    @GetMapping("/management/management-purchase-request/{purchaseHeaderId}")
    public ModelAndView performManagementCheck(@PathVariable Integer purchaseHeaderId) {
        ModelAndView modelAndView = new ModelAndView("/purchase/management/management-purchase-request");

        PurchaseHeaderEntity purchaseHeader = purchaseHeaderRepository.findById(purchaseHeaderId).get();
        modelAndView.addObject("purchaseHeaderObject", purchaseHeader);

        List<PurchaseLineEntity> purchaseLines = purchaseLineRepository.findAllByPurchaseHeader(purchaseHeader);
        modelAndView.addObject("purchaseLines", purchaseLines);
        return modelAndView;
    }


    @GetMapping("/management/approve/{purchaseHeaderId}")
    public ModelAndView approvePurchaseRequest(@PathVariable Integer purchaseHeaderId) {
        ModelAndView modelAndView = new ModelAndView("redirect:/web/management/approved-list");
        PurchaseHeaderEntity purchaseHeader = purchaseHeaderRepository.findByPurchaseHeaderId(purchaseHeaderId);

        StatusEntity status = statusRepository.findByStatus("approved");
        purchaseHeader.setStatus(status);
        purchaseHeaderRepository.save(purchaseHeader);
        return modelAndView;
    }

    @GetMapping("/management/reject/{purchaseHeaderId}")
    public ModelAndView rejectPurchaseHeader(@PathVariable Integer purchaseHeaderId) {
        ModelAndView modelAndView = new ModelAndView("redirect:/web/management/rejected-list");
        PurchaseHeaderEntity purchaseHeader = purchaseHeaderRepository.findByPurchaseHeaderId(purchaseHeaderId);

        StatusEntity status = statusRepository.findByStatus("rejected");
        purchaseHeader.setStatus(status);
        purchaseHeaderRepository.save(purchaseHeader);
        return modelAndView;
    }

}
