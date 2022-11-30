package com.example.apidatn.service

import com.example.apidatn.dto.CartDto
import com.example.apidatn.dto.DeleteDto

interface CartService {
    fun addCart(userId:Int,cartDto: CartDto):Boolean
    fun getAllCart(userId: Int):MutableList<CartDto>
    fun getAmountCart(userId: Int):Int
    fun deleteCart(cartId:Int):DeleteDto
}