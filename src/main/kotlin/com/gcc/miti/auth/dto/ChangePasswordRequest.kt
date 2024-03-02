package com.gcc.miti.auth.dto

class ChangePasswordRequest(
    val email: String,
    val newPassword: String,
    val verificationNumber: String,
)
