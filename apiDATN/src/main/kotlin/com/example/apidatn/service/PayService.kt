package com.example.apidatn.service

import com.example.apidatn.dto.PayDto

interface PayService {
    fun listPay():MutableList<PayDto>
}