package com.example.apidatn.service

import com.example.apidatn.dto.CartDto
import com.example.apidatn.dto.CategoryDetailDto
import com.example.apidatn.model.Cart
import com.example.apidatn.model.CategoryDetail
import com.example.apidatn.repository.CartRepository
import com.example.apidatn.repository.UserRepository
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class CartServiceImpl: CartService {

    private val mapper: ModelMapper = ModelMapper()

    fun toEntityDto(cart:Cart): CartDto = mapper.map(cart, CartDto::class.java)

    fun toDtoEntity(cartDto: CartDto): Cart =mapper.map(cartDto, Cart::class.java)


    @Autowired
    private lateinit var cartRepository: CartRepository

    @Autowired
    private lateinit var userRepository: UserRepository
    override fun addCart(userId: Int, cartDto: CartDto): Boolean {

        if (userRepository.findById(userId).isPresent){
            cartDto.userId=userId
            cartRepository.save(toDtoEntity(cartDto))
            return true
        }
        return false
    }

    override fun getAllCart(userId: Int): MutableList<CartDto> {
        return cartRepository.findAllByUserId(userId).stream().map { cart:Cart->toEntityDto(cart) }.collect(Collectors.toList())
    }

    override fun getAmountCart(userId: Int): Int {
        return cartRepository.getAmountCart(userId)
    }
}