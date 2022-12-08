package com.example.apidatn.service

import com.example.apidatn.dto.StatisticsDto
import com.example.apidatn.dto.UserInfoDto
import org.springframework.web.multipart.MultipartFile

interface UserInfoService {
    fun getAllUser():MutableList<UserInfoDto>
    fun getUserById(userId:Int):UserInfoDto
    fun updateUser(userId: Int,userInfoDto: UserInfoDto):Boolean
    fun getAllUserByStatus():MutableList<UserInfoDto>
    fun getAllUserRole():MutableList<UserInfoDto>
    fun updateUserStatus(userId: Int, accountStatus:String):Boolean
    fun updateImage(userId:Int,userImage:MultipartFile):Boolean
    fun statistics():StatisticsDto
    fun userByPhone(phone:Int):UserInfoDto
    fun updateAddress(userId: Int,userInfoDto: UserInfoDto):Boolean
}