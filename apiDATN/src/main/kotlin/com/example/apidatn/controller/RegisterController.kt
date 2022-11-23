package com.example.apidatn.controller

import com.example.apidatn.dto.AccountDto
import com.example.apidatn.model.User
import com.example.apidatn.service.RegisterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class RegisterController {

    @Autowired
    private lateinit var registerService: RegisterService

    @PostMapping(value=["/register"],produces = ["application/json;charset=UTF-8"])
    fun addUser(@RequestBody accountDto: AccountDto): ResponseEntity<AccountDto> {
        if (registerService.registerAccount(accountDto)){
            return ResponseEntity.ok(accountDto)
        }
        return ResponseEntity(HttpStatus.BAD_REQUEST)
    }
}