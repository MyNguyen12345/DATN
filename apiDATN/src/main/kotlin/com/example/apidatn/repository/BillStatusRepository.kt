package com.example.apidatn.repository

import com.example.apidatn.model.BillStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface BillStatusRepository:JpaRepository<BillStatus,Int> {
}