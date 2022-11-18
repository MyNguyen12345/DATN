package com.example.apidatn.service

import com.paypal.api.payments.*
import com.paypal.base.rest.APIContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode


@Service
class PayPalService {

    @Autowired
    private lateinit var apiContext: APIContext

    fun createPayment(
            total: Double?,
            currency: String?,
            method: String,
            intent: String,
            description: String?,
            cancelUrl: String?,
            successUrl: String?): Payment? {
        var total = total
        val amount = Amount()
        amount.currency = currency
        total = BigDecimal(total!!).setScale(2, RoundingMode.HALF_UP).toDouble()
        amount.total = String.format("%.2f", total)
        val transaction = Transaction()
        transaction.description = description
        transaction.amount = amount
        val transactions: MutableList<Transaction> = ArrayList()
        transactions.add(transaction)
        val payer = Payer()
        payer.paymentMethod = method
        val payment = Payment()
        payment.intent = intent
        payment.payer = payer
        payment.transactions = transactions
        val redirectUrls = RedirectUrls()
        redirectUrls.cancelUrl = cancelUrl
        redirectUrls.returnUrl = successUrl
        payment.redirectUrls = redirectUrls
        return payment.create(apiContext)
    }

    fun executePayment(paymentId: String?, payerId: String?): Payment? {
        val payment = Payment()
        payment.id = paymentId
        val paymentExecute = PaymentExecution()
        paymentExecute.payerId = payerId
        return payment.execute(apiContext, paymentExecute)
    }
}