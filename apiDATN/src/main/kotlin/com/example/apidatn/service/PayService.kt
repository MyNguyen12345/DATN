package com.example.apidatn.service

import com.example.apidatn.dto.PayDto

interface PayService {
    fun getAllPay():MutableList<PayDto>
    fun getPayById(payId:Int):PayDto
}