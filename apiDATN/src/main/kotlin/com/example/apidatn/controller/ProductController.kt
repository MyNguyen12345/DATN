package com.example.apidatn.controller

import com.example.apidatn.dto.ProductDto
import com.example.apidatn.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value= ["/product"],produces = ["application/json;charset=UTF-8"])
class ProductController {

    @Autowired
    private lateinit var productService:ProductService

    @GetMapping()
    fun getAllProduct():ResponseEntity<MutableList<ProductDto>>
    {
        return ResponseEntity.ok(productService.getAllProduct())
    }

    @GetMapping("/{id}")
    fun getProductById(@PathVariable("id") productId:Int):ResponseEntity<ProductDto>
    = ResponseEntity.ok(productService.getProductById(productId))

    @GetMapping("/list")
    fun findAllByUserId(@RequestParam("userId") userId:Int):ResponseEntity<MutableList<ProductDto>>
    = ResponseEntity.ok(productService.findAllByUserId(userId))

    @GetMapping("/list/cate/{cateDetailId}")
    fun findAllByCategoryDetailId(@RequestParam("cateDetailId") categoryDetailId:Int, @PathVariable cateDetailId: String):ResponseEntity<MutableList<ProductDto>>
            = ResponseEntity.ok(productService.findAllByCategoryDetailId(categoryDetailId))

//    @PostMapping("/image/{id}")
//   fun saveImage(@PathVariable("id")productId: Int,@RequestParam("image") imageFile: MultipartFile):ResponseEntity<Boolean>
//   = ResponseEntity.ok(productService.saveImage(productId,imageFile))
//
//    @PostMapping("")
//    fun addProduct(@RequestBody productDto: ProductDto):ResponseEntity<Boolean>
//    {
//        return ResponseEntity.ok(productService.addProduct(productDto))
//    }
//
//    @PostMapping("/{id}")
//    fun updateProduct(@PathVariable("id") productId: Int,@RequestBody productDto: ProductDto):ResponseEntity<Boolean>
//    = ResponseEntity.ok(productService.updateProduct(productDto,productId))
//
//    @PostMapping("listImage/{id}")
//    fun saveListImage(@PathVariable("id") productId: Int,@RequestParam("image") listImage: MutableList<MultipartFile>):ResponseEntity<Boolean>
//    = ResponseEntity.ok(productService.saveListImage(productId, listImage))
}