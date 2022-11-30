package com.example.apidatn.service

import com.example.apidatn.dto.UserCountDto
import com.example.apidatn.dto.UserInfoDto
import com.example.apidatn.model.User
import com.example.apidatn.repository.RoleRepository
import com.example.apidatn.repository.UserRepository
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collector
import java.util.stream.Collectors

@Service
class UserInfoServiceImpl():UserInfoService {

    @Autowired
    private lateinit var userRepository: UserRepository


    private val currentFolder = Paths.get(System.getProperty("user.dir"))

    private val mapper: ModelMapper = ModelMapper()

    fun toEntityDto(user: User): UserInfoDto = mapper.map(user, UserInfoDto::class.java)

    fun toDtoEntity(userInfoDto: UserInfoDto): User = mapper.map(userInfoDto, User::class.java)


    override fun getAllUser(): MutableList<UserInfoDto> = userRepository.findAll().stream().map { user: User -> toEntityDto(user) }.collect(Collectors.toList())

    override fun getUserById(userId: Int): UserInfoDto {
        return toEntityDto(userRepository.findById(userId).get())
    }


    override fun updateUser(userId: Int, userInfoDto: UserInfoDto): Boolean {
        val userInfo = toDtoEntity(userInfoDto)
        val user = userRepository.findById(userId).get()
        if (userRepository.findById(userId).isPresent) {
            user.userId = userId
            user.accountStatus = userInfo.accountStatus
            user.avatar = userInfo.avatar
            user.address = userInfo.address
            user.birthday = userInfo.birthday
            user.dateJoin = userInfo.dateJoin
            user.phone = userInfo.phone
            user.username = userInfo.username
            user.gender = userInfo.gender
            user.roleId = 2
            userRepository.save(user)
            return true
        }
        return false
    }

    override fun getAllUserByStatus(): MutableList<UserInfoDto> {
        return userRepository.findAllByAccountStatus().stream().map { user: User -> toEntityDto(user) }.collect(Collectors.toList())
    }

    override fun getAllUserRole(): MutableList<UserInfoDto> {
        return userRepository.findAllByUser().stream().map { user: User -> toEntityDto(user) }.collect(Collectors.toList())
    }

    override fun updateUserStatus(userId: Int, accountStatus: String): Boolean {
        if (userRepository.findById(userId).isPresent) {
            userRepository.updateUserByStatus(accountStatus, userId)
            return true
        }
        return false
    }

    override fun updateImage(userId: Int, userImage: MultipartFile): Boolean {
        var user = userRepository.findById(userId).get()
        val staticPath = Paths.get("static")
        val imagePath = Paths.get("images")
        if (!Files.exists(currentFolder.resolve(staticPath).resolve(imagePath))) {
            Files.createDirectories(currentFolder.resolve(staticPath).resolve(imagePath))
        }
        val file = currentFolder.resolve(staticPath).resolve(imagePath).resolve(userImage.originalFilename)
        Files.newOutputStream(file).use { os -> os.write(userImage.bytes) }
        user.avatar = imagePath.resolve(userImage.originalFilename).toString()
        userRepository.save(user)
        return true
    }

    override fun countUser(): UserCountDto {
        return  UserCountDto(
                countUser = userRepository.countByUserId(),
                countUserStatus = userRepository.countByUserIdStatus()
        )

    }

    override fun userByPhone(phone: Int): UserInfoDto {
        return toEntityDto(userRepository.findUserByPhone(phone).get())
    }
}