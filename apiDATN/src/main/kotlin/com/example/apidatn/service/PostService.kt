package com.example.apidatn.service

import com.example.apidatn.dto.NewPostDto
import com.example.apidatn.dto.PostDto
import com.example.apidatn.dto.ProductDto

interface PostService {
    fun newPost(newPostDto:NewPostDto):Boolean
    fun statusProduct():MutableList<ProductDto>
    fun postByProductId(productId:Int):PostDto
}