package com.example.apidatn.repository

import com.example.apidatn.model.Rating
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RatingRepository:JpaRepository<Rating,Int> {
    fun findByProductId(productId:Int):MutableList<Rating>

    @Query(value = "select count(user_id) from rating  where  product_id =? ",nativeQuery = true)
    fun amountRatingByUser(productId: Int):Int

    @Query(value = "SELECT AVG(rating_star)FROM rating where product_id=?",nativeQuery = true)
    fun avgRating(productId: Int):Float

}