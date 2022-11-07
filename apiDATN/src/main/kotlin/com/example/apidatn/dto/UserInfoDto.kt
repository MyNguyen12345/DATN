package com.example.apidatn.dto


import java.util.*

data class UserInfoDto (
        var userId:Int?=null,
        var avatar:String?=null,
        var username:String?=null,
        var gender:String?=null,
        var address:String?=null,
        var birthday: Date?=null,
        var dateJoin: Date?=null,
        var accountStatus:String?=null,
        var phone:Int?=null,
        )