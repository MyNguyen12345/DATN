package com.example.apidatn.service

import com.example.apidatn.dto.BillDetailDto
import com.example.apidatn.model.BillDetail
import com.example.apidatn.repository.BillDetailRepository
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class BillDetailServiceImpl:BillDetailService {
    @Autowired
    private lateinit var billDetailRepository: BillDetailRepository

    private val mapper: ModelMapper = ModelMapper()

    fun toDtoEntityBilDetail(billDetailDto: BillDetailDto): BillDetail =mapper.map(billDetailDto, BillDetail::class.java)

    fun toEntityDtoBillDetail(billDetail: BillDetail): BillDetailDto = mapper.map(billDetail, BillDetailDto::class.java)

    override fun listBillDetail(billId: Int): List<BillDetailDto> {

        return billDetailRepository.findAllByBillId(billId).stream().map { billDetail:BillDetail->toEntityDtoBillDetail(billDetail) }.collect(Collectors.toList())
    }
}