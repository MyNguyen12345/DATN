package com.example.apidatn.service

import com.example.apidatn.dto.BillDetailDto

interface BillDetailService {
    fun listBillDetail(billId:Int):List<BillDetailDto>
}