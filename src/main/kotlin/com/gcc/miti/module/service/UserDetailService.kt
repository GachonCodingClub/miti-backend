package com.gcc.miti.module.service

import com.gcc.miti.module.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpServerErrorException
import java.util.Collections

@Service
class UserDetailService(private val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findById(username).orElseThrow { HttpServerErrorException(HttpStatus.NOT_FOUND) }
        val grantedAuthority: GrantedAuthority = SimpleGrantedAuthority("USER")
        return User(user.userId, user.password, Collections.singleton(grantedAuthority))
    }
}
