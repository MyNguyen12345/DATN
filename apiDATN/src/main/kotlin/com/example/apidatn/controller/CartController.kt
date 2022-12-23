package com.example.apidatn.controller

import com.example.apidatn.dto.CartDto
import com.example.apidatn.dto.ProductIdDto
import com.example.apidatn.dto.ListCartDto
import com.example.apidatn.service.CartService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value=["/cart"],produces = ["application/json;charset=UTF-8"])
class CartController {
    @Autowired
    private lateinit var cartService: CartService

    @GetMapping("/{id}")
    fun getAllCartByUserId(@PathVariable("id") userId: Int):ResponseEntity<MutableList<ListCartDto>>
    = ResponseEntity.ok(cartService.getAllCart(userId ))



    @GetMapping("/amount/{id}")
    fun getAmountCart(@PathVariable("id") userId: Int):ResponseEntity<Int>
    = ResponseEntity.ok(cartService.getAmountCart(userId))

    @PostMapping("/pay")
    fun payCartId(@RequestBody listCartId:List<ProductIdDto>) : ResponseEntity<Float>
    = ResponseEntity.ok(cartService.payListCart(listCartId))

    @PostMapping("/{id}")
    fun addCart(@PathVariable("id") userId:Int,@RequestBody cartDto: CartDto):ResponseEntity<Boolean>
    = ResponseEntity.ok(cartService.addCart(userId, cartDto))

    @DeleteMapping("/delete/{id}")
    fun deleteCart(@PathVariable("id") cartId:Int):ResponseEntity<Boolean>
    = ResponseEntity.ok(cartService.deleteCart(cartId))
}