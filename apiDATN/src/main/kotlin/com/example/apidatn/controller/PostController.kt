package com.example.apidatn.controller

import com.example.apidatn.dto.NewPostDto
import com.example.apidatn.dto.PostDto
import com.example.apidatn.dto.ProductDto
import com.example.apidatn.service.PostService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping(value=["/post"],produces = ["application/json;charset=UTF-8"])
class PostController {

    @Autowired
    private lateinit var postService: PostService

    @GetMapping()
    fun getAllPost():ResponseEntity<MutableList<ProductDto>>{
        return ResponseEntity.ok(postService.statusProduct())
    }

    @GetMapping("/{id}")
    fun getPostByProductId(@PathVariable("id") productId:Int):ResponseEntity<PostDto>
    = ResponseEntity.ok(postService.postByProductId(productId))

    @PostMapping()
    fun newPost(@RequestParam ("user_id")userId:Int, @RequestParam("post_status") postStatus:String, @RequestParam("category_detail_id")categoryDetailId:Int, @RequestParam("product_name")productName:String,
                @RequestParam("product_status")  productStatus:String, @RequestParam("avatar")avatar: MultipartFile, @RequestParam("description")description:String, @RequestParam("amount_product") amountProduct:Int, @RequestParam("price_product") priceProduct :Float, @RequestParam("price_deposit")priceDeposit:Float, @RequestParam("list_image")listImage:MutableList<MultipartFile>):ResponseEntity<Boolean> {
        return ResponseEntity.ok(postService.newPost(userId, postStatus, categoryDetailId, productName, productStatus, avatar, description, amountProduct, priceProduct, priceDeposit, listImage))

    }
}