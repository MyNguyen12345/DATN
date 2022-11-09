package com.example.apidatn.dto



data class CartDto (
        var cartId :Int ?= null,
        var userId:Int?=null,
        var productId:Int?=null,
        var amountProduct:Int?=null,
        var product: ProductDto?=null,
        var user: UserInfoDto?=null
        )