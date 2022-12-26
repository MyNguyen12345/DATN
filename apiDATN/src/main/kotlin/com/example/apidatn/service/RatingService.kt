package com.example.apidatn.service

import com.example.apidatn.dto.RatingDto

interface RatingService {
    fun addRatingProduct(ratingDto: RatingDto) : Boolean
    fun avgRating(productId: Int):Float
}