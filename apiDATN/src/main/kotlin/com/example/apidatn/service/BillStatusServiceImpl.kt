package com.example.apidatn.service

import com.example.apidatn.dto.BillStatusDto
import com.example.apidatn.dto.CartDto
import com.example.apidatn.model.BillStatus
import com.example.apidatn.model.Cart
import com.example.apidatn.repository.BillStatusRepository
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class BillStatusServiceImpl:BillStatusService {

    @Autowired
    private  lateinit var billStatusRepository: BillStatusRepository

    private val mapper: ModelMapper = ModelMapper()

    fun toDtoEntity(billStatusDto: BillStatusDto): BillStatus =mapper.map(billStatusDto, BillStatus::class.java)

    fun toEntityDto(billStatus: BillStatus): BillStatusDto = mapper.map(billStatus, BillStatusDto::class.java)

    override fun getBillStatus(): MutableList<BillStatusDto> {
        return billStatusRepository.findAll().stream().map {billStatus:BillStatus->toEntityDto(billStatus) }
                .collect(Collectors.toList())

    }
}