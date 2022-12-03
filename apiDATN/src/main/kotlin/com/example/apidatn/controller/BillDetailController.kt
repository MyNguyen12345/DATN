package com.example.apidatn.controller

import com.example.apidatn.dto.BillDetailDto
import com.example.apidatn.service.BillDetailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value=["/bill/detail"],produces = ["application/json;charset=UTF-8"])
class BillDetailController {

    @Autowired
    private lateinit var billDetailService: BillDetailService

    @GetMapping("/{id}")
    fun listBillDetail(@PathVariable("id") billId:Int):ResponseEntity<List<BillDetailDto>>
    = ResponseEntity.ok(billDetailService.listBillDetail(billId))
}