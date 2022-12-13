package com.example.apidatn.service

import com.example.apidatn.dto.*
import com.example.apidatn.model.Bill
import com.example.apidatn.model.BillDetail
import com.example.apidatn.model.Product
import com.example.apidatn.model.User
import com.example.apidatn.repository.*
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors

@Service
class BillServiceImpl:BillService {

    @Autowired
    private lateinit var billRepository: BillRepository

    @Autowired
    private lateinit var billDetailRepository: BillDetailRepository

    @Autowired
    private lateinit var cartRepository: CartRepository

    @Autowired
    private  lateinit var productRepository: ProductRepository

    @Autowired
    private  lateinit var userRepository: UserRepository

    @Autowired
    private  lateinit var ratingRepository:RatingRepository

    private val mapper: ModelMapper = ModelMapper()


    fun toEntityDto(user: User): UserInfoDto = mapper.map(user, UserInfoDto::class.java)
    fun toEntityDtoProduct(product: Product): ProductDto = mapper.map(product, ProductDto::class.java)



    fun toDtoEntityBill(billDto: BillDto): Bill =mapper.map(billDto, Bill::class.java)

    fun toEntityDtoBill(bill: Bill): BillDto= mapper.map(bill, BillDto::class.java)

    fun toDtoEntityBilDetail(billDetailDto: BillDetailDto): BillDetail =mapper.map(billDetailDto, BillDetail::class.java)

    fun toEntityDtoBillDetail(billDetail: BillDetail): BillDetailDto = mapper.map(billDetail, BillDetailDto::class.java)

    override fun postBill(billPayDto: BillPayDto): Boolean {
        var listUserId : MutableList<Int> = mutableListOf()
        var listSS : MutableList<Int> = mutableListOf()
        for (index in 0 until billPayDto.listProductId!!.size){
            var product=billPayDto.listProductId!![index].productId?.let { productRepository.findById(it) }!!.get()
            product.amountProduct= product.amountProduct?.minus(billPayDto.ListAmountBuy!![index].amountBuy!!)
            productRepository.save(product)
            product.userId?.let { listUserId.add(it) }
            billDetailRepository.save(BillDetail(
                    amountBuy = billPayDto.ListAmountBuy!![index].amountBuy,
                    productId = billPayDto.listProductId!![index].productId,
                    money = product.priceProduct,
                )
            )

            var cart=cartRepository.findByProductId(billPayDto.listProductId!![index].productId!!).get()
            cartRepository.deleteById(cart.cartId!!)
        }
        var boolean=false
        listSS.add(listUserId[0])
        for (index in listUserId.size-1 downTo 0) {
            boolean=false
            var userId2 = listUserId[index]
            for (i in listSS.size - 1 downTo 0) {
                var userId3 =listSS[i]
                if (userId2 == userId3) {
                    boolean= true
                    break
                }
            }
            if (!boolean){
                userId2?.let { listSS.add(it) }
            }
        }
        for( userId in listSS){
            var listBillDetail=billDetailRepository.findAllByUserId(userId)
            var price=0F

            for (billDetail in listBillDetail){
                var product= billDetail.productId?.let { productRepository.findById(it) }?.get()
                price += product?.priceProduct?.times((billDetail.amountBuy!!))?.minus((product?.priceDeposit?.times(product?.priceProduct!! * billDetail.amountBuy!!))!!.mod(100.0))!!.toFloat()
                println(price)
            }
            var bill= Bill(
                    billStatusId = 1,
                    dateBill = Date(System.currentTimeMillis()),
                    addressBill = billPayDto.addressBill,
                    pay_id = billPayDto.payId,
                    userId = billPayDto.userId,
                    totalPrice = price
            )
            billRepository.save(bill)
            for(billDetail in listBillDetail){
                billDetail.billId=bill.billId
                billDetailRepository.save(billDetail)
            }
        }
        return true
    }

    override fun getBillUserId(userId: Int,billStatusId: Int): MutableList<BillDto> {
        var listBillDto= billRepository.findAllByUserIdAndBillStatusId(userId,billStatusId).stream().map { bill:Bill->toEntityDtoBill(bill) }
                .collect(Collectors.toList())
        for (bill in listBillDto){
            bill.userInfoDto= toEntityDto(bill.listBillDetail?.get(0)?.product?.userId?.let { userRepository.findById(it) }!!.get())
             for (billDetail in bill.listBillDetail!!){
                 var productDto=toEntityDtoProduct(billDetail.productId?.let { productRepository.findById(it) }!!.get())
                 if(ratingRepository.findByProductId(productDto.productId!!).size>0){
                     billDetail.product?.rating = ratingRepository.avgRating(productDto?.productId!!)
                     billDetail.product?.userRating= ratingRepository.amountRatingByUser(productDto?.productId!!)
                 }else{
                     billDetail.product?.rating=0F
                     billDetail.product?.userRating=0
                 }
             }
        }
        return  listBillDto
    }

    override fun getBillStatus(billStatusId: Int): List<BillDto> {
        var listBillDto=billRepository.findAllByBillStatusId(billStatusId).stream().map { bill:Bill->toEntityDtoBill(bill) }
                .collect(Collectors.toList())
        var list:MutableList<BillDto> = mutableListOf()

        for (bill in listBillDto){
            bill.listBillDetail=listBillDetail(bill.billId!!)
            list.add(bill)

        }
        return list
    }

    override fun updateBillStatus(billStatusId: Int, billId: Int): Boolean {
        if(billRepository.findById(billId).isPresent){
            var billDto = billRepository.findById(billId).get()
            billDto.billStatusId=billStatusId
            billRepository.save(billDto)
            return true

        }
        return false
    }

    override fun getBillByProductUserId(userId: Int, billStatusId: Int): MutableList<BillDto> {
       var list= billRepository.findAllByBillUserId(userId,billStatusId).stream().map { bill:Bill->toEntityDtoBill(bill) }
                .collect(Collectors.toList())
        for (bill in list){
            bill.userInfoDto=toEntityDto(bill.userId?.let { userRepository.findById(it).get() }!!)
        }
        return  list
    }

    fun listBillDetail(billId: Int): MutableList<BillDetailDto>? {
        var listBillDetailDto=billDetailRepository.findAllByBillId(billId).stream()
                .map { billDetail:BillDetail->toEntityDtoBillDetail(billDetail) }.collect(Collectors.toList())
        var list:MutableList<BillDetailDto> = mutableListOf()
        for (billDetail in listBillDetailDto){
            var userInfoDto=toEntityDto(userRepository.findById(billDetail.product?.userId!!).get())
            list.add(
                    BillDetailDto(
                            billDetailId = billDetail.billDetailId,
                            productId = billDetail.productId,
                            amountBuy = billDetail.amountBuy,
                            moneyNow = billDetail.moneyNow,
                            money = billDetail.money,
                            product = billDetail.product,
                    )
            )
        }

        return list
    }
}


