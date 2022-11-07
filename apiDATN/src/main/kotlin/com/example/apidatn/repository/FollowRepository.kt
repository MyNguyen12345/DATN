package com.example.apidatn.repository

import com.example.apidatn.model.Follow
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FollowRepository:JpaRepository<Follow,Int> {
}