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

            if (cartRepository.findAllByProductId(cartDto.productId!!).size>0 && cartRepository.findAllByUserId(userId).size>0) {
                val cart = cartRepository.findByProductIdAndUserId(cartDto.productId!!,userId).get()
                cart?.userId = userId
                val amount = cart?.amountProduct!! + cartDto.amountProduct!!
                if(amount<= cart.product?.amountProduct!!){
                    cart.amountProduct = amount
                }else{
                    cart.amountProduct=cart.product?.amountProduct
                }
                cartRepository.save(cart)
            } else {
                var cart=Cart(
                        productId = cartDto.productId,
                        userId = userId,
                        amountProduct = cartDto.amountProduct
                )
                cartRepository.save(cart)

            }

            return true
        }

        return false
    }

    override fun getAllCart(userId: Int): MutableList<ListCartDto> {
        val listcart = cartRepository.findAllByUserId(userId)
        for (cart in listcart){
            cart.product= cart.productId?.let { productRepository.findById(it).get() }
        }

        val list:MutableList<ListCartDto> = mutableListOf()

        var listId:MutableList<Int> = mutableListOf()
        var boolean=false
        if(listcart.size>0){
            listcart[listcart.size-1].product?.userId?.let { listId.add(it) }

            for (index in listcart.size-1 downTo 0) {
                boolean=false
                var userId2 = listcart[index].product?.userId
                for (i in listId.size - 1 downTo 0) {
                    var userId3 =listId[i]
                    if (userId2 == userId3) {
                        boolean= true
                        break
                    }
                }
                if (!boolean){
                    userId2?.let { listId.add(it) }
                }
            }
            for (userID in listId){
                val listCartDe=userID?.let { cartRepository.listCartUserId(it) }
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
                                    user = toEntityDtoUser(userRepository.findById(userID!!).get())
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

    override fun payListCart(listCartId: List<ProductIdDto>): Float {
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