package com.example.apidatn.service

import com.example.apidatn.dto.ImageDto
import com.example.apidatn.dto.NewPostDto
import com.example.apidatn.dto.PostDto
import com.example.apidatn.dto.ProductDto
import com.example.apidatn.model.Image
import com.example.apidatn.model.Post
import com.example.apidatn.model.Product
import com.example.apidatn.repository.ImageRepository
import com.example.apidatn.repository.PostRepository
import com.example.apidatn.repository.ProductRepository
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors

@Service
class PostServiceImpl():PostService {

    @Autowired
    private lateinit var postRepository: PostRepository

    @Autowired
    private lateinit var productRepository: ProductRepository

    @Autowired
    private lateinit var imageRepository: ImageRepository

    private val mapper: ModelMapper = ModelMapper()


    fun toDtoEntityProduct(productDto: ProductDto): Product =mapper.map(productDto, Product::class.java)

    fun toDtoEntityImage(imageDto: ImageDto): Image =mapper.map(imageDto, Image::class.java)

    fun toDtoEntityPost(postDto: PostDto): Post =mapper.map(postDto, Post::class.java)

    fun toEntityDtoProduct(product: Product): ProductDto =mapper.map(product, ProductDto::class.java)

    fun toEntityDtoImage(image: Image): ImageDto =mapper.map(image, ImageDto::class.java)

    fun toEntityDtoPost(post: Post): PostDto =mapper.map(post, PostDto::class.java)




    override fun newPost(newPostDto: NewPostDto): Boolean {
        val postDate=Date(System.currentTimeMillis())
        val userId=newPostDto.userId
        val product=Product(
                userId=userId,
                categoryDetailId=newPostDto.categoryDetailId,
                productName=newPostDto.productName,
                productStatus=newPostDto.productStatus,
                avatar=newPostDto.avatar,
                description=newPostDto.description,
                amountProduct=newPostDto.amountProduct,
                priceProduct =newPostDto.priceProduct,
                priceDeposit=newPostDto.priceDeposit,
        )
        productRepository.save(product)
        val productId=product.productId
        val post=Post(
                postDate=postDate,
                userId=userId,
                productId=productId,
                postStatus="active"
        )
        postRepository.save(post)


        val listImage=newPostDto.listImage
        if (listImage != null) {
            for (image in listImage){
                val image=Image(
                        imageUrl = image.imageUrl,
                        productId = productId
                )
                println(image.productId)
                imageRepository.save(image)
            }
        }

        return true
    }

    override fun statusProduct(): MutableList<ProductDto> {
       return productRepository.findAllProductByPostStatus().stream().map { product:Product->toEntityDtoProduct(product) }
               .collect(Collectors.toList())

    }

    override fun postByProductId(productId: Int): PostDto {
        return toEntityDtoPost(postRepository.findPostByProductId(productId))
    }

}