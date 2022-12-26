package com.example.apidatn.service

import com.example.apidatn.dto.AccountDto
import com.example.apidatn.dto.UserDetailDto
import com.example.apidatn.model.User
import com.example.apidatn.repository.UserRepository
import org.modelmapper.ModelMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailService (private val userRepository: UserRepository):UserDetailsService{
    private val mapper: ModelMapper = ModelMapper()

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    fun toEntityDto(user: User):UserDetailDto=mapper.map(user,UserDetailDto::class.java)


    override fun loadUserByUsername(username:String): UserDetails {
        if(username[0].equals(0)){
            username.removePrefix(username[0].toString())
        }
        val phone=Integer.valueOf(username)
        val userDetailDto=toEntityDto(userRepository.findUserByPhone(phone).get())
        if (userDetailDto==null){
            logger.error("User not found in database")
            throw UsernameNotFoundException("User not found in the database")
        }else{
            logger.info("User found in the database:{} $phone ")
        }
        val authorities:MutableList<SimpleGrantedAuthority> = mutableListOf()
        authorities.add((SimpleGrantedAuthority(userDetailDto.role?.roleName)))

        return  org.springframework.security.core.userdetails.User(userDetailDto.phone.toString(),userDetailDto.password,authorities)

    }
}