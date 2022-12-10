package com.example.apidatn.controller

import com.example.apidatn.dto.BillStatusDto
import com.example.apidatn.service.BillStatusService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value=["/bill/status"],produces = ["application/json;charset=UTF-8"])
class BillStatusController {

    @Autowired
    private  lateinit var  billStatusService: BillStatusService

    @GetMapping("")
    fun getListBillStatus():ResponseEntity<MutableList<BillStatusDto>>
    = ResponseEntity.ok(billStatusService.getBillStatus())
}