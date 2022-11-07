package com.example.apidatn.model

import javax.persistence.*

@Entity
@Table(name = "category_detail")
class CategoryDetail (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "category_detail_id")
        var categoryDetailId:Int?=null,

        @Column(name = "category_detail_name")
        var categoryDetailName:String?=null,

        @Column(name = "category_id")
        var categoryId:Int?=null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "category_id",insertable = false,updatable = false)
        var category: Category?=null,

//        @OneToMany(fetch = FetchType.LAZY,mappedBy = "category_detail",cascade =[CascadeType.MERGE,CascadeType.PERSIST])
//        var listProduct:MutableList<Product>?=null

        )