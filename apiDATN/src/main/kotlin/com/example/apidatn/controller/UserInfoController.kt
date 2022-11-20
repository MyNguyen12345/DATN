package com.example.apidatn.controller

import com.example.apidatn.dto.AccountDto
import com.example.apidatn.dto.UserInfoDto
import com.example.apidatn.model.User
import com.example.apidatn.service.UserInfoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value=["/user"],produces = ["application/json;charset=UTF-8"])
class UserInfoController (private val userInfoService: UserInfoService){

    @GetMapping("")
    fun getAllUser():ResponseEntity<MutableList<UserInfoDto>> = ResponseEntity.ok(userInfoService.getAllUser())

    @GetMapping("/{userId}")
    fun getUserById(@PathVariable("userId") userId:Int):ResponseEntity<UserInfoDto> = ResponseEntity.ok(userInfoService.getUserById(userId))

    @GetMapping("/status")
    fun getAllUserByStatus():ResponseEntity<MutableList<UserInfoDto>> =ResponseEntity.ok(userInfoService.getAllUserByStatus())


    @PostMapping("/{userId}")
    fun updateUser(@PathVariable("userId") userId: Int, @RequestBody userInfoDto: UserInfoDto):ResponseEntity<UserInfoDto>{
        val boolean= userInfoService.updateUser(userId,userInfoDto)
        if (boolean){
            userInfoDto.userId=userId
            return ResponseEntity.ok(userInfoDto)
        }
        return ResponseEntity(HttpStatus.BAD_REQUEST)
    }
}