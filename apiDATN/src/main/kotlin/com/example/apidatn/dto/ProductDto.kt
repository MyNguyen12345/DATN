package com.example.apidatn.dto

import com.example.apidatn.model.CategoryDetail
import com.example.apidatn.model.Image
import javax.persistence.*

data class ProductDto (
        var productId:Int?=null,
        var userId:Int?=null,
        var categoryDetailId:Int?=null,
        var productName:String?=null,
        var productStatus:String?=null,
        var avatar:String?=null,
        var description:String?=null,
        var amountProduct:Int?=null,
        var priceProduct :Float?=null,
        var priceDeposit:Float?=null,
        var rating:Float?=null,
        var userRating:Int?=null,
        var postStatus:String="cho",
        var listImage:MutableList<ImageDto>?=null

        )