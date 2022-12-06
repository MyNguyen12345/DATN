package com.example.apidatn.model

import javax.persistence.*

@Entity
@Table(name="cart")
class Cart (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    var cartId :Int ?= null,

    @Column(name = "user_id")
    var userId:Int?=null,

    @Column(name = "product_id")
    var productId:Int?=null,

    @Column(name = "amount_product")
    var amountProduct:Int?=null,

    @ManyToOne
    @JoinColumn(name = "product_id",insertable = false,updatable = false)
    var product:Product?=null,

    @ManyToOne
    @JoinColumn(name = "user_id",insertable = false,updatable = false)
    var user: User?=null

    )