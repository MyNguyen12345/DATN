package com.example.apidatn.model

import javax.persistence.*

@Entity
@Table(name = "role")
class Role (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "role_id")
        var roleId :Int?=null,

        @Column(name = "role_name")
        var roleName:String?=null,

        )