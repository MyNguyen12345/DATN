package com.example.apidatn.service

import com.example.apidatn.dto.CategoryDto
import com.example.apidatn.model.Category

interface CategoryService {
    fun getAllCategory():MutableList<CategoryDto>
    fun getCategoryById(categoryId:Int):CategoryDto
    fun addCategory(categoryDto: CategoryDto):Boolean
    fun updateCategory(categoryId: Int,categoryDto: CategoryDto):Boolean
}