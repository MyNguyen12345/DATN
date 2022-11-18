package com.example.apidatn.dto

data class PayOderDto (
        var price:Double?=null,
        var currency:String?=null,
        var method:String?=null,
        var intent:String?=null,
        var description:String?=null
        )