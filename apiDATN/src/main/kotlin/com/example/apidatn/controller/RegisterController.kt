package com.example.apidatn.controller

import com.example.apidatn.dto.AccountDto
import com.example.apidatn.model.User
import com.example.apidatn.service.RegisterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value=["/register"],produces = ["application/json;charset=UTF-8"])
class RegisterController {

    @Autowired
    private lateinit var registerService: RegisterService

    @PostMapping("/pass/{phone}")
    fun forgotPass(@PathVariable("phone") phone:Int,@RequestParam("password") password:String):ResponseEntity<Boolean>
    = ResponseEntity.ok(registerService.forgotPassword(phone,password))

    @GetMapping("/{phone}")
    fun findByPhone(@PathVariable("phone") phone: Int) : ResponseEntity<Boolean>
    =ResponseEntity.ok(registerService.findByPhone(phone))


    @PostMapping()
    fun addUser(@RequestBody accountDto: AccountDto): ResponseEntity<Boolean> {
            return ResponseEntity.ok(registerService.registerAccount(accountDto))
    }

}