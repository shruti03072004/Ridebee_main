package com.coderdot.controllers;

import com.coderdot.services.PaymentService;
import com.razorpay.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create-order")
    public String createOrder(@RequestParam int amount) throws Exception {
        Order order = paymentService.createOrder(amount);
        return order.toString();
    }
}