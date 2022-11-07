package com.example.apidatn.model

import javax.persistence.*

@Entity
@Table(name = "bill_status")
class BillStatus (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "bill_status_id")
        var billStatusId:Int?=null,

        @Column(name = "bill_status_name")
        var billStatusName:String?=null
        )