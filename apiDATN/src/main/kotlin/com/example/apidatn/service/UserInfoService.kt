package com.example.apidatn.service

import com.example.apidatn.dto.AccountDto
import com.example.apidatn.dto.UserCountDto
import com.example.apidatn.dto.UserInfoDto
import com.example.apidatn.model.User
import org.springframework.web.multipart.MultipartFile

interface UserInfoService {
    fun getAllUser():MutableList<UserInfoDto>
    fun getUserById(userId:Int):UserInfoDto
    fun updateUser(userId: Int,userInfoDto: UserInfoDto):Boolean
    fun getAllUserByStatus():MutableList<UserInfoDto>
    fun getAllUserRole():MutableList<UserInfoDto>
    fun updateUserStatus(userId: Int, accountStatus:String):Boolean
    fun updateImage(userId:Int,userImage:MultipartFile):Boolean
    fun countUser():UserCountDto
    fun userByPhone(phone:Int):UserInfoDto
}