package com.example.apidatn.dto

import com.example.apidatn.model.BillDetail
import com.example.apidatn.model.BillStatus
import com.example.apidatn.model.Pay
import java.util.*
import javax.persistence.*

data  class BillDto(
        var billId:Int?=null,
        var billStatusId:Int?=null,
        var userId:Int?=null,
        var dateBill: Date?=null,
        var totalPrice:Float?=null,
        var addressBill:String?=null,
        var pay_id:Int?=null,
        var pay_status:String?=null,
        var listBillDetail:MutableList<BillDetailDto>?=null,
        var pay: PayDto?=null,
        var userInfoDto: UserInfoDto?=null
)