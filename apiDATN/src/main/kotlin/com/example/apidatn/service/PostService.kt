package com.example.apidatn.service

import com.example.apidatn.dto.*
import org.springframework.web.multipart.MultipartFile

interface PostService {
    fun newPost(userId:Int,postStatus:String,categoryDetailId:Int,productName:String,
    productStatus:String,avatar:MultipartFile,description:String,amountProduct:Int,priceProduct :Float, priceDeposit:Float,listImage:MutableList<MultipartFile>):Boolean
    fun statusProduct():MutableList<ProductDto>
    fun postByProductId(productId:Int):PostDto
    fun listPostStatus():MutableList<PostStatusDto>
    fun updatePostStatus(productId: Int,postStatus: String):Boolean
}
