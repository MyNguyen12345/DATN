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
import java.util.stream.Collectors

@Service
class CartServiceImpl: CartService {

    private val mapper: ModelMapper = ModelMapper()

    fun toDtoEntity(cartDto: CartDto): Cart =mapper.map(cartDto, Cart::class.java)

    fun toEntityDto(cart: Cart): CartDto = mapper.map(cart, CartDto::class.java)
    fun toEntityDtoUser(user: User): UserInfoDto = mapper.map(user, UserInfoDto::class.java)

    @Autowired
    private lateinit var cartRepository: CartRepository

    @Autowired
    private  lateinit var productRepository: ProductRepository

    @Autowired
    private  lateinit var  userRepository: UserRepository


    override fun addCart(userId: Int, cartDto: CartDto): Boolean {


        if (userRepository.findById(userId).isPresent) {

            if (cartRepository.findByProductId(cartDto.productId!!).isPresent) {
                var cart = cartDto.productId?.let { cartRepository.findByProductId(it).get() }
                cart?.userId = userId
                var amount = cart?.amountProduct!! + cartDto.amountProduct!!
                cart.amountProduct = amount
                cartRepository.save(cart)
            } else {
                cartDto.userId = userId
                cartRepository.save(toDtoEntity(cartDto))

            }

            return true
        }

        return false
    }


    override fun getAllCart(userId: Int): MutableList<ListCartDto> {
        var listcart = cartRepository.findAllByUserId(userId)

        var list:MutableList<ListCartDto> = mutableListOf()
        var product0=productRepository.findById(listcart[0].productId!!).get()
            list.add(
                    ListCartDto(
                            listCart= product0?.userId?.let { cartRepository.listCartUserId(it) }
                                    ?.stream()?.map { cart:Cart->toEntityDto(cart)}?.collect(Collectors.toList()),
                            user = toEntityDtoUser(userRepository.findById(product0?.userId!!).get())
                    )
            )
        for (index in listcart.size-1 downTo 1){
            var product2=productRepository.findById(listcart[index].productId!!).get()
            var product3=productRepository.findById(listcart[index-1].productId!!).get()

            if(product2.userId!=product3.userId){
                list.add(
                        ListCartDto(
                                listCart= product2?.userId?.let { cartRepository.listCartUserId(it) }
                                        ?.stream()?.map { cart:Cart->toEntityDto(cart)}?.collect(Collectors.toList()),
                                user = toEntityDtoUser(userRepository.findById(product2?.userId!!).get())
                        )
                )
            }
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