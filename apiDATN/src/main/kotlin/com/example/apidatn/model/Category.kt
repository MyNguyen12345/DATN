package com.example.apidatn.model

import javax.persistence.*

@Entity
@Table(name = "category")
class Category (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "category_id")
        var categoryId:Int?=null,

        @Column(name = "category_name")
        var categoryName:String?=null,

        @Column(name = "category_icon")
        var categoryIcon:String?=null,

        @OneToMany(mappedBy = "category")
        var categoryDetail:MutableList<CategoryDetail>?=null

        )