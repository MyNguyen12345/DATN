package com.example.apidatn.service

import com.example.apidatn.dto.CategoryDetailDto

interface CategoryDetailService {
    fun getAllCategoryDetail():MutableList<CategoryDetailDto>
    fun getCategoryDetailById(categoryDetailId:Int):CategoryDetailDto
    fun getAllCategoryDetailByCategoryId(categoryId:Int):MutableList<CategoryDetailDto>
    fun addCategoryDetail(categoryDetailDto: CategoryDetailDto):Boolean
    fun updateCategoryDetail(categoryDetailId: Int,categoryDetailDto: CategoryDetailDto):Boolean
}