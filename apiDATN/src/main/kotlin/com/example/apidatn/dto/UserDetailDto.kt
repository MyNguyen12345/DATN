package com.example.apidatn.dto


data class UserDetailDto (
        var phone:Int?=null,
        var password:String?=null,
        var role:RoleDto?=null
)