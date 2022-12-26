package com.example.apidatn.controller

import com.example.apidatn.dto.RatingDto
import com.example.apidatn.service.RatingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value=["/rating"],produces = ["application/json;charset=UTF-8"])
class RatingController {

    @Autowired
    private lateinit var ratingService: RatingService

    @GetMapping("/{id}")
    fun avgRating(@PathVariable("id") productId: Int):ResponseEntity<Float>
    = ResponseEntity.ok(ratingService.avgRating(productId))

    @PostMapping("")
    fun addRating(@RequestBody ratingDto: RatingDto):ResponseEntity<Boolean>
    = ResponseEntity.ok(ratingService.addRatingProduct(ratingDto))
}