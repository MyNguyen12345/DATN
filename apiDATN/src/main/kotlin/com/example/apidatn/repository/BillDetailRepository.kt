package com.example.apidatn.repository

import com.example.apidatn.model.BillDetail
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BillDetailRepository:JpaRepository<BillDetail,Int> {
}