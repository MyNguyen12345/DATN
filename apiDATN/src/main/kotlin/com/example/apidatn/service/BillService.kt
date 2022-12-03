package com.example.apidatn.service

import com.example.apidatn.dto.BillDto
import com.example.apidatn.dto.BillPayDto
import com.example.apidatn.dto.CartIdDto

interface BillService {
    fun postBill(billPayDto: BillPayDto):Boolean
    fun getBillUserId(userId:Int):BillDto
}