package com.example.apidatn.service

import com.example.apidatn.dto.AccountDto
import com.example.apidatn.model.User
import com.example.apidatn.repository.UserRepository
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service

@Service
class RegisterServiceImpl(private val userRepository: UserRepository):RegisterService {

    private val mapper: ModelMapper = ModelMapper()
    fun toDtoEntity(accountDto: AccountDto): User =mapper.map(accountDto, User::class.java)
    override fun registerAccount(accountDto: AccountDto): Boolean {
        val account=toDtoEntity(accountDto)
        if(accountDto.phone?.let { userRepository.findUserByPhone(it).isPresent } == false){
            account.roleId=2
            account.accountStatus="2"
            userRepository.save(account)
            return true
        }
        return false
    }

}