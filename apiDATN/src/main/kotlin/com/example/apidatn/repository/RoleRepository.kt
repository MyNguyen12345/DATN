package com.example.apidatn.repository

import com.example.apidatn.model.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository:JpaRepository<Role,Int> {
    @Query(value = "select role.* from role join user_info on user_info.role_id =role.role_id  where user_info.phone =?",nativeQuery = true)
    fun findRoleByPhone(phone:Int):Role
}