package com.example.apidatn.service

import com.example.apidatn.dto.CategoryDetailDto
import com.example.apidatn.dto.RatingDto
import com.example.apidatn.model.CategoryDetail
import com.example.apidatn.model.Rating
import com.example.apidatn.repository.RatingRepository
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RatingServiceImpl :RatingService{

    @Autowired
    private lateinit var ratingRepository: RatingRepository

    private val mapper: ModelMapper = ModelMapper()

    fun toEntityDto(rating: Rating): RatingDto = mapper.map(rating, RatingDto::class.java)

    fun toDtoEntity(ratingDto: RatingDto ): Rating =mapper.map(ratingDto, Rating::class.java)


    override fun addRatingProduct(productId: Int,ratingDto: RatingDto): Boolean {
            ratingDto.productId=productId
            ratingRepository.save(toDtoEntity(ratingDto))
            return true

    }

    override fun avgRating(productId: Int): Float {
        return ratingRepository.avgRating(productId)
    }
}