package com.example.apidatn.dto

import javax.persistence.Column


data class RatingDto (
        var userId:Int?=null,
        var productId:Int?=null,
        var ratingStar:Float?=null,
        )