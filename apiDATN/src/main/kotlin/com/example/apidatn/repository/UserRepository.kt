package com.example.apidatn.repository

import com.example.apidatn.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository:JpaRepository<User,Int> {
    fun findUserByPhone(phone:Int):Optional<User>

    @Query( value = "SELECT * FROM user_info  WHERE user_info.account_status= 'active'",nativeQuery = true)
    fun findAllByAccountStatus():MutableList<User>

    @Query(value = "select *from user_info join role on user_info.role_id  = role.role_id  where role.role_name ='ROLE_USER'",nativeQuery = true)
    fun findAllByUser():MutableList<User>
}