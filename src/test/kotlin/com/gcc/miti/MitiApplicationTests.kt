package com.gcc.miti

// @SpringBootTest
// @Profile("test")
// @Commit
// @TestMethodOrder(MethodOrderer.OrderAnnotation::class)
// class MitiApplicationTests {
//
//    @Autowired
//    lateinit var authService: AuthService
//
//    @Autowired
//    lateinit var certificationRepository: EmailVerificationRepository
//
//    @Autowired
//    lateinit var userRepository: UserRepository
//
//    @Autowired
//    lateinit var passwordEncoder: PasswordEncoder
//
//    val user = SignUpDto(
//        "test@gachon.ac.kr",
//        "testpassword1",
//        "description",
//        gender = Gender.MALE,
//        birthDate = LocalDate.now(),
//        userName = "테스트",
//        nickname = "nickname01233213213asda!@##scazdsa",
//        height = Height.A,
//        weight = Weight.A,
//    )
//
//    @Test
//    @Order(value = 1)
//    fun signUp() {
//        authService.saveMail(user.userId)
//        val randomNumber = certificationRepository.getByEmail(user.userId)?.randomNumber ?: throw Exception()
//        authService.checkCertification(user.userId, randomNumber)
//        assert(
//            authService.signUp(
//                user,
//            ),
//        )
//        val getUser = userRepository.findById(user.userId).get()
//
//        assert(user.gender == getUser.gender)
//        assert(user.userName == getUser.userName)
//        assert(user.password != getUser.password)
//        assert(passwordEncoder.matches(user.password, getUser.password))
//    }
//
//    @Test
//    @Order(value = 2)
//    @Transactional
//    fun signIn() {
//        println(userRepository.getReferenceById(user.userId).password)
//        val signInResult = authService.signIn(
//            SignInDto(
//                user.userId,
//                user.password,
//            ),
//        )
//        println(signInResult.accessToken)
//        assert(true)
//    }
//
//    @Test
//    @Order(999)
//    fun cleanDBAfter() {
//        certificationRepository.deleteById(user.userId)
//        userRepository.deleteById(user.userId)
//        assert(true)
//    }
// }
