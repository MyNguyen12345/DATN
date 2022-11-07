package com.example.apidatn.repository

import com.example.apidatn.model.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository:JpaRepository<Product,Int> {
    fun findAllByUserId(userId:Int):MutableList<Product>
    fun findAllByCategoryDetailId(categoryDetailId:Int):MutableList<Product>

    @Query(value = "SELECT product.* FROM product join post on product.product_id=post.product_id where post.post_status='active'",nativeQuery = true)
    fun findAllProductByPostStatus():MutableList<Product>
}