package com.example.apidatn.service

import com.example.apidatn.dto.ProductDto
import org.springframework.web.multipart.MultipartFile

interface ProductService {
    fun getAllProduct():MutableList<ProductDto>
    fun getProductById(productId:Int):ProductDto
    fun findAllByUserId(userId:Int):MutableList<ProductDto>
    fun findAllByCategoryDetailId(categoryDetailId:Int):MutableList<ProductDto>
    fun addProduct(productDto: ProductDto):Boolean
    fun updateProduct(productDto: ProductDto,productId: Int):Boolean
    fun saveImage(productId:Int, imageFile: MultipartFile):Boolean
    fun saveListImage(productId: Int,listImage:MutableList<MultipartFile>):Boolean
}