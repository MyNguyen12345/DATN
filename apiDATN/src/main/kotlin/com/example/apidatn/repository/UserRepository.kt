package com.example.apidatn.repository

import com.example.apidatn.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
interface UserRepository:JpaRepository<User,Int> {
    fun findUserByPhone(phone:Int):Optional<User>

    @Query( value = "SELECT * FROM user_info  WHERE user_info.account_status= 'active'",nativeQuery = true)
    fun findAllByAccountStatus():MutableList<User>

    @Query(value = "select *from user_info join role on user_info.role_id  = role.role_id  where role.role_name ='ROLE_USER'",nativeQuery = true)
    fun findAllByUser():MutableList<User>

    @Transactional
    @Modifying
    @Query(value ="update user_info set account_status=? where user_id=?" ,nativeQuery = true)
    fun updateUserByStatus(accountStatus:String,userId:Int)

    @Query(value = "select count(user_id)  from user_info join role on user_info.role_id =role.role_id where role.role_name='ROLE_USER'",nativeQuery = true)
    fun countByUserId():Int

    @Query(value = "select count(user_id)  from user_info join role on user_info.role_id =role.role_id where role.role_name='ROLE_USER'and account_status='active'",nativeQuery = true)
    fun countByUserIdStatus():Int


}