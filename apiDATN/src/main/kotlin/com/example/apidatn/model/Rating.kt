package com.example.apidatn.model

import javax.persistence.*

@Entity
@Table(name = "rating")
class Rating (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "rating_id")
        var ratingId:Int?=null,

        @Column(name = "user_id")
        var userId:Int?=null,

        @Column(name = "product_id")
        var productId:Int?=null,

        @Column(name = "rating_star")
        var ratingStar:Float?=null
        )