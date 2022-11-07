package com.example.apidatn.controller

import com.example.apidatn.dto.NewPostDto
import com.example.apidatn.dto.PostDto
import com.example.apidatn.dto.ProductDto
import com.example.apidatn.service.PostService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/post")
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
    fun newPost(@RequestBody newPostDto: NewPostDto):ResponseEntity<Boolean> {
        return ResponseEntity.ok(postService.newPost(newPostDto)
        )

    }
}