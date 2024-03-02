package com.gcc.miti.user.service

import com.gcc.miti.archive.entity.DeletedUser
import com.gcc.miti.archive.repository.DeletedUserRepository
import com.gcc.miti.auth.repository.CertificationRepository
import com.gcc.miti.auth.security.SecurityUtils
import com.gcc.miti.common.exception.BaseException
import com.gcc.miti.common.exception.BaseExceptionCode
import com.gcc.miti.user.dto.ProfileRes
import com.gcc.miti.user.dto.UpdateProfileReq
import com.gcc.miti.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val certificationRepository: CertificationRepository,
    private val deletedUserRepository: DeletedUserRepository,
) {
    @Transactional(readOnly = true)
    fun getMyProfile(): ProfileRes {
        val userId = SecurityUtils.getUserIdFromJwt()
        val user = userRepository.findByIdOrNull(userId) ?: throw BaseException(BaseExceptionCode.USER_NOT_FOUND)
        return ProfileRes.userToProfileRes(user)
    }

    @Transactional
    fun updateProfile(updateProfileReq: UpdateProfileReq): Boolean {
        val userId = SecurityUtils.getUserIdFromJwt()
        val user = userRepository.getReferenceById(userId)
        user.description = updateProfileReq.description
        user.nickname = updateProfileReq.nickname
        user.height = updateProfileReq.height
        user.weight = updateProfileReq.weight
        return true
    }

    @Transactional
    fun deleteUser(userId: String) {
        val user = userRepository.getReferenceById(userId)
        certificationRepository.getByEmail(user.userId)?.let {
            certificationRepository.delete(it)
        }
        userRepository.delete(user)
        deletedUserRepository.save(DeletedUser(user.userId))
    }
}
