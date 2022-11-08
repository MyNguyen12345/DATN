package com.example.apidatn.jwt

import com.example.apidatn.constant.Constant
import com.example.apidatn.constant.SignKey
import org.springframework.stereotype.Component
import java.io.File
import java.security.*
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*

@Component
class JwtSignKey {
    fun jwtWithRsaSign(){
        val keyGenerator = KeyPairGenerator.getInstance("RSA")
        keyGenerator.initialize(1024)
        val keypair: KeyPair = keyGenerator.genKeyPair()
        val publicKey = keypair.public
        val privateKey = keypair.private
        val encodePublicKey = Base64.getEncoder().encodeToString(publicKey.encoded)
        val encodePrivateKey = Base64.getEncoder().encodeToString(privateKey.encoded)
        SignKey.publicKey=encodePublicKey
        SignKey.privateKey=encodePrivateKey
    }

    fun decodePublicKey(publicKey: ByteArray): PublicKey {
        val ks = X509EncodedKeySpec(publicKey)
        val keyFactory = KeyFactory.getInstance("RSA")
        return keyFactory.generatePublic(ks)
    }

    fun decodePrivateKey(privateKey: ByteArray): PrivateKey {
        val ks = PKCS8EncodedKeySpec(privateKey)
        val keyFactory = KeyFactory.getInstance("RSA")
        return keyFactory.generatePrivate(ks)
    }
}