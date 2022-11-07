package com.example.apidatn.repository

import com.example.apidatn.model.Bill
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BillRepository:JpaRepository<Bill,Int> {
}