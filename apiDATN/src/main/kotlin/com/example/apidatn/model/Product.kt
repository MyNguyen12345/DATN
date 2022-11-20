package com.example.apidatn.model

import javax.persistence.*

@Entity
@Table(name = "product")
class Product(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "product_id")
        var productId:Int?=null,

        @Column(name = "user_id")
        var userId:Int?=null,

        @Column(name = "category_detail_id")
        var categoryDetailId:Int?=null,

        @Column(name = "product_name")
        var productName:String?=null,

        @Column(name = "product_status")
        var productStatus:String?=null,

        @Column(name = "avatar")
        var avatar:String?=null,

        @Column(name = "description")
        var description:String?=null,

        @Column(name = "amount_product")
        var amountProduct:Int?=null,

        @Column(name = "price_product")
        var priceProduct :Int?=null,

        @Column(name = "price_deposit")
        var priceDeposit:Int?=null,

        @Column(name = "status")
        var status:String?=null,

        @OneToMany(mappedBy = "product")
        var listImage:MutableList<Image>?=null,

        @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
        @JoinColumn(name = "user_id",insertable = false,updatable = false)
        var user: User? = null,

        @ManyToOne()
        @JoinColumn(name = "category_detail_id",insertable = false,updatable = false)
        var categoryDetail: CategoryDetail? = null




)