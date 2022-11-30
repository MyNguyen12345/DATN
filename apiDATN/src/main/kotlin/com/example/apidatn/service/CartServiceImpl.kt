package com.example.apidatn.service

import com.example.apidatn.dto.*
import com.example.apidatn.model.Cart
import com.example.apidatn.model.CategoryDetail
import com.example.apidatn.model.Product
import com.example.apidatn.model.User
import com.example.apidatn.repository.CartRepository
import com.example.apidatn.repository.ProductRepository
import com.example.apidatn.repository.UserRepository
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class CartServiceImpl: CartService {

    private val mapper: ModelMapper = ModelMapper()

//    fun toEntityDto(cart:Cart): CartDto = mapper.map(cart, CartDto::class.java)

    fun toDtoEntity(cartDto: CartDto): Cart =mapper.map(cartDto, Cart::class.java)

    fun toEntityDto(product: Product): ProductDto = mapper.map(product, ProductDto::class.java)

//    fun toDtoEntity(productDto: ProductDto): Product =mapper.map(productDto, Product::class.java)

    fun toEntityDtoUser(user: User): UserInfoDto = mapper.map(user, UserInfoDto::class.java)

//    fun toDtoEntity(userInfoDto: UserInfoDto): User = mapper.map(userInfoDto, User::class.java)
    @Autowired
    private lateinit var cartRepository: CartRepository

    @Autowired
    private  lateinit var productRepository: ProductRepository

    @Autowired
    private  lateinit var  userRepository: UserRepository

    override fun addCart(userId: Int, cartDto: CartDto): Boolean {

        if (userRepository.findById(userId).isPresent){
            cartDto.userId=userId
            cartRepository.save(toDtoEntity(cartDto))
            return true
        }
        return false
    }

    override fun getAllCart(userId: Int): MutableList<CartDto> {
        var listcart = cartRepository.findAllByUserId(userId)
        var list:MutableList<CartDto> = mutableListOf()
        for (i in 0..listcart.size-1){
            list.add(  CartDto(
                       cartId = listcart[i].cartId,
                       userId=listcart[i].userId,
                       amountProduct = listcart[i].amountProduct,
                       productId = listcart[i].productId,
                       product = toEntityDto(productRepository.findById(listcart[i].productId!!).get()),
                    user = toEntityDtoUser(userRepository.findById(listcart[i].userId!!).get())
               ))
        }
        return list
    }

    override fun getAmountCart(userId: Int): Int {
        return cartRepository.getAmountCart(userId)
    }

    override fun deleteCart(cartId: Int): Boolean {
        cartRepository.deleteById(cartId)
        return true
    }
}