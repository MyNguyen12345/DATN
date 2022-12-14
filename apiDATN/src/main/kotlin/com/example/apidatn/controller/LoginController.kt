package com.example.apidatn.controller

import com.example.apidatn.constant.Constant
import com.example.apidatn.constant.SignKey
import com.example.apidatn.dto.AccountDto
import com.example.apidatn.dto.ResponseTokenDto
import com.example.apidatn.jwt.JwtSignKey
import com.example.apidatn.jwt.JwtUtil
import com.example.apidatn.repository.RoleRepository
import com.example.apidatn.repository.UserRepository
import com.example.apidatn.service.CustomUserDetailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.naming.AuthenticationException

@RestController
class LoginController (private val customUserDetailService: CustomUserDetailService){
    @Autowired
    private lateinit var jwtSignKey: JwtSignKey

    @Autowired
    private lateinit var jwtUtil: JwtUtil

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private  lateinit var  roleRepository: RoleRepository

    @Autowired
    private  lateinit var  userRepository: UserRepository
    var encoder = BCryptPasswordEncoder()


    @PostMapping(value = ["/login"],produces = ["application/json;charset=UTF-8"])
    fun loginSign(@RequestBody accountDto: AccountDto): ResponseTokenDto {
        if(accountDto.phone?.let { userRepository.findUserByPhone(it).isPresent } == true)
        {
            var user=userRepository.findUserByPhone(accountDto.phone!!).get()
          if( encoder.matches(accountDto.password,user.password)) {
              if (SignKey.privateKey==null && SignKey.publicKey==null) {
                  jwtSignKey.jwtWithRsaSign()
              }
              val privatekeyByte=Base64.getDecoder().decode(SignKey.privateKey)
              try {
                  authenticationManager.authenticate(
                          UsernamePasswordAuthenticationToken(
                                  accountDto.phone,
                                  accountDto.password
                          )
                  )
              } catch (ex: AuthenticationException) {
                  throw Exception("inavalid username/password", ex)
              }
              val userDetails = customUserDetailService.loadUserByUsername(accountDto.phone.toString())
              print(userDetails)
              var token = jwtUtil.generateToken(userDetails, jwtSignKey.decodePrivateKey(privatekeyByte))
              val role=roleRepository.findRoleByPhone(Integer.valueOf(userDetails.username))
              return ResponseTokenDto(
                      token = token,
                      phone = Integer.valueOf(userDetails.username),
                      roleName = role.roleName
              )
          }
            else{
              return ResponseTokenDto(
                      token = "token",
                      phone = 0,
                      roleName = "khong co quyen"
              )

          }

        }
        return ResponseTokenDto(
                token = "token",
                phone = 0,
                roleName = "khong co quyen"
        )

    }

}