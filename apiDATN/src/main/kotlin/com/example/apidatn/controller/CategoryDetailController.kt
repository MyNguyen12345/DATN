package com.example.apidatn.controller

import com.example.apidatn.dto.CategoryDetailDto
import com.example.apidatn.service.CategoryDetailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["category/detail"],produces = ["application/json;charset=UTF-8"])
class CategoryDetailController {

    @Autowired
    private lateinit var categoryDetailService: CategoryDetailService

    @GetMapping()
    fun getAllCategoryDetail():ResponseEntity<MutableList<CategoryDetailDto>>
    = ResponseEntity.ok(categoryDetailService.getAllCategoryDetail())

    @GetMapping("/product/{id}")
    fun getCategoryDetail(@PathVariable("id") productId:Int):ResponseEntity<CategoryDetailDto>
    = ResponseEntity.ok(categoryDetailService.categoryDetailByProductId(productId))

    @GetMapping("/{id}")
    fun getCategoryDetailById(@PathVariable("id") categoryDetailId:Int):ResponseEntity<CategoryDetailDto>
    =ResponseEntity.ok(categoryDetailService.getCategoryDetailById(categoryDetailId))

    @GetMapping("/categoryId")
    fun getAllCategoryDetailByCategoryId(@RequestParam(name = "categoryId") categoryId:Int):ResponseEntity<MutableList<CategoryDetailDto>>
    {
        return ResponseEntity.ok(categoryDetailService.getAllCategoryDetailByCategoryId(categoryId))
    }

    @PostMapping()
    fun addCategoryDetail(@RequestBody categoryDetailDto: CategoryDetailDto):ResponseEntity<Boolean>
    = ResponseEntity.ok(categoryDetailService.addCategoryDetail(categoryDetailDto))

    @PostMapping("/{id}")
    fun updateCategoryDetail(@PathVariable("id") categoryDetailId: Int,@RequestBody categoryDetailDto: CategoryDetailDto)
    :ResponseEntity<Boolean> = ResponseEntity.ok(categoryDetailService.updateCategoryDetail(categoryDetailId, categoryDetailDto))
}