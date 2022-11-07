package com.example.apidatn.jwt

import com.example.apidatn.constant.Constant
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.io.Serializable
import java.security.PrivateKey
import java.security.PublicKey
import java.util.*
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.function.Function


@Component
class JwtUtil : Serializable {

    fun getUsernameFromToken(token: String, publicKey: PublicKey): String {
        return getClaimFromToken(token, Claims::getSubject, publicKey)
    }

    fun getExpirationDateFromToken(token: String, publicKey: PublicKey): Date {
        return getClaimFromToken(token, Claims::getExpiration, publicKey)

    }

    fun <T> getClaimFromToken(token: String, claimsResolver: Function<Claims,T>, publicKey: PublicKey): T {
        val claims = getAllClaimsFromToken(token, publicKey)
        return claimsResolver.apply(claims)
    }

    private fun isTokenExpired(token: String, publicKey: PublicKey): Boolean {
        val expiration = getExpirationDateFromToken(token, publicKey)
        return expiration.before(Date())
    }

    private fun getAllClaimsFromToken(token: String, publicKey: PublicKey): Claims {
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).body
    }

    fun generateToken(userDetails: UserDetails, privateKey: PrivateKey): String {
        val authorities = userDetails.authorities
        val claims: Map<String, Objects> = HashMap()
        return Jwts.builder().setClaims(claims).setSubject(userDetails.username.toString())
                .setIssuedAt(Date(System.currentTimeMillis()))
                .setExpiration(Date(System.currentTimeMillis() + Constant.ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .claim(Constant.AUTHORITIES_KEY, authorities)
                .compact()
    }

    fun validateToken(token: String, userDetails: UserDetails, publicKey: PublicKey): Boolean {
        val username = getUsernameFromToken(token, publicKey)
        return username == userDetails.username && !isTokenExpired(token, publicKey)
    }
}

