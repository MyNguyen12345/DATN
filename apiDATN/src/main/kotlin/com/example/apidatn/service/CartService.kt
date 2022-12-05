package com.example.apidatn.service

import com.example.apidatn.dto.CartDto
import com.example.apidatn.dto.CartIdDto
import com.example.apidatn.dto.DeleteDto
import com.example.apidatn.dto.ListCartDto

interface CartService {
    fun addCart(userId:Int,cartDto: CartDto):Boolean
    fun getAllCart(userId: Int):MutableList<ListCartDto>
    fun getAmountCart(userId: Int):Int
    fun deleteCart(cartId:Int):Boolean
    fun payListCart(listCartId:List<CartIdDto>):Float
}