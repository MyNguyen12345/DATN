package com.example.apidatn.repository

import com.example.apidatn.model.Rating
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RatingRepository:JpaRepository<Rating,Int> {
}