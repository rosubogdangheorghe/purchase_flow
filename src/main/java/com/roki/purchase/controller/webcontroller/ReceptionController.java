package com.roki.purchase.controller.webcontroller;

import com.roki.purchase.entity.PurchaseHeaderEntity;
import com.roki.purchase.entity.PurchaseLineEntity;
import com.roki.purchase.entity.StatusEntity;
import com.roki.purchase.repository.PurchaseHeaderRepository;
import com.roki.purchase.repository.PurchaseLineRepository;
import com.roki.purchase.repository.StatusRepository;
import com.roki.purchase.service.DataSupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/web")
public class ReceptionController {

    @Autowired
    private PurchaseHeaderRepository purchaseHeaderRepository;

    @Autowired
    private PurchaseLineRepository purchaseLineRepository;

    @Autowired
    private StatusRepository statusRepository;


    @Autowired
    private DataSupplyService dataSupplyService;



    @GetMapping("/purchase/reception/pending-reception-list")
    public ModelAndView getAllPendingReceptions() {
        ModelAndView modelAndView = new ModelAndView("/purchase/reception/reception-purchase-requests-list");

        String status = "approved";
        dataSupplyService.purchaseHeaderDataByLoggedUserIdAndStatus(modelAndView, status);

        modelAndView.addObject("editAndPromoteButtons", true);

        return modelAndView;
    }



    @GetMapping("/purchase/reception/reception-list")
    public ModelAndView getAllReceptions() {
        ModelAndView modelAndView = new ModelAndView("/purchase/reception/reception-purchase-requests-list");

        String status = "received";

        dataSupplyService.purchaseHeaderDataByLoggedUserIdAndStatus(modelAndView, status);

        modelAndView.addObject("editAndPromoteButtons", false);
        return modelAndView;
    }

    @GetMapping("/purchase/reception/reception-purchase-request/{purchaseHeaderId}")
    public ModelAndView performReception(@PathVariable Integer purchaseHeaderId){
        ModelAndView modelAndView = new ModelAndView("/purchase/reception/reception-purchase-request");

        dataSupplyService.dataSupplyForPurchaseRequest(purchaseHeaderId,modelAndView);

        modelAndView.addObject("receptionCheckForm",false);

        return modelAndView;
    }

    @GetMapping("/purchase/reception/reception-purchase-line/edit/{purchaseHeaderId}/{purchaseLineId}")
    public ModelAndView editPurchaseLineReception(@PathVariable Integer purchaseHeaderId, @PathVariable Integer purchaseLineId) {

        ModelAndView modelAndView = new ModelAndView("/purchase/reception/reception-purchase-request");
        modelAndView.addObject("receptionCheckForm",true);

        dataSupplyService.dataSupplyForPurchaseRequest(purchaseHeaderId,modelAndView);

        dataSupplyService.dataSupplyForPurchaseLine(purchaseLineId,modelAndView);


        return modelAndView;
    }

    @PostMapping("/purchase/reception/reception-purchase-line/save")
    public ModelAndView saveReceivedPurchaseLine(@ModelAttribute("purchaseLineObject") PurchaseLineEntity purchaseLine, HttpServletRequest request) {

        String string = request.getParameter("purchaseHeaderId");
        Integer purchaseHeaderId = Integer.parseInt(string);
        PurchaseHeaderEntity purchaseHeader = purchaseHeaderRepository.findByPurchaseHeaderId(purchaseHeaderId);
        purchaseLine.setPurchaseHeader(purchaseHeader);

        purchaseLineRepository.save(purchaseLine);

        ModelAndView modelAndView = new ModelAndView("redirect:/web/purchase/reception/reception-purchase-request/" + purchaseHeader.getPurchaseHeaderId());

        return modelAndView;
    }

    @GetMapping("/purchase/reception/promote/{purchaseHeaderId}")
    ModelAndView promotePurchaseRequest(@PathVariable Integer purchaseHeaderId) {
        ModelAndView modelAndView = new ModelAndView("redirect:/web/purchase/reception/reception-list");

        PurchaseHeaderEntity purchaseHeader = purchaseHeaderRepository.findByPurchaseHeaderId(purchaseHeaderId);

        List<PurchaseLineEntity> purchaseLines = purchaseLineRepository.findAllByPurchaseHeaderAndReceivedQuantityNotNullAndReceivedPriceNotNull(purchaseHeader);
        if(purchaseLines.isEmpty()) {
            modelAndView.setViewName("redirect:/web/purchase/reception/reception-purchase-request" + purchaseHeader.getPurchaseHeaderId());
        } else {
            StatusEntity status = statusRepository.findByStatus("received");
            purchaseHeader.setStatus(status);
            purchaseHeaderRepository.save(purchaseHeader);
        }

        return modelAndView;
    }
}
