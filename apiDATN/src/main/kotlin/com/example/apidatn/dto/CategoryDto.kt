package com.example.apidatn.dto

import com.example.apidatn.model.CategoryDetail


data class CategoryDto (
        var categoryId:Int?=null,
        var categoryName:String?=null,
        var categoryIcon:String?=null,
        var categoryDetail:MutableList<CategoryDetailDto>?=null
        )