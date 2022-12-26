package com.example.apidatn.repository

import com.example.apidatn.model.BillStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository


@Repository
interface BillStatusRepository:JpaRepository<BillStatus,Int> {

    @Query(value = "select bill_status.* from bill_status order by bill_status_id asc ",nativeQuery = true)
    fun findAllByBillStatusId():MutableList<BillStatus>
}