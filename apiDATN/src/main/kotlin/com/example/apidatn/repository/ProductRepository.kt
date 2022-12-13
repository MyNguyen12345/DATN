package com.example.apidatn.repository

import com.example.apidatn.model.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Int> {
    fun findAllByUserId(userId: Int): MutableList<Product>
    fun findAllByCategoryDetailId(categoryDetailId: Int): MutableList<Product>

    @Query(value = "select *from product where  product.user_id =:userId  and upper (product_name) like %:search%",nativeQuery = true)
    fun searchByUserId(userId: Int, search: String): MutableList<Product>

    @Query(value = "select product.* from product join user_info  on product.user_id =user_info.user_id where not user_info.phone =?", nativeQuery = true)
    fun listProduct(phone: Int): MutableList<Product>

    @Query(value = "select product.* from product join user_info  on product.user_id = user_info.user_id where not user_info.phone =:phone and lower(product.product_name) like '%'||:search||'%'", nativeQuery = true)
    fun searchListProduct(phone: Int, search: String?): MutableList<Product>

    @Query(value = "SELECT product.* FROM product join post on product.product_id=post.product_id where post.post_status='active'", nativeQuery = true)
    fun findAllProductByPostStatus(): MutableList<Product>

}