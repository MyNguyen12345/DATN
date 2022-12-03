package com.example.apidatn.controller

import com.example.apidatn.dto.BillDto
import com.example.apidatn.dto.BillPayDto
import com.example.apidatn.dto.CartIdDto
import com.example.apidatn.service.BillService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value=["/bill"],produces = ["application/json;charset=UTF-8"])
class BillController {
    @Autowired
    private lateinit var billService: BillService

    @GetMapping("/{id}")
    fun getBillUserId(@PathVariable("id") userId:Int):ResponseEntity<BillDto>
    =ResponseEntity.ok(billService.getBillUserId(userId))

    @PostMapping("")
    fun postBill(@RequestBody billPayDto: BillPayDto):ResponseEntity<Boolean>
    = ResponseEntity.ok(billService.postBill(billPayDto))
}