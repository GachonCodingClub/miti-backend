package com.gcc.miti.module.service

import com.gcc.miti.module.dto.user.dto.ProfileRes
import com.gcc.miti.module.dto.user.dto.UpdateProfileReq
import com.gcc.miti.module.global.exception.BaseException
import com.gcc.miti.module.global.exception.BaseExceptionCode
import com.gcc.miti.module.global.security.SecurityUtils
import com.gcc.miti.module.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(private val userRepository: UserRepository) {
    @Transactional(readOnly = true)
    fun getMyProfile():ProfileRes{
        val userId = SecurityUtils.getUserIdFromJwt()
        val user = userRepository.findByIdOrNull(userId) ?: throw BaseException(BaseExceptionCode.USER_NOT_FOUND)
        return ProfileRes.userToProfileRes(user)
    }

    @Transactional
    fun updateProfile(updateProfileReq: UpdateProfileReq){
        val userId = SecurityUtils.getUserIdFromJwt()
        val user = userRepository.getReferenceById(userId)
        user.description = updateProfileReq.description
        user.nickname = updateProfileReq.nickname
        user.height = updateProfileReq.height
        user.weight = updateProfileReq.weight
    }
}