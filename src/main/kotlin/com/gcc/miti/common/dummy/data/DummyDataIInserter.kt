package com.gcc.miti.common.dummy.data

import com.gcc.miti.user.constants.Gender
import com.gcc.miti.user.constants.Height
import com.gcc.miti.user.constants.Weight
import com.gcc.miti.auth.dto.SignUpDto
import com.gcc.miti.group.dto.CreateGroupReq
import com.gcc.miti.user.repository.UserRepository
import com.gcc.miti.group.service.GroupService
import org.slf4j.LoggerFactory
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime

@Component
class DummyDataIInserter(
    private val jdbcTemplate: JdbcTemplate,
    private val groupService: GroupService,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) {
    companion object {
        const val USER_ID_1 = "test1@gachon.ac.kr"
        const val USER_ID_2 = "test2@gachon.ac.kr"
        const val USER_ID_3 = "test3@gachon.ac.kr"
        const val USER_ID_4 = "test4@gachon.ac.kr"
        const val NICKNAME_1 = "테스트닉네임1"
        const val NICKNAME_2 = "테스트닉네임2"
        const val NICKNAME_3 = "테스트닉네임3"
        const val NICKNAME_4 = "테스트닉네임4"
    }

    private val logger = LoggerFactory.getLogger(this::class.java)

    @EventListener(ApplicationReadyEvent::class)
    @Transactional
    fun putDummyData() {
        if (checkInit()) {
            return
        }
        insertDummies()
        finishJob()
    }

    private fun insertDummies() {
        addDummyUsers()
        addGroups()
    }

    private fun addDummyUsers() {
        val user1 = SignUpDto(
            USER_ID_1,
            "testpassword",
            "description",
            gender = Gender.MALE,
            birthDate = LocalDate.of(2000, 1, 26),
            nickname = NICKNAME_1,
            height = Height.A,
            weight = Weight.A,
        )

        val user2 = SignUpDto(
            USER_ID_2,
            "testpassword",
            "description",
            gender = Gender.MALE,
            birthDate = LocalDate.of(1997, 5, 29),
            nickname = NICKNAME_2,
            height = Height.A,
            weight = Weight.A,
        )

        val user3 = SignUpDto(
            USER_ID_3,
            "testpassword",
            "description",
            gender = Gender.FEMALE,
            birthDate = LocalDate.of(2005, 8, 22),
            nickname = NICKNAME_3,
            height = Height.A,
            weight = Weight.A,
        )

        val user4 = SignUpDto(
            USER_ID_4,
            "testpassword",
            "description",
            gender = Gender.FEMALE,
            birthDate = LocalDate.of(1998, 3, 2),
            nickname = NICKNAME_4,
            height = Height.A,
            weight = Weight.A,
        )
        val userList = mutableListOf(user1, user2, user3, user4).map {
            it.toUser(passwordEncoder)
        }
        userRepository.saveAllAndFlush(userList)
    }

    private fun addGroups() {
        groupService.createGroup(
            CreateGroupReq(
                "첫 그룹입니다.",
                "첫 그룹 제목입니다.",
                4,
                LocalDateTime.now().plusDays(9),
                "만날 장소",
                emptyList()
            ),
            userId = USER_ID_1,
        )

        groupService.createGroup(
            CreateGroupReq(
                "두번째 그룹입니다.",
                "두번째 그룹 제목입니다.",
                4,
                LocalDateTime.now().plusDays(5),
                "만날 장소",
                emptyList()
            ),
            userId = USER_ID_2,
        )
    }

    private fun checkInit(): Boolean {
        var count: Long = 0
        jdbcTemplate.execute(
            " create table if not exists init_dummy (value bool)  ",
        )
        jdbcTemplate.query(" select count(*) as count from init_dummy ") { rs, rownum ->
            count = rs.getLong("count")
        }
        return if (count > 0) {
            logger.info("skipping dummy data insert")
            true
        } else {
            false
        }
    }

    private fun finishJob() {
        jdbcTemplate.execute(" insert into init_dummy values (true) ")
        logger.info("dummy data inserted")
    }
}
