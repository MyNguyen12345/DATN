package com.example.apidatn.service

import com.example.apidatn.dto.ProductDto
import com.example.apidatn.dto.ProductIdDto
import com.example.apidatn.model.Image
import com.example.apidatn.model.Product
import com.example.apidatn.repository.ImageRepository
import com.example.apidatn.repository.ProductRepository
import com.example.apidatn.repository.RatingRepository
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files.*
import java.nio.file.Paths
import java.util.stream.Collectors



@Service
class ProductServiceImpl(private val productRepository: ProductRepository):ProductService {

    private val  currentFolder = Paths.get(System.getProperty("user.dir"))

    @Autowired
    private lateinit var ratingRepository: RatingRepository
    @Autowired
    private lateinit var imageRepository: ImageRepository

    private val mapper: ModelMapper = ModelMapper()

    fun toEntityDto(product: Product): ProductDto = mapper.map(product,ProductDto::class.java)

    fun toDtoEntity(productDto: ProductDto): Product =mapper.map(productDto,Product::class.java)

    override fun getAllProduct(phone:Int): MutableList<ProductDto> {
        var list :MutableList<ProductDto> = mutableListOf()
        var rating=0F
        var userRating=0
        var listProduct=productRepository.listProduct(phone)
                .stream().map { product:Product->toEntityDto(product) }
                .collect(Collectors.toList())
        println(listProduct)
        for (product in  listProduct){
            if(ratingRepository.findByProductId(product.productId!!).size>0){
                 rating = ratingRepository.avgRating(product.productId!!)
                  userRating= ratingRepository.amountRatingByUser(product.productId!!)
            }else{
                rating =0F
                userRating=0
            }
            list.add(ProductDto(
                    productId = product.productId,
                    userId = product.userId,
                    categoryDetailId = product.categoryDetailId,
                    productName = product.productName,
                    productStatus = product.productStatus,
                    avatar = product.avatar,
                    description = product.description,
                    amountProduct = product.amountProduct,
                    priceProduct = product.priceProduct,
                    priceDeposit = product.priceDeposit,
                    rating=rating,
                    userRating=userRating,
                    listImage = product.listImage
            ))
        }

        return list
    }

    override fun getProductById(productId: Int): ProductDto {
        var productDto=toEntityDto(productRepository.findById(productId).get())
        if(ratingRepository.findByProductId(productId).size>0){
            productDto.rating = ratingRepository.avgRating(productId)
            productDto.userRating= ratingRepository.amountRatingByUser(productId)
        }else{
            productDto.rating =0F
            productDto.userRating=0
        }
        return  productDto
    }

    override fun findAllByUserId(userId: Int): MutableList<ProductDto> {
        var list= productRepository.findAllByUserId(userId).stream().map { product:Product->toEntityDto(product) }
                .collect(Collectors.toList())
        for (product in list){
            if(ratingRepository.findByProductId(product.productId!!).size>0){
                product?.rating = ratingRepository.avgRating(product?.productId!!)
                product?.userRating= ratingRepository.amountRatingByUser(product?.productId!!)
            }else{
                product?.rating =0F
                product?.userRating=0
            }
        }
        return  list
    }

    override fun findAllByCategoryDetailId(categoryDetailId: Int): MutableList<ProductDto> {
        var list=productRepository.findAllByCategoryDetailId(categoryDetailId).stream().map { product:Product->toEntityDto(product) }
                .collect(Collectors.toList())
        for (product in list){
            if(ratingRepository.findByProductId(product.productId!!).size>0){
                product?.rating = ratingRepository.avgRating(product?.productId!!)
                product?.userRating= ratingRepository.amountRatingByUser(product?.productId!!)
            }else{
                product?.rating =0F
               product?.userRating=0
            }
        }
        return  list
    }

    override fun addProduct(productDto: ProductDto): Boolean  {
        val product=toDtoEntity(productDto)
        productRepository.save(product)

        return true
    }

    override fun updateProduct(productDto: ProductDto, productId: Int): Boolean {
        if(productRepository.findById(productId).isPresent){
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
            return true
        }
        return false
    }

    override fun saveImage(productId: Int, imageFile: MultipartFile): Boolean {
        val product=productRepository.findById(productId).get()
        val staticPath=Paths.get("static")
        val imagePath=Paths.get("images")
        if(!exists(currentFolder.resolve(staticPath).resolve(imagePath))){
            createDirectories(currentFolder.resolve(staticPath).resolve(imagePath))
        }
        val file=currentFolder.resolve(staticPath).resolve(imagePath).resolve(imageFile.originalFilename)
        newOutputStream(file).use { os -> os.write(imageFile.bytes) }
        product.avatar=imagePath.resolve(imageFile.originalFilename).toString()
        productRepository.save(product)
        return true
    }

    override fun saveListImage(productId: Int, listImage: MutableList<MultipartFile>): Boolean {
        val staticPath=Paths.get("static")
        val imagePath=Paths.get("images")
        if(!exists(currentFolder.resolve(staticPath).resolve(imagePath))){
            createDirectories(currentFolder.resolve(staticPath).resolve(imagePath))
        }
        for (imageFile in listImage) {
            val file = currentFolder.resolve(staticPath).resolve(imagePath).resolve(imageFile.originalFilename)
            newOutputStream(file).use { os -> os.write(imageFile.bytes) }
            val imageSave=Image(
                    productId=productId,
                    imageUrl = imagePath.resolve(imageFile.originalFilename).toString()
            )
            imageRepository.save(imageSave)
        }
        return true
    }

    override fun getProductListId(listProductId: List<ProductIdDto>): MutableList<ProductDto> {
        val list:MutableList<ProductDto> = mutableListOf()
        for (i in 0 until listProductId.size){
            var productDto=toEntityDto(productRepository.findById(listProductId[i].productId!!).get())
            if(ratingRepository.findByProductId(listProductId[i].productId!!).size>0){
                productDto.rating = ratingRepository.avgRating(listProductId[i].productId!!)
                productDto.userRating= ratingRepository.amountRatingByUser(listProductId[i].productId!!)
            }else{
                productDto.rating =0F
                productDto.userRating=0
            }
            list.add(productDto)
        }
        return list
    }

}