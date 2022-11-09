package com.example.apidatn.repository

import com.example.apidatn.model.Cart
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CartRepository :JpaRepository<Cart,Int>{
    fun findAllByUserId(userId:Int):MutableList<Cart>

    @Query(value = "SELECT COUNT(user_id) FROM cart WHERE user_id=?",nativeQuery = true)
    fun getAmountCart(userId: Int):Int
}