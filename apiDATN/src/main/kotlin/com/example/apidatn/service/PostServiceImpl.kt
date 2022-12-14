package com.example.apidatn.service

import com.example.apidatn.dto.*
import com.example.apidatn.model.Image
import com.example.apidatn.model.Post
import com.example.apidatn.model.Product
import com.example.apidatn.model.User
import com.example.apidatn.repository.ImageRepository
import com.example.apidatn.repository.PostRepository
import com.example.apidatn.repository.ProductRepository
import com.example.apidatn.repository.UserRepository
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import java.util.stream.Collectors

@Service
class PostServiceImpl():PostService {

    private val  currentFolder = Paths.get(System.getProperty("user.dir"))

    @Autowired
    private lateinit var postRepository: PostRepository

    @Autowired
    private lateinit var productRepository: ProductRepository

    @Autowired
    private lateinit var imageRepository: ImageRepository

    @Autowired
    private lateinit var userRepository:UserRepository

    private val mapper: ModelMapper = ModelMapper()


    fun toDtoEntityProduct(productDto: ProductDto): Product =mapper.map(productDto, Product::class.java)

    fun toDtoEntityImage(imageDto: ImageDto): Image =mapper.map(imageDto, Image::class.java)

    fun toDtoEntityPost(postDto: PostDto): Post =mapper.map(postDto, Post::class.java)

    fun toEntityDtoProduct(product: Product): ProductDto =mapper.map(product, ProductDto::class.java)
    fun toEntityDtoUser(user: User): UserInfoDto =mapper.map(user, UserInfoDto::class.java)


    fun toEntityDtoImage(image: Image): ImageDto =mapper.map(image, ImageDto::class.java)

    fun toEntityDtoPost(post: Post): PostDto =mapper.map(post, PostDto::class.java)





    override fun newPost(userId: Int, postStatus: String, categoryDetailId: Int, productName: String, productStatus: String, avatar: MultipartFile, description: String, amountProduct: Int, priceProduct: Float, priceDeposit: Float, listImage: MutableList<MultipartFile>): Boolean {
        val postDate=Date(System.currentTimeMillis())
        val userId=userId
        val staticPath= Paths.get("static")
        val imagePath= Paths.get("images")
        if(!Files.exists(currentFolder.resolve(staticPath).resolve(imagePath))){
            Files.createDirectories(currentFolder.resolve(staticPath).resolve(imagePath))
        }
        val file=currentFolder.resolve(staticPath).resolve(imagePath).resolve(avatar.originalFilename)
        Files.newOutputStream(file).use { os -> os.write(avatar.bytes) }
        val product=Product(
                userId=userId,
                categoryDetailId=categoryDetailId,
                productName=productName,
                productStatus=productStatus,
                avatar=imagePath.resolve(avatar.originalFilename).toString(),
                description=description,
                amountProduct=amountProduct,
                priceProduct =priceProduct,
                priceDeposit=priceDeposit
        )
        productRepository.save(product)
        val productId=product.productId
        val post=Post(
                postDate=postDate,
                userId=userId,
                productId=productId,
                postStatus="Ch??? x??c nh???n"
        )
        postRepository.save(post)

        for (imageFile in listImage) {
            val file = currentFolder.resolve(staticPath).resolve(imagePath).resolve(imageFile.originalFilename)
            Files.newOutputStream(file).use { os -> os.write(imageFile.bytes) }
            val imageSave=Image(
                    productId=productId,
                    imageUrl = imagePath.resolve(imageFile.originalFilename).toString()
            )
            imageRepository.save(imageSave)


        }

        return true
    }

    override fun statusProduct(): MutableList<ProductDto> {
       return productRepository.findAllProductByPostStatus().stream().map { product:Product->toEntityDtoProduct(product) }
               .collect(Collectors.toList())

    }

    override fun postByProductId(productId: Int): PostDto {
        return toEntityDtoPost(postRepository.findPostByProductId(productId).get())
    }

    override fun listPostStatus(): MutableList<PostStatusDto> {
       var list:MutableList<PostStatusDto> = mutableListOf()
        var listId= postRepository.postByUserId()
        for (userId in listId){
            var postStatusDto=  PostStatusDto(
                  listProduct = productRepository.findPostStatus(userId).stream().map { product:Product->toEntityDtoProduct(product) }
                          .collect(Collectors.toList()),
                    userInfoDto = toEntityDtoUser(userRepository.findById(userId).get())
            )
            list.add(postStatusDto)

        }
        return list
    }

    override fun updatePostStatus(productId: Int, postStatus: String): Boolean {
        if(postRepository.findPostByProductId(productId).isPresent){
            var post=postRepository.findPostByProductId(productId).get()
            post.postStatus=postStatus
            postRepository.save(post)
            return true
        }
        return false
    }

}