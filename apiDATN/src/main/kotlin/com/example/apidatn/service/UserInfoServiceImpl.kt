package com.example.apidatn.service

import com.example.apidatn.dto.AccountDto
import com.example.apidatn.dto.UserInfoDto
import com.example.apidatn.model.User
import com.example.apidatn.repository.RoleRepository
import com.example.apidatn.repository.UserRepository
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.stream.Collector
import java.util.stream.Collectors

@Service
class UserInfoServiceImpl(private val userRepository: UserRepository):UserInfoService {

    private val mapper:ModelMapper= ModelMapper()

    fun toEntityDto(user:User):UserInfoDto = mapper.map(user,UserInfoDto::class.java)

    fun toDtoEntity(userInfoDto:UserInfoDto):User=mapper.map(userInfoDto,User::class.java)



    override fun getAllUser(): MutableList<UserInfoDto> = userRepository.findAll().stream().map { user:User->toEntityDto(user) }.collect(Collectors.toList())

    override fun getUserById(userId: Int): UserInfoDto {
        return toEntityDto(userRepository.findById(userId).get())
    }



    override fun updateUser(userId: Int,userInfoDto: UserInfoDto): Boolean {
        val userInfo=toDtoEntity(userInfoDto)
        val user=userRepository.findById(userId).get()
       if (userRepository.findById(userId).isPresent){
           user.userId=userId
           user.accountStatus=userInfo.accountStatus
           user.avatar=userInfo.avatar
           user.address=userInfo.address
           user.birthday=userInfo.birthday
           user.dateJoin=userInfo.dateJoin
           user.phone=userInfo.phone
           user.username=userInfo.username
           user.gender=userInfo.gender
           userRepository.save(user)
           return true
       }
        return false
    }

    override fun getAllUserByStatus(): MutableList<UserInfoDto> {
        return userRepository.findAllByAccountStatus().stream().map { user:User->toEntityDto(user) }.collect(Collectors.toList())
    }
}