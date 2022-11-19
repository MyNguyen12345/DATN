package com.example.apidatn.dto

import com.example.apidatn.model.CategoryDetail
import java.util.*

data class NewPostDto (
//        var postId:Int?=null,
//        var postDate: Date?=null,
        var userId:Int?=null,
        var productId:Int?=null,
        var postStatus:String?=null,
        var categoryDetailId:Int?=null,
        var productName:String?=null,
        var productStatus:String?=null,
        var avatar:String?=null,
        var description:String?=null,
        var amountProduct:Int?=null,
        var priceProduct :Int?=null,
        var priceDeposit:Int?=null,
        var listImage:MutableList<ImageDto>?=null
    )