package com.example.apidatn.controller

import com.example.apidatn.dto.ProductDto
import com.example.apidatn.dto.ProductIdDto
import com.example.apidatn.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping(value= ["/product"],produces = ["application/json;charset=UTF-8"])
class ProductController {

    @Autowired
    private lateinit var productService:ProductService

    @GetMapping("/phone/{phone}")
    fun getAllProduct(@PathVariable("phone") phone:Int ):ResponseEntity<MutableList<ProductDto>>
    {
        return ResponseEntity.ok(productService.getAllProduct(phone))
    }


    @GetMapping("/search/{id}")
    fun getSearchProduct(@PathVariable("id") userId: Int,@RequestParam(name="search",required = false) search:String):ResponseEntity<MutableList<ProductDto>>
    = ResponseEntity.ok(productService.getSearchProductName(userId, search))

    @GetMapping("/search/phone/{id}")
    fun getListSearchProduct(@PathVariable("id") phone: Int,@RequestParam(name="search",required = false) search:String):ResponseEntity<MutableList<ProductDto>>
            = ResponseEntity.ok(productService.searchList(phone, search))

    @GetMapping("/{id}")
    fun getProductById(@PathVariable("id") productId:Int):ResponseEntity<ProductDto>
    = ResponseEntity.ok(productService.getProductById(productId))

    @GetMapping("/list")
    fun findAllByUserId(@RequestParam("userId") userId:Int):ResponseEntity<MutableList<ProductDto>>
    = ResponseEntity.ok(productService.findAllByUserId(userId))

    @GetMapping("/status/{id}")
    fun findListProductStatusUserId(@PathVariable("id") userId: Int) : ResponseEntity<MutableList<ProductDto>>
    = ResponseEntity.ok(productService.listProductUserId(userId))

    @GetMapping("/list/cate")
    fun findAllByCategoryDetailId(@RequestParam("cateDetailId") categoryDetailId:Int):ResponseEntity<MutableList<ProductDto>>
            = ResponseEntity.ok(productService.findAllByCategoryDetailId(categoryDetailId))

    @PostMapping("/listId")
    fun getAllById(@RequestBody listProductId:List<ProductIdDto>) : ResponseEntity<MutableList<ProductDto>>
    = ResponseEntity.ok(productService.getProductListId(listProductId))

    @PostMapping("/image/{id}")
   fun saveImage(@PathVariable("id")productId: Int,@RequestParam("image") imageFile: MultipartFile):ResponseEntity<Boolean>
   = ResponseEntity.ok(productService.saveImage(productId,imageFile))

    @PostMapping("")
    fun addProduct(@RequestBody productDto: ProductDto):ResponseEntity<Boolean>
    {
        return ResponseEntity.ok(productService.addProduct(productDto))
    }

    @PostMapping("/{id}")
    fun updateProduct(@PathVariable("id") productId: Int,@RequestBody productDto: ProductDto):ResponseEntity<Boolean>
    = ResponseEntity.ok(productService.updateProduct(productDto,productId))

    @PostMapping("listImage/{id}")
    fun saveListImage(@PathVariable("id") productId: Int,@RequestParam("image") listImage: MutableList<MultipartFile>):ResponseEntity<Boolean>
    = ResponseEntity.ok(productService.saveListImage(productId, listImage))
}