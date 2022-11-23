package com.example.apidatn.service

import com.example.apidatn.dto.ProductDto
import com.example.apidatn.model.Image
import com.example.apidatn.model.Product
import com.example.apidatn.repository.ImageRepository
import com.example.apidatn.repository.ProductRepository
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

}