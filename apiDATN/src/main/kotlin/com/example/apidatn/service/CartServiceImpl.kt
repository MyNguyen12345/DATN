package com.example.apidatn.service

import com.example.apidatn.dto.*
import com.example.apidatn.model.Cart
import com.example.apidatn.model.Product
import com.example.apidatn.model.User
import com.example.apidatn.repository.CartRepository
import com.example.apidatn.repository.ProductRepository
import com.example.apidatn.repository.UserRepository
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CartServiceImpl: CartService {

    private val mapper: ModelMapper = ModelMapper()

    fun toDtoEntity(cartDto: CartDto): Cart =mapper.map(cartDto, Cart::class.java)

    fun toEntityDto(product: Product): ProductDto = mapper.map(product, ProductDto::class.java)
    fun toEntityDtoUser(user: User): UserInfoDto = mapper.map(user, UserInfoDto::class.java)

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
        for (index in  (listcart.size-1) downTo 0){
            list.add(  CartDto(
                       cartId = listcart[index].cartId,
                       userId=listcart[index].userId,
                       amountProduct = listcart[index].amountProduct,
                       productId = listcart[index].productId,
                       product = toEntityDto(productRepository.findById(listcart[index].productId!!).get()),
                    user = toEntityDtoUser(userRepository.findById(listcart[index].userId!!).get())
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

    override fun payListCart(listCartId: List<CartIdDto>): Float {
        var pay:Float= 0F;
        for (cartDto in listCartId){
            if(cartDto!=null){
                var price=cartRepository.payCartId(cartDto.productId)
                pay += price
            }

        }
        return pay
    }
}