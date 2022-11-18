package com.example.apidatn.controller

import com.example.apidatn.service.PayPalService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
//@RequestMapping("/api/pay")
class PayPalController {

    @Autowired
    private lateinit var payPalService: PayPalService

    @PostMapping("/paypal")
    fun pay(@RequestParam ("totalPrice") totalPrice:Double):String{
        val payment = payPalService.createPayment(totalPrice,"USD","paypal","sale","paying","http://localhost:8080/api/pay/cancel","http://localhost:8080/api/pay/success")
        if (payment != null) {
            for (link in payment.links) {
                if (link.rel=="approval_url") {
                    return  link.href
                }
            }
        }
        return "redirect:http://localhost:8080/api/pay/cancel"
    }
}