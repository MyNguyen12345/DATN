package com.example.apidatn.model

import com.fasterxml.jackson.annotation.JsonFormat
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
        var postStatus:String?=null,

        @ManyToOne()
        @JoinColumn(name = "user_id",insertable = false,updatable = false)
        var user: User?=null,

        @OneToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "product_id",insertable = false,updatable = false)
        var product: Product?=null

        )