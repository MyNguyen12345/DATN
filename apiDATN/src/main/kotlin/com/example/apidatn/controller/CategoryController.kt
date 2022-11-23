package com.example.apidatn.controller

import com.example.apidatn.dto.CategoryDto
import com.example.apidatn.service.CategoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value=["/category"],produces = ["application/json;charset=UTF-8"])
class CategoryController {

    @Autowired
    private lateinit var categoryService: CategoryService

    @GetMapping("")
    fun getAllCategory():ResponseEntity<MutableList<CategoryDto>> = ResponseEntity.ok(categoryService.getAllCategory())

    @GetMapping("/{id}")
    fun getCategoryById(@PathVariable("id") categoryId:Int):ResponseEntity<CategoryDto> = ResponseEntity.ok(categoryService.getCategoryById(categoryId))

    @PostMapping("")
    fun addCategory(@RequestBody categoryDto: CategoryDto):ResponseEntity<Boolean> = ResponseEntity.ok(categoryService.addCategory(categoryDto))

    @PostMapping("/{id}")
    fun updateCategory(@PathVariable("id") categoryId: Int,@RequestBody categoryDto: CategoryDto):ResponseEntity<Boolean>
    = ResponseEntity.ok(categoryService.updateCategory(categoryId, categoryDto))


}