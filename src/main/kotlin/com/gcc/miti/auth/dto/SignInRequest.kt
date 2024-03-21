package com.gcc.miti.auth.dto

data class SignInRequest(
    val userId: String,
    val password: String,
)
