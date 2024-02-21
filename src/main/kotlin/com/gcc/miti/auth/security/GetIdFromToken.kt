package com.gcc.miti.auth.security

import com.gcc.miti.common.exception.BaseException
import com.gcc.miti.common.exception.BaseExceptionCode
import org.springframework.context.annotation.Configuration
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import javax.servlet.http.HttpServletRequest

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class GetIdFromToken

@Component
class GetIdFromTokenArgumentResolver(
    private val jwtTokenProvider: JwtTokenProvider,
) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.getParameterAnnotation(GetIdFromToken::class.java) != null
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): String {
        val request: HttpServletRequest =
            webRequest.getNativeRequest(HttpServletRequest::class.java) as HttpServletRequest
        val authorizationHeader =
            request.getHeader("Authorization") ?: throw BaseException(BaseExceptionCode.AUTHORIZATION_HEADER_NULL)
        return jwtTokenProvider.getUserPk(authorizationHeader.substring(6))
    }
}

@Configuration
class WebMvcConfig(
    private val getIdFromTokenArgumentResolver: GetIdFromTokenArgumentResolver,
) : WebMvcConfigurer {
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(getIdFromTokenArgumentResolver)
    }
}
