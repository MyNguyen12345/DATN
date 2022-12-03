package com.example.apidatn.service

import com.example.apidatn.dto.*
import com.example.apidatn.model.Bill
import com.example.apidatn.model.BillDetail
import com.example.apidatn.model.Cart
import com.example.apidatn.model.Product
import com.example.apidatn.repository.BillDetailRepository
import com.example.apidatn.repository.BillRepository
import com.example.apidatn.repository.CartRepository
import com.example.apidatn.repository.ProductRepository
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

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

    private val mapper: ModelMapper = ModelMapper()


    fun toDtoEntityBill(billDto: BillDto): Bill =mapper.map(billDto, Bill::class.java)

    fun toEntityDtoBill(bill: Bill): BillDto= mapper.map(bill, BillDto::class.java)

    fun toDtoEntityBilDetail(billDetailDto: BillDetailDto): BillDetail =mapper.map(billDetailDto, BillDetail::class.java)

    fun toEntityDtoBillDetail(billDetail: BillDetail): BillDetailDto = mapper.map(billDetail, BillDetailDto::class.java)

    override fun postBill(billPayDto: BillPayDto): Boolean {
//        var pay=0F
//        for ( index in 0 until billPayDto.listProductId!!.size){
//            var price=cartRepository.payCartId(billPayDto.listProductId!![index].productId)
//            pay += price
//        }
        var bill= Bill(
                billStatusId = 1,
                dateBill = System.currentTimeMillis().toString(),
                addressBill = billPayDto.addressBill,
                pay_id = billPayDto.payId,
                totalPrice = billPayDto.priceTotal,
                userId = billPayDto.userId
                )
        billRepository.save(bill)

        for (index in 0 until billPayDto.listProductId!!.size){
            var productDto= billPayDto.listProductId!![index].productId?.let { productRepository.findById(it) }!!.get()
           billDetailRepository.save(BillDetail(
                   amountBuy = billPayDto.ListAmountBuy!![index].amountBuy,
                   productId = billPayDto.listProductId!![index].productId,
                   money = productDto.priceProduct,
                   billId = bill.billId)
            )
        }
        return true
    }

    override fun getBillUserId(userId: Int): BillDto {
        return toEntityDtoBill(billRepository.findByUserId(userId))
    }


}