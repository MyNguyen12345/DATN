package com.example.apidatn.service

import com.example.apidatn.dto.CategoryDetailDto
import com.example.apidatn.dto.ProductDto
import com.example.apidatn.model.CategoryDetail
import com.example.apidatn.model.Image
import com.example.apidatn.model.Product
import com.example.apidatn.repository.ImageRepository
import com.example.apidatn.repository.ProductRepository
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class ProductServiceImpl(private val productRepository: ProductRepository):ProductService {

    @Autowired
    private lateinit var imageRepository: ImageRepository

    private val mapper: ModelMapper = ModelMapper()

    fun toEntityDto(product: Product): ProductDto = mapper.map(product,ProductDto::class.java)

    fun toDtoEntity(productDto: ProductDto): Product =mapper.map(productDto,Product::class.java)

    override fun getAllProduct(): MutableList<ProductDto> {
        return productRepository.findAll().stream().map { product:Product->toEntityDto(product) }
                .collect(Collectors.toList())
    }

    override fun getProductById(productId: Int): ProductDto {
        return toEntityDto(productRepository.findById(productId).get())
    }

    override fun findAllByUserId(userId: Int): MutableList<ProductDto> {
        return productRepository.findAllByUserId(userId).stream().map { product:Product->toEntityDto(product) }
                .collect(Collectors.toList())
    }

    override fun findAllByCategoryDetailId(categoryDetailId: Int): MutableList<ProductDto> {
        return productRepository.findAllByCategoryDetailId(categoryDetailId).stream().map { product:Product->toEntityDto(product) }
                .collect(Collectors.toList())
    }

    override fun addProduct(productDto: ProductDto): Boolean {
        productRepository.save(toDtoEntity(productDto))
        return true
    }

    override fun updateProduct(productDto: ProductDto, productId: Int): Boolean {
        if(productRepository.findById(productId).isPresent){
            val images=productDto.listImage
            val productDtoUD=ProductDto(
                    productId=productId,
                    userId=productDto.userId,
                    categoryDetailId=productDto.categoryDetailId,
                    productName=productDto.productName,
                    productStatus=productDto.productStatus,
                    avatar=productDto.avatar,
                    description=productDto.description,
                    amountProduct=productDto.amountProduct,
                    priceProduct =productDto.priceProduct,
                    priceDeposit=productDto.priceDeposit,
            )
            productRepository.save(toDtoEntity(productDtoUD))
            val listImage=imageRepository.findImageByProductId(productId)
            if (listImage !=null  ) {
                for (imageData in listImage) {
                     if (images != null) {
                        for (image in images){
                            if (imageData.imageId==image.imageId){
                                println(image.imageUrl)
                                imageData.imageUrl=image.imageUrl
                                imageRepository.save(imageData)
                            }

                        }
                    }
                }
            }

            return true
        }
        return false
    }
}