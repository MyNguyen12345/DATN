package com.example.apidatn.dto

data class BillPayDto(
        var listProductId:List<ProductIdDto>?=null,
        var ListAmountBuy:List<AmountBuyDto>?=null,
        var addressBill:String?=null,
        var payId:Int?=null,
        var userId:Int?=null,
        var priceTotal:Float?=null

        )