package com.gcc.miti.auth.security

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import java.io.IOException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.core.userdetails.UsernameNotFoundException

class JwtAuthenticationFilter(private val jwtTokenProvider: JwtTokenProvider) : GenericFilterBean() {
    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val token: String? = jwtTokenProvider.resolveToken((request as HttpServletRequest))
        if (token != null && jwtTokenProvider.validateToken(token)) {
            val authentication = try {
                jwtTokenProvider.getAuthentication(token)
            } catch (e: UsernameNotFoundException) {
                (response as HttpServletResponse).status = HttpServletResponse.SC_UNAUTHORIZED
                response.contentType = MediaType.APPLICATION_JSON_VALUE
                return
            }
            SecurityContextHolder.getContext().authentication = authentication
            chain.doFilter(request, response)
            return
        }
        chain.doFilter(request, response)
    }
}
