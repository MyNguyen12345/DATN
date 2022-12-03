package com.example.apidatn.model

import javax.persistence.*

@Entity
@Table(name = "bill_detail")
class BillDetail (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "bill_detail_id")
        var billDetailId:Int?=null,

        @Column(name = "product_id")
        var productId:Int?=null,

        @Column(name = "amount_buy")
        var amountBuy:Int?=null,

        @Column(name = "money_now")
        var moneyNow: Float?=null,

        @Column(name = "money")
        var money:Float?=null,

        @Column(name = "bill_id")
        var billId:Int ?=null,

        @ManyToOne(fetch = FetchType.LAZY,cascade = [CascadeType.MERGE])
        @JoinColumn(name = "bill_id",insertable = false,updatable = false)
        var bill:Bill?=null,

        @ManyToOne(fetch=FetchType.LAZY,cascade = [CascadeType.MERGE])
        @JoinColumn(name = "product_id",insertable = false,updatable = false)
        var product: Product?=null
        )