package com.gcc.miti.module.global.dummy.data

import com.gcc.miti.module.constants.Gender
import com.gcc.miti.module.constants.Height
import com.gcc.miti.module.constants.Weight
import com.gcc.miti.module.dto.authdto.SignUpDto
import com.gcc.miti.module.dto.group.dto.CreateGroupReq
import com.gcc.miti.module.repository.UserRepository
import com.gcc.miti.module.service.GroupService
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
        const val userId1 = "test1@gachon.ac.kr"
        const val userId2 = "test2@gachon.ac.kr"
        const val userId3 = "test3@gachon.ac.kr"
        const val userId4 = "test4@gachon.ac.kr"
        const val nickname1 = "테스트닉네임1"
        const val nickname2 = "테스트닉네임2"
        const val nickname3 = "테스트닉네임3"
        const val nickname4 = "테스트닉네임4"
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
            userId1,
            "testpassword",
            "description",
            gender = Gender.MALE,
            birthDate = LocalDate.now(),
            userName = "테스트1",
            nickname = nickname1,
            height = Height.A,
            weight = Weight.A,
        )

        val user2 = SignUpDto(
            userId2,
            "testpassword",
            "description",
            gender = Gender.MALE,
            birthDate = LocalDate.now(),
            userName = "테스트2",
            nickname = nickname2,
            height = Height.A,
            weight = Weight.A,
        )

        val user3 = SignUpDto(
            userId3,
            "testpassword",
            "description",
            gender = Gender.FEMALE,
            birthDate = LocalDate.now(),
            userName = "테스트3",
            nickname = nickname3,
            height = Height.A,
            weight = Weight.A,
        )

        val user4 = SignUpDto(
            userId4,
            "testpassword",
            "description",
            gender = Gender.FEMALE,
            birthDate = LocalDate.now(),
            userName = "테스트4",
            nickname = nickname4,
            height = Height.A,
            weight = Weight.A,
        )
        val userList = mutableListOf(user1, user2, user3, user4).map {
            it.toUser(passwordEncoder)
        }
        userRepository.saveAllAndFlush(userList)
    }

    private fun addGroups() {
        groupService.makeGroup(
            CreateGroupReq(
                "첫 그룹입니다.",
                "첫 그룹 제목입니다.",
                4,
                LocalDateTime.now().plusDays(3),
                "만날 장소",
                emptyList()
            ),
            userId = userId1,
        )

        groupService.makeGroup(
            CreateGroupReq(
                "두번째 그룹입니다.",
                "두번째 그룹 제목입니다.",
                4,
                LocalDateTime.now().plusDays(3),
                "만날 장소",
                emptyList()
            ),
            userId = userId2,
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
