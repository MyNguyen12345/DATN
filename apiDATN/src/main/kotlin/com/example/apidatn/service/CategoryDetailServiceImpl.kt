package com.example.apidatn.service

import com.example.apidatn.dto.CategoryDetailDto
import com.example.apidatn.dto.CategoryDto
import com.example.apidatn.model.Category
import com.example.apidatn.model.CategoryDetail
import com.example.apidatn.repository.CategoryDetailRepository
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors

@Service
class CategoryDetailServiceImpl(private val categoryDetailRepository: CategoryDetailRepository):CategoryDetailService {

    private val mapper: ModelMapper = ModelMapper()

    fun toEntityDto(categoryDetail: CategoryDetail): CategoryDetailDto = mapper.map(categoryDetail, CategoryDetailDto::class.java)

    fun toDtoEntity(categoryDetailDto: CategoryDetailDto): CategoryDetail =mapper.map(categoryDetailDto, CategoryDetail::class.java)

    override fun getAllCategoryDetail(): MutableList<CategoryDetailDto> {
        return categoryDetailRepository.findAll().stream().map { categoryDetail:CategoryDetail->toEntityDto(categoryDetail) }
                .collect(Collectors.toList())
    }

    override fun getCategoryDetailById(categoryDetailId: Int): CategoryDetailDto {
        return toEntityDto(categoryDetailRepository.findById(categoryDetailId).get())
    }

    override fun getAllCategoryDetailByCategoryId(categoryId: Int): MutableList<CategoryDetailDto> {
        return categoryDetailRepository.findAllByCategoryId(categoryId).stream().map {categoryDetail:CategoryDetail->toEntityDto(categoryDetail) }
                .collect(Collectors.toList())
    }

    override fun addCategoryDetail(categoryDetailDto: CategoryDetailDto): Boolean {
         categoryDetailRepository.save(toDtoEntity(categoryDetailDto))
        return true
    }

    override fun updateCategoryDetail(categoryDetailId: Int, categoryDetailDto: CategoryDetailDto): Boolean {
        val categoryDetail=toDtoEntity(categoryDetailDto)
        if(categoryDetailRepository.findById(categoryDetailId).isPresent){
            categoryDetail.categoryDetailId=categoryDetailId
            categoryDetailRepository.save(categoryDetail)
            return true
        }
        return false

    }
}