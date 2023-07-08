package com.ecommerce.aafrincosmetics.controller.admincontroller;


import com.ecommerce.aafrincosmetics.entity.Order;
import com.ecommerce.aafrincosmetics.repo.OrderRepo;
import com.ecommerce.aafrincosmetics.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminOrderController {
    private final OrderRepo orderRepo;

    @GetMapping("/manage-order")
    public String getManageOrderPage(Model model){
        model.addAttribute("allOrders", orderRepo.findAll());
        return "adminPages/manageOrder";
    }

    @GetMapping("/update-order-status/{id}")
    public String getUpdateOrderForm(@PathVariable("id") Integer id, Model model){
        model.addAttribute("dto", orderRepo.findById(id).get());
        return "adminPages/updateOrderStatus";
    }

    @PostMapping("/update-order-status/{id}")
    public String updateTheOrders(@PathVariable("id") Integer id,
                                  @ModelAttribute Order order )
            {
        Order foundOrder = orderRepo.findById(id).get();
        foundOrder.setStatus(order.getStatus());
        orderRepo.save(foundOrder);
        return "redirect:/admin/manage-order";

    }


}
