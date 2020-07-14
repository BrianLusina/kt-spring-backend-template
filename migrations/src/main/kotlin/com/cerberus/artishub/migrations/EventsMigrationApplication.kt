package com.cerberus.artishub.migrations

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class EventsMigrationApplication

fun main(args: Array<String>) {
    SpringApplication.run(EventsMigrationApplication::class.java)
}