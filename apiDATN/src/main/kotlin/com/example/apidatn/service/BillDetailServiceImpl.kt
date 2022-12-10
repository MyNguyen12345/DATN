package com.example.apidatn.service

import com.example.apidatn.dto.BillDetailDto
import com.example.apidatn.dto.UserInfoDto
import com.example.apidatn.model.BillDetail
import com.example.apidatn.model.User
import com.example.apidatn.repository.BillDetailRepository
import com.example.apidatn.repository.UserRepository
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class BillDetailServiceImpl:BillDetailService {
    @Autowired
    private lateinit var billDetailRepository: BillDetailRepository

    @Autowired
    private  lateinit var userRepository: UserRepository

    private val mapper: ModelMapper = ModelMapper()

    fun toDtoEntityBilDetail(billDetailDto: BillDetailDto): BillDetail =mapper.map(billDetailDto, BillDetail::class.java)

    fun toEntityDtoBillDetail(billDetail: BillDetail): BillDetailDto = mapper.map(billDetail, BillDetailDto::class.java)

    fun toEntityDto(user: User): UserInfoDto = mapper.map(user, UserInfoDto::class.java)


    override fun listBillDetail(billId: Int): List<BillDetailDto> {
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