package com.coderdot.services;

import com.razorpay.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;


@Service
public class PaymentService {

//    @Value("${razorpay.key}")
    private String key = "rzp_test_SDzYuoGkZunvpH";

//    @Value("${razorpay.secret}")
    private String secret= "NNYIxOQ1RlCXcwpEISqwMtYU";

    // ✅ MOVE HERE
    @PostConstruct
    public void test() {
        System.out.println("KEY = " + key);
        System.out.println("SECRET = " + secret);
    }

    public Order createOrder(int amount) throws RazorpayException {

        RazorpayClient client = new RazorpayClient(key, secret);

        JSONObject options = new JSONObject();
        options.put("amount", amount * 100); // rupees → paise
        options.put("currency", "INR");
        options.put("receipt", "order_rcptid_11");

        return client.orders.create(options);
    }
}