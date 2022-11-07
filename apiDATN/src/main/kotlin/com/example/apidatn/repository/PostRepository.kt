package com.example.apidatn.repository

import com.example.apidatn.model.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository:JpaRepository<Post,Int> {
    fun findPostByProductId(productId:Int):Post
}