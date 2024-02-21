package com.gcc.miti.dto.authdto

class ChangePasswordRequest(
    val email: String,
    val newPassword: String,
    val certificationNumber: String,
)