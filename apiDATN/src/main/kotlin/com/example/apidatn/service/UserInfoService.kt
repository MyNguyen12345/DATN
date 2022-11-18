package com.example.apidatn.service

import com.example.apidatn.dto.AccountDto
import com.example.apidatn.dto.UserInfoDto
import com.example.apidatn.model.User

interface UserInfoService {
    fun getAllUser():MutableList<UserInfoDto>
    fun getUserById(userId:Int):UserInfoDto
    fun updateUser(userId: Int,userInfoDto: UserInfoDto):Boolean
    fun getAllUserByStatus():MutableList<UserInfoDto>
    fun getAllUserRole():MutableList<UserInfoDto>
}