package com.example.apidatn.service

import com.example.apidatn.dto.RatingDto
import com.example.apidatn.model.Rating
import com.example.apidatn.repository.ProductRepository
import com.example.apidatn.repository.RatingRepository
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RatingServiceImpl :RatingService{

    @Autowired
    private lateinit var ratingRepository: RatingRepository

    @Autowired
    private  lateinit var productRepository: ProductRepository

    private val mapper: ModelMapper = ModelMapper()

    fun toEntityDto(rating: Rating): RatingDto = mapper.map(rating, RatingDto::class.java)

    fun toDtoEntity(ratingDto: RatingDto ): Rating =mapper.map(ratingDto, Rating::class.java)


    override fun addRatingProduct(ratingDto: RatingDto): Boolean {
        mapper.configuration.isAmbiguityIgnored = true
        if(ratingDto.productId?.let { productRepository.findById(it).isPresent } == true){
               ratingRepository.save(toDtoEntity(ratingDto))
               return true
           }
        return false
    }

    override fun avgRating(productId: Int): Float {
        return ratingRepository.avgRating(productId)
    }
}