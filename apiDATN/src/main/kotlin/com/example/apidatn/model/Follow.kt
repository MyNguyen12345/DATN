package com.example.apidatn.model

import javax.persistence.*

@Entity
@Table(name = "follow")
class Follow (

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "follow_id")
        var followId:Int?=null,

        @Column(name = "follower_id")
        var followerId : Int?=null,

        @Column(name = "user_follow_id")
        var userFollowId:Int?=null,

        @ManyToOne
        @JoinColumn(name = "user_id",insertable = false,updatable = false)
        var userFollower:User?=null,

        @ManyToOne
        @JoinColumn(name = "user_id",insertable = false,updatable = false)
        var userFollowed:User?=null
        )