package com.example.apidatn.service

import com.example.apidatn.dto.AccountDto

interface RegisterService {

    fun registerAccount(accountDto: AccountDto):Boolean
    fun forgotPassword(phone:Int,pass:String): Boolean
    fun findByPhone(phone: Int):Boolean
}