package com.gcc.miti.module.service

import com.gcc.miti.module.dto.user.dto.ProfileRes
import com.gcc.miti.module.global.security.SecurityUtils
import com.gcc.miti.module.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(private val userRepository: UserRepository) {
    @Transactional(readOnly = true)
    fun getMyProfile():ProfileRes{
        val userId = SecurityUtils.getUserIdFromJwt()
        val user = userRepository.getReferenceById(userId)
        return ProfileRes.userToProfileRes(user)
    }
}