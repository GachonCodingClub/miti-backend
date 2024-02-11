package com.gcc.miti.module.dto.authdto

class ChangePasswordRequest(
    val email: String,
    val newPassword: String,
    val certificationNumber: String,
)