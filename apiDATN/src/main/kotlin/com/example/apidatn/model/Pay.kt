package com.example.apidatn.model

import javax.persistence.*

@Entity
@Table(name = "pay")
class Pay (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "pay_id")
        var payId:Int?=null,

        @Column(name = "pay_name")
        var payName:String?=null
        )