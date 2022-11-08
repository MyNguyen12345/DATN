package com.example.apidatn.filter

import com.example.apidatn.constant.Constant
import com.example.apidatn.constant.SignKey
import com.example.apidatn.jwt.JwtSignKey
import com.example.apidatn.jwt.JwtUtil
import com.example.apidatn.service.CustomUserDetailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import java.security.PublicKey
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter


@Component
class JwtFilter: OncePerRequestFilter() {
    @Autowired
    private lateinit var jwtSignKey: JwtSignKey

    @Autowired
    private lateinit var jwtUtil: JwtUtil

    @Autowired
    private lateinit var customUserDetailService: CustomUserDetailService

    override fun doFilterInternal(
            request: HttpServletRequest,
            response: HttpServletResponse,
            filterChain: FilterChain) {
        var publicKey: PublicKey? = null
        val authorization = request.getHeader("Authorization")
        var token = ""
        var userName = ""

        if (authorization != null && authorization.startsWith("Bearer ")) {
//            val listKey = jwtSignKey.readFile(Constant.fileName)
            val publicKeyByte = Base64.getDecoder().decode(SignKey.publicKey)
            publicKey = jwtSignKey.decodePublicKey(publicKeyByte)
            token = authorization.substring(7)
            println(token)
            userName = jwtUtil.getUsernameFromToken(token, publicKey)
            println(userName)
        }
        if (!StringUtils.isEmpty(userName) && SecurityContextHolder.getContext().authentication == null) {
            val userDetails = customUserDetailService.loadUserByUsername(userName)
            if (publicKey?.let { jwtUtil.validateToken(token, userDetails, it) } == true) {
                val usernamePasswordAuthenticationToken =
                        UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
                usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
            }
        }
        filterChain.doFilter(request, response)
    }
}