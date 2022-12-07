package com.example.apidatn.controller

import com.example.apidatn.dto.PayDto
import com.example.apidatn.service.PayService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value=["/pay"],produces = ["application/json;charset=UTF-8"])
class PayController {
    @Autowired
    private lateinit var payService: PayService

    @GetMapping("")
    fun listPay(): ResponseEntity<MutableList<PayDto>>
    =ResponseEntity.ok(payService.listPay())
}