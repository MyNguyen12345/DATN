package com.example.apidatn.model

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "post")
class Post (

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "post_id")
        var postId:Int?=null,

        @Column(name = "post_date")
        var postDate:Date?=null,

        @Column(name = "user_id")
        var userId:Int?=null,

        @Column(name = "product_id")
        var productId:Int?=null,

        @Column(name = "post_status")
        var postStatus:String?=null
        )