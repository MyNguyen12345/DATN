package com.example.apidatn.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "user_info")
class User (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "user_id",nullable = false)
        var userId:Int?=null,

        @Column(name = "avatar")
        var avatar:String?=null,

        @Column(name = "username")
        var username:String?=null,

        @Column(name = "gender")
        var gender:String?=null,

        @Column(name = "address")
        var address:String?=null,

        @Column(name = "birthday")
        var birthday: Date?=null,

        @Column(name = "date_join")
        var dateJoin:Date?=null,

        @Column(name = "account_status")
        var accountStatus:String?=null,

        @Column(name = "phone")
        var phone:Int?=null,

        @Column(name = "password")
        var password:String?=null,

        @Column(name = "role_id")
        var roleId:Int?=null,

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "role_id",insertable = false,updatable = false)
        var role:Role?=null,

//        @OneToMany(mappedBy = "product",cascade = [CascadeType.ALL],fetch = FetchType.LAZY)
//        var listProduct:MutableList<Product>


        )


