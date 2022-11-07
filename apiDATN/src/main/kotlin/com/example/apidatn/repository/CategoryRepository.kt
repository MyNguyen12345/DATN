package com.example.apidatn.repository

import com.example.apidatn.model.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository:JpaRepository<Category,Int> {
}