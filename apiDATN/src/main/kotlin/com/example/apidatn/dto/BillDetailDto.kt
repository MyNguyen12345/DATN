package com.example.apidatn.dto

import com.example.apidatn.model.Bill
import com.example.apidatn.model.Product
import javax.persistence.*

data class BillDetailDto (
        var billDetailId:Int?=null,
        var productId:Int?=null,
        var amountBuy:Int?=null,
        var moneyNow: Float?=null,
        var money:Float?=null,
//        var bill: BillDto?=null,
        var product: ProductDto?=null,
)
