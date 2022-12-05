package com.example.apidatn.service

import com.example.apidatn.dto.CategoryDto
import com.example.apidatn.dto.UserInfoDto
import com.example.apidatn.model.Category
import com.example.apidatn.model.User
import com.example.apidatn.repository.CategoryRepository
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class CategoryServiceImpl(private  val categoryRepository: CategoryRepository):CategoryService {

    private val mapper: ModelMapper = ModelMapper()

    fun toEntityDto(category: Category): CategoryDto = mapper.map(category, CategoryDto::class.java)

    fun toDtoEntity(categoryDto: CategoryDto): Category =mapper.map(categoryDto, Category::class.java)

    override fun getAllCategory(): MutableList<CategoryDto> {
        return categoryRepository.findAll().stream().map { category:Category->toEntityDto(category) }.collect(Collectors.toList())
    }

    override fun getCategoryById(categoryId: Int): CategoryDto {
        return toEntityDto(categoryRepository.findById(categoryId).get())
    }

    override fun addCategory(categoryDto: CategoryDto): Boolean {
         categoryRepository.save(toDtoEntity(categoryDto))
        return true
    }

    override fun updateCategory(categoryId: Int, categoryDto: CategoryDto): Boolean {
        if(categoryRepository.findById(categoryId).isPresent){
            categoryDto.categoryId=categoryId
            categoryRepository.save(toDtoEntity(categoryDto))
            return true
        }
        return false
    }
}