package com.example.apidatn.service

import com.example.apidatn.dto.*
import com.example.apidatn.model.Cart
import com.example.apidatn.model.Product
import com.example.apidatn.model.User
import com.example.apidatn.repository.CartRepository
import com.example.apidatn.repository.ProductRepository
import com.example.apidatn.repository.RatingRepository
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
    fun toEntityDtoProduct(product: Product): ProductDto = mapper.map(product, ProductDto::class.java)
    fun toEntityDtoUser(user: User): UserInfoDto = mapper.map(user, UserInfoDto::class.java)

    @Autowired
    private lateinit var cartRepository: CartRepository

    @Autowired
    private  lateinit var productRepository: ProductRepository

    @Autowired
    private  lateinit var  userRepository: UserRepository

    @Autowired
    private  lateinit var ratingRepository:RatingRepository


    override fun addCart(userId: Int, cartDto: CartDto): Boolean {


        if (userRepository.findById(userId).isPresent) {

            if (cartRepository.findByProductId(cartDto.productId!!).isPresent) {
                val cart = cartDto.productId?.let { cartRepository.findByProductId(it).get() }
                cart?.userId = userId
                val amount = cart?.amountProduct!! + cartDto.amountProduct!!
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
        val listcart = cartRepository.findAllByUserId(userId)
        val list:MutableList<ListCartDto> = mutableListOf()

        val productA=productRepository.findById(listcart[0].productId!!).get()
        val listCartDe=productA?.userId?.let { cartRepository.listCartUserId(it) }
                ?.stream()?.map { cart:Cart->toEntityDto(cart)}?.collect(Collectors.toList())

        if (listCartDe != null) {
            for (cart in listCartDe){
                cart.product= cart.productId?.let { productRepository.findById(it).get() }?.let { toEntityDtoProduct(it) }
                if(ratingRepository.findByProductId(cart.product?.productId!!).size>0){
                    cart.product?.rating = ratingRepository.avgRating(cart.product?.productId!!)
                    cart.product?.userRating= ratingRepository.amountRatingByUser(cart.product?.productId!!)
                }else{
                    cart.product?.rating =0F
                    cart.product?.userRating=0
                }

            }
                list.add(
                        ListCartDto(
                                listCart=listCartDe ,
                                user = toEntityDtoUser(userRepository.findById(productA?.userId!!).get())
                        )
                )
        }
        for (index in listcart.size-1 downTo 1){
            var product2=productRepository.findById(listcart[index].productId!!).get()
            var product3=productRepository.findById(listcart[index-1].productId!!).get()
            print(index)

            if(product2.userId!=product3.userId){

                var listCartDe=product2?.userId?.let { cartRepository.listCartUserId(it) }
                        ?.stream()?.map { cart:Cart->toEntityDto(cart)}?.collect(Collectors.toList())

                if (listCartDe != null) {
                    for (cart in listCartDe){
                        cart.product= cart.productId?.let { productRepository.findById(it).get() }?.let { toEntityDtoProduct(it) }

                        if(ratingRepository.findByProductId(cart.product?.productId!!).size>0){
                            cart.product?.rating = ratingRepository.avgRating(cart.product?.productId!!)
                            cart.product?.userRating= ratingRepository.amountRatingByUser(cart.product?.productId!!)
                        }else{
                            cart.product?.rating =0F
                            cart.product?.userRating=0
                        }

                    }
                    list.add(
                            ListCartDto(
                                    listCart=listCartDe ,
                                    user = toEntityDtoUser(userRepository.findById(product2?.userId!!).get())
                            )
                    )
                }
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