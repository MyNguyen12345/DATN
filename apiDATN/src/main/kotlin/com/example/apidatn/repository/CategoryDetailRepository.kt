package com.example.apidatn.repository

import com.example.apidatn.model.CategoryDetail
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CategoryDetailRepository:JpaRepository<CategoryDetail,Int> {
    fun findAllByCategoryId(categoryId: Int):MutableList<CategoryDetail>

    @Query(value = "select  category_detail.* from category_detail join product on category_detail.category_detail_id =product.category_detail_id  where product.product_id =?",nativeQuery = true)
    fun findCategoryDetailByProductId(productId:Int):CategoryDetail
}