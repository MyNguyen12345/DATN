package com.example.apidatn.service

import com.example.apidatn.dto.BillDto
import com.example.apidatn.dto.BillPayDto

interface BillService {
    fun postBill(billPayDto: BillPayDto):Boolean
    fun getBillUserId(userId:Int,billStatusId: Int):MutableList<BillDto>
    fun getBillStatus(billStatusId:Int):List<BillDto>
    fun updateBillStatus(billStatusId: Int,billId:Int):Boolean
}