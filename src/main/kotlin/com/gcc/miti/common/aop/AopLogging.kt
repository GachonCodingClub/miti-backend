package com.gcc.miti.common.aop

import com.gcc.miti.auth.security.JwtTokenProvider
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder

import org.springframework.web.context.request.ServletRequestAttributes


@Aspect
@Component
@EnableAspectJAutoProxy
class AopLogging(
    private val jwtTokenProvider: JwtTokenProvider
) {
    private val logger = LoggerFactory.getLogger(AopLogging::class.java)

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *) && !within(@com.gcc.miti.common.aop.NoLogging *)")
    fun controller() {
    }

    @Before("controller()")
    fun before(joinPoint: JoinPoint) {
        val methodSignature = joinPoint.signature as MethodSignature
        logger.info("${joinPoint.target.javaClass.name} ${methodSignature.method.name}")
        joinPoint.args.forEachIndexed { index, it ->
            logger.info("arg${index + 1} class: ${it.javaClass.name} value: $it")
        }
        val requestAttributes = RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes
        val request = requestAttributes.request
        val authorization: String? = request.getHeader("Authorization")
        if (authorization != null) {
            try {
                logger.info("userId : ${jwtTokenProvider.getUserPk(authorization)}")
            }
            catch (_: Exception) { }
        }
    }
}