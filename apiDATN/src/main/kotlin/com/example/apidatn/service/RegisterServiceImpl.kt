package com.example.apidatn.service

import com.example.apidatn.dto.AccountDto
import com.example.apidatn.model.User
import com.example.apidatn.repository.UserRepository
import org.modelmapper.ModelMapper
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*


@Service
class RegisterServiceImpl(private val userRepository: UserRepository):RegisterService {

    private val mapper: ModelMapper = ModelMapper()
    var encoder = BCryptPasswordEncoder()

    fun toDtoEntity(accountDto: AccountDto): User =mapper.map(accountDto, User::class.java)
    override fun registerAccount(accountDto: AccountDto): Boolean {
        val account=toDtoEntity(accountDto)
        if(accountDto.phone?.let { userRepository.findUserByPhone(it).isPresent } == false){
            account.roleId=2
            account.password= encoder.encode(accountDto.password)
            account.accountStatus="active"
            account.dateJoin= Date(System.currentTimeMillis())
            account.avatar="images/default-person.png"
            account.address="chưa cung cấp"
            account.gender="chưa cung cấp"
            account.username="advfgggh"
            account.birthday=Date(System.currentTimeMillis())
            userRepository.save(account)
            return true
        }
        return false
    }

}