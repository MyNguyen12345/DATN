package com.example.apidatn.service

import com.example.apidatn.dto.CategoryDto
import com.example.apidatn.dto.PayDto
import com.example.apidatn.model.Category
import com.example.apidatn.model.Pay
import com.example.apidatn.repository.PayRepository
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class PayServiceImpl:PayService {

    private val mapper: ModelMapper = ModelMapper()

    fun toEntityDto(pay: Pay): PayDto = mapper.map(pay, PayDto::class.java)

    fun toDtoEntity(payDto: PayDto): Pay =mapper.map(payDto, Pay::class.java)

    @Autowired
    private  lateinit var  payRepository: PayRepository


    override fun listPay(): MutableList<PayDto> {
        return payRepository.findAll().stream().map { pay:Pay->toEntityDto(pay) }.collect(Collectors.toList())
    }
}