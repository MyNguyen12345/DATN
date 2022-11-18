package com.example.apidatn.controller

import com.example.apidatn.dto.PayDto
import com.example.apidatn.service.PayPalService
import com.example.apidatn.service.PayService
import com.paypal.api.payments.Payment
import com.paypal.base.rest.PayPalRESTException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("api/pay")
class PayController {

    @Autowired
    private lateinit var payService: PayService

    @Autowired
    private lateinit var payPalService: PayPalService

    @GetMapping()
    fun getAllPay():ResponseEntity<MutableList<PayDto>> =ResponseEntity.ok(payService.getAllPay())

    @GetMapping("/{id}")
    fun getPayById(@PathVariable("id") payId:Int):ResponseEntity<PayDto>
    = ResponseEntity.ok(payService.getPayById(payId))

    @GetMapping("/cancel")
    fun cancelPay() :ResponseEntity<Boolean> = ResponseEntity.ok(false)

    @GetMapping("/success")
    fun successPay(@RequestParam("paymentId") paymentId: String?, @RequestParam("PayerID") payerId: String?): ResponseEntity<Boolean> {
        try {
            val payment: Payment? = payPalService.executePayment(paymentId, payerId)
            if (payment != null) {
                if (payment.state == "approved") {
                    return ResponseEntity.ok(true)
                }
            }
        } catch (e: PayPalRESTException) {
            println(e.message)
        }
        return ResponseEntity.ok(false)
    }

}