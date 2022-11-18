package com.example.apidatn.security

import com.paypal.base.rest.APIContext
import com.paypal.base.rest.OAuthTokenCredential
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PaypalConfig {

//    @Value("${paypal.client.id}")
//     lateinit var clientId:String
//
//     @Value("${paypal.client.secret}")
//     lateinit var clientSecret:String
//
//     @Value("${paypal.mode}")
//     lateinit var mode:String
      @Value("\${paypal.client.id}")
     private var clientId: String? = null

    @Value("\${paypal.client.secret}")
    private val clientSecret: String? = null

    @Value("\${paypal.mode}")
    private val mode: String? = null

     @Bean
     fun paypalSdkConfig():Map<String,String>{
         val configMap = HashMap<String,String>()
         if (mode != null) {
             configMap.put("mode",mode)
         }
         return configMap
     }

    @Bean
    fun oAuthTokenCredential() : OAuthTokenCredential{
        return OAuthTokenCredential(clientId,clientSecret,paypalSdkConfig())
    }

    @Bean
    fun apiContext() : APIContext{
        val context=APIContext(oAuthTokenCredential().accessToken)
        context.configurationMap = paypalSdkConfig()
        return context
    }
}