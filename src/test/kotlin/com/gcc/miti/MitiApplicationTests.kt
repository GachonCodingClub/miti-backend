package com.gcc.miti

import com.gcc.miti.module.constants.Gender
import com.gcc.miti.module.constants.Height
import com.gcc.miti.module.constants.Weight
import com.gcc.miti.module.dto.authdto.SignInDto
import com.gcc.miti.module.dto.authdto.SignUpDto
import com.gcc.miti.module.repository.CertificationRepository
import com.gcc.miti.module.repository.UserRepository
import com.gcc.miti.module.service.AuthService
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.annotation.Commit
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@SpringBootTest
@Profile("test")
@Commit
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class MitiApplicationTests{

    @Autowired
    lateinit var authService:AuthService

    @Autowired
    lateinit var certificationRepository: CertificationRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    val user = SignUpDto(
        "test@gachon.ac.kr",
        "testpassword1",
        "description",
        gender = Gender.MALE,
        birthDate = LocalDate.now(),
        userName = "테스트",
        nickname = "nickname01233213213asda!@##scazdsa",
        height = Height.A,
        weight = Weight.A
    )

    @Test
    @Order(value = 1)
    fun signUp() {
        authService.saveMail(user.userId)
        val randomNumber = certificationRepository.getByEmail(user.userId)?.randomNumber ?: throw Exception()
        authService.checkCertification(user.userId, randomNumber)
        assert(authService.signUp(
            user
        ))
        val getUser = userRepository.findById(user.userId).get()

        assert(user.gender == getUser.gender)
        assert(user.userName == getUser.userName)
        assert(user.password != getUser.password)
        assert(passwordEncoder.matches(user.password, getUser.password))
    }

    @Test
    @Order(value = 2)
    @Transactional
    fun signIn(){
        println(userRepository.getReferenceById(user.userId).password)
        val signInResult = authService.signIn(SignInDto(
            user.userId,
            user.password
        ))
        println(signInResult.accessToken)
        assert(true)
    }

    @Test
    @Order(999)
    fun cleanDBAfter(){
        certificationRepository.deleteById(user.userId)
        userRepository.deleteById(user.userId)
        assert(true)
    }
}
