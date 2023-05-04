package com.gcc.websocketexample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories
class WebsocketExampleApplication

fun main(args: Array<String>) {
    runApplication<WebsocketExampleApplication>(*args)
}
