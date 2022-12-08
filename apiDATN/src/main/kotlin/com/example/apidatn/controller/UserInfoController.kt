package com.example.apidatn.controller

import com.example.apidatn.dto.StatisticsDto
import com.example.apidatn.dto.UserInfoDto
import com.example.apidatn.service.UserInfoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping(value=["/user"],produces = ["application/json;charset=UTF-8"])
class UserInfoController (private val userInfoService: UserInfoService){

    @GetMapping("")
    fun getAllUser():ResponseEntity<MutableList<UserInfoDto>> = ResponseEntity.ok(userInfoService.getAllUser())

    @GetMapping("/phone/{phone}")
    fun userByPhone(@PathVariable("phone") phone:Int) :ResponseEntity<UserInfoDto>
    = ResponseEntity.ok(userInfoService.userByPhone(phone))

    @GetMapping("/{userId}")
    fun getUserById(@PathVariable("userId") userId:Int):ResponseEntity<UserInfoDto> = ResponseEntity.ok(userInfoService.getUserById(userId))

    @GetMapping("/status")
    fun getAllUserByStatus():ResponseEntity<MutableList<UserInfoDto>> =ResponseEntity.ok(userInfoService.getAllUserByStatus())

    @GetMapping("/role")
    fun getAllUserRole():ResponseEntity<MutableList<UserInfoDto>> = ResponseEntity.ok(userInfoService.getAllUserRole())

    @GetMapping("/statistics")
    fun countUser():ResponseEntity<StatisticsDto> = ResponseEntity.ok(userInfoService.statistics())


    @PostMapping("/{userId}")
    fun updateUser(@PathVariable("userId") userId: Int, @RequestBody userInfoDto: UserInfoDto):ResponseEntity<Boolean>{
        val boolean= userInfoService.updateUser(userId,userInfoDto)
        if (boolean){
            userInfoDto.userId=userId
            return ResponseEntity.ok(boolean)
        }
        return ResponseEntity(HttpStatus.BAD_REQUEST)
    }

    @PostMapping("/address/{id}")
    fun updateAddress(@PathVariable("id") userId: Int,@RequestBody userInfoDto: UserInfoDto) : ResponseEntity<Boolean>
    = ResponseEntity.ok(userInfoService.updateAddress(userId, userInfoDto))

    @PostMapping("/status/{id}")
    fun updateUser(@PathVariable("id") userId: Int,@RequestParam("accountStatus") accountStatus:String): ResponseEntity<Boolean> {
         println(accountStatus)
        return ResponseEntity.ok(userInfoService.updateUserStatus(userId,accountStatus))
    }

    @PostMapping("/image/{id}")
    fun updateImage(@PathVariable("id") userId:Int,@RequestParam("image") avatar:MultipartFile):ResponseEntity<Boolean>
    = ResponseEntity.ok(userInfoService.updateImage(userId,avatar))
}