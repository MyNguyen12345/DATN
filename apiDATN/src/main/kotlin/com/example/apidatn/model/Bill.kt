package com.example.apidatn.model

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "bill")
class Bill (

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "bill_id")
        var billId:Int?=null,

        @Column(name = "bill_status_id")
        var billStatusId:Int?=null,

        @Column(name = "user_id")
        var userId:Int?=null,

        @Column(name = "date_bill")
        var dateBill:Date?=null,

        @Column(name = "total_price")
        var totalPrice:Float?=null,

        @Column(name = "address_bill")
        var addressBill:String?=null,

        @Column(name = "pay_id")
        var pay_id:Int?=null,

        @Column(name = "pay_status")
        var payStatus:String?=null,

        @OneToMany(mappedBy = "bill",cascade = [CascadeType.ALL],fetch = FetchType.LAZY)
        var listBillDetail:MutableList<BillDetail>?=null,

        @ManyToOne
        @JoinColumn(name = "bill_status_id",insertable = false,updatable = false)
        var billStatus:BillStatus?=null,

        @ManyToOne
        @JoinColumn(name = "pay_id",insertable = false,updatable = false)
        var pay:Pay?=null
        )