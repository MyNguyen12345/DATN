package com.example.apidatn.repository

import com.example.apidatn.model.Rating
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RatingRepository:JpaRepository<Rating,Int> {
    fun findRatingByProductId(productId:Int):Optional<Rating>

    @Query(value = "SELECT AVG(rating_star)FROM rating where product_id=?",nativeQuery = true)
    fun avgRating(productId: Int):Float
}