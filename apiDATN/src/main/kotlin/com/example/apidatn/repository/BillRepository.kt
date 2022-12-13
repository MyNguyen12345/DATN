package com.example.apidatn.repository

import com.example.apidatn.model.Bill
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface BillRepository:JpaRepository<Bill,Int> {
    fun findAllByUserIdAndBillStatusId(userId:Int,billStatusId: Int):MutableList<Bill>

    fun findAllByBillStatusId(billStatusId:Int):List<Bill>

    @Query(value = "select count(distinct user_id) from bill",nativeQuery = true)
    fun countByOder():Int

    @Query(value ="select bill.* from bill join bill_detail on bill.bill_id =bill_detail.bill_id  join product  on bill_detail.product_id = product.product_id \n" +
            "where product.user_id =:userId and bill.bill_status_id=:billStatusId",nativeQuery = true)
    fun findAllByBillUserId(userId: Int,billStatusId: Int):List<Bill>
}