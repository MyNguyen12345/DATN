package com.example.apidatn.repository

import com.example.apidatn.model.CategoryDetail
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryDetailRepository:JpaRepository<CategoryDetail,Int> {
    fun findAllByCategoryId(categoryId: Int):MutableList<CategoryDetail>
}