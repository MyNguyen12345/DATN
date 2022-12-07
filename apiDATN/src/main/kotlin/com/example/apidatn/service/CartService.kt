package com.example.apidatn.service

import com.example.apidatn.dto.CartDto
import com.example.apidatn.dto.ProductIdDto
import com.example.apidatn.dto.ListCartDto

interface CartService {
    fun addCart(userId:Int,cartDto: CartDto):Boolean
    fun getAllCart(userId: Int):MutableList<ListCartDto>
    fun getAmountCart(userId: Int):Int
    fun deleteCart(cartId:Int):Boolean
    fun payListCart(listCartId:List<ProductIdDto>):Float
}