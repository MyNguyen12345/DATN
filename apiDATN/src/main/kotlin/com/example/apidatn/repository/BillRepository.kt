package com.example.apidatn.repository

import com.example.apidatn.model.Bill
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface BillRepository:JpaRepository<Bill,Int> {
    fun findByUserId(userId:Int):Bill

    fun findAllByBillStatusId(billStatusId:Int):List<Bill>

    @Query(value = "select count(distinct user_id) from bill",nativeQuery = true)
    fun countByOder():Int
}