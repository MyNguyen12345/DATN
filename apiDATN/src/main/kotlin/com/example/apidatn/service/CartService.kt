package com.example.apidatn.service

import com.example.apidatn.dto.CartDto

interface CartService {
    fun addCart(userId:Int,cartDto: CartDto):Boolean
    fun getAllCart(userId: Int):MutableList<CartDto>
    fun getAmountCart(userId: Int):Int
}