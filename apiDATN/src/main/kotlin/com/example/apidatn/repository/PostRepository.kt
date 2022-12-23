package com.example.apidatn.repository

import com.example.apidatn.model.Post
import com.example.apidatn.model.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PostRepository:JpaRepository<Post,Int> {
    fun findPostByProductId(productId:Int):Optional<Post>

    @Query(value = "select distinct  product.user_id  from product join post on product.product_id = post.product_id  where post.post_status ='Chờ xác nhận'",nativeQuery = true)
    fun postByUserId():MutableList<Int>
}