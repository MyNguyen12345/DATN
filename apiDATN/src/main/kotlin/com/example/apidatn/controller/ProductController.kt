package com.example.apidatn.controller

import com.example.apidatn.dto.ProductDto
import com.example.apidatn.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/product")
class ProductController {

    @Autowired
    private lateinit var productService:ProductService

    @GetMapping()
    fun getAllProduct():ResponseEntity<MutableList<ProductDto>>
    =ResponseEntity.ok(productService.getAllProduct())

    @GetMapping("/{id}")
    fun getProductById(@PathVariable("id") productId:Int):ResponseEntity<ProductDto>
    = ResponseEntity.ok(productService.getProductById(productId))

    @GetMapping("/list")
    fun findAllByUserId(@RequestParam("userId") userId:Int):ResponseEntity<MutableList<ProductDto>>
    = ResponseEntity.ok(productService.findAllByUserId(userId))

    @GetMapping("/list/cate")
    fun findAllByCategoryDetailId(@RequestParam("cateDetailId") categoryDetailId:Int):ResponseEntity<MutableList<ProductDto>>
            = ResponseEntity.ok(productService.findAllByCategoryDetailId(categoryDetailId))

    @PostMapping("")
    fun addProduct(@RequestBody productDto: ProductDto):ResponseEntity<Boolean>
    = ResponseEntity.ok(productService.addProduct(productDto))

    @PostMapping("/{id}")
    fun updateProduct(@PathVariable("id") productId: Int,@RequestBody productDto: ProductDto):ResponseEntity<Boolean>
    = ResponseEntity.ok(productService.updateProduct(productDto,productId))
}