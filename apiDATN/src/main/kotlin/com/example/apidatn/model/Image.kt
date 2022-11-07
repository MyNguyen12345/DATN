package com.example.apidatn.model

import javax.persistence.*

@Entity
@Table(name = "image")
class Image (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "image_id")
        var imageId:Int?=null,

        @Column(name = "image_url")
        var imageUrl:String?=null,

        @Column(name="product_id")
        var productId:Int?=null,

        @ManyToOne
        @JoinColumn(name = "product_id",insertable = false, updatable = false)
        var product: Product?=null
        )