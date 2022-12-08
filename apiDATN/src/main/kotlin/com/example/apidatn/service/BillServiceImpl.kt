package com.example.apidatn.service

import com.example.apidatn.dto.*
import com.example.apidatn.model.Bill
import com.example.apidatn.model.BillDetail
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

    private val mapper: ModelMapper = ModelMapper()


    fun toEntityDto(user: User): UserInfoDto = mapper.map(user, UserInfoDto::class.java)



    fun toDtoEntityBill(billDto: BillDto): Bill =mapper.map(billDto, Bill::class.java)

    fun toEntityDtoBill(bill: Bill): BillDto= mapper.map(bill, BillDto::class.java)

    fun toDtoEntityBilDetail(billDetailDto: BillDetailDto): BillDetail =mapper.map(billDetailDto, BillDetail::class.java)

    fun toEntityDtoBillDetail(billDetail: BillDetail): BillDetailDto = mapper.map(billDetail, BillDetailDto::class.java)

    override fun postBill(billPayDto: BillPayDto): Boolean {
        var bill= Bill(
                billStatusId = 1,
                dateBill = Date(System.currentTimeMillis()),
                addressBill = billPayDto.addressBill,
                pay_id = billPayDto.payId,
                totalPrice = billPayDto.priceTotal,
                userId = billPayDto.userId
                )
        billRepository.save(bill)

        for (index in 0 until billPayDto.listProductId!!.size){
            var productDto= billPayDto.listProductId!![index].productId?.let { productRepository.findById(it) }!!.get()
            productDto.amountProduct= productDto.amountProduct?.minus(billPayDto.ListAmountBuy!![index].amountBuy!!)
            productRepository.save(productDto)
           billDetailRepository.save(BillDetail(
                   amountBuy = billPayDto.ListAmountBuy!![index].amountBuy,
                   productId = billPayDto.listProductId!![index].productId,
                   money = productDto.priceProduct,
                   billId = bill.billId)
            )
            billPayDto.listProductId!![index].productId?.let { cartRepository.deleteById(it) }
        }
        return true
    }

    override fun getBillUserId(userId: Int): BillDto {
        var billDto=toEntityDtoBill(billRepository.findByUserId(userId))
         billDto.listBillDetail=listBillDetail(billDto.billId!!)
        return billDto
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
                            userInfoDto=userInfoDto
                    )
            )
        }

        return list
    }
}


