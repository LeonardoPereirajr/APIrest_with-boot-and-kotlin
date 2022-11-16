package br.com.erudio.integrationtest.vo

import java.util.*

data class TokenVO(
    val username: String? = null,
    val authenticated: Boolean? = null,
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val created: Date? = null,
    val expiration: Date? = null,
)
