package com.example.apidatn.service

import com.example.apidatn.dto.ProductDto

interface ProductService {
    fun getAllProduct():MutableList<ProductDto>
    fun getProductById(productId:Int):ProductDto
    fun findAllByUserId(userId:Int):MutableList<ProductDto>
    fun findAllByCategoryDetailId(categoryDetailId:Int):MutableList<ProductDto>
    fun addProduct(productDto: ProductDto):Boolean
    fun updateProduct(productDto: ProductDto,productId: Int):Boolean
}