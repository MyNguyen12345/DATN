package com.example.apidatn.repository

import com.example.apidatn.model.BillDetail
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface BillDetailRepository:JpaRepository<BillDetail,Int> {

    fun  findAllByBillId(billId:Int):List<BillDetail>

    @Query(value = "select  bill_detail.* from bill_detail  join product  on bill_detail.product_id =product.product_id \n" +
            "where  product.user_id =:userId and bill_detail.bill_id =:billId",nativeQuery = true)
    fun findAllProductUserId(userId:Int,billId: Int):List<BillDetail>

    @Query(value = "select  bill_detail.* from bill_detail join product on bill_detail.product_id = product.product_id \n" +
            "where product.user_id =? and bill_detail.bill_id is  NULL",nativeQuery = true)
    fun findAllByUserId(userId:Int) : List<BillDetail>

    @Query(value = "select bill_detail.* from  bill_detail join product  on bill_detail.product_id = product.product_id \n" +
            "where product.user_id =?",nativeQuery = true)
    fun findAllByProductUserId(userId: Int):MutableList<BillDetail>
}