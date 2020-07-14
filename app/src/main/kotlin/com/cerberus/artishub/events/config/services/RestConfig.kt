package com.cerberus.artishub.events.config.services

import org.apache.http.conn.ssl.NoopHostnameVerifier
import org.apache.http.conn.ssl.TrustSelfSignedStrategy
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import org.apache.http.ssl.SSLContextBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate

@Configuration
class RestConfig {
    companion object {
        private const val CONNECTION_TIMEOUT = 15000
    }

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }

    @Bean("customRestTemplate")
    fun customRestTemplate(): RestTemplate {
        val sslContext = SSLContextBuilder()
        sslContext.loadTrustMaterial(null, TrustSelfSignedStrategy())

        val httpClient: CloseableHttpClient = HttpClients.custom()
            .setSSLContext(sslContext.build())
            .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
            .build()

        val requestFactory = HttpComponentsClientHttpRequestFactory()

        requestFactory.httpClient = httpClient
        requestFactory.setConnectTimeout(CONNECTION_TIMEOUT)

        return RestTemplate(requestFactory)
    }
}
