package com.cerberus.artishub.events

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import java.util.TimeZone
import javax.annotation.PostConstruct

@SpringBootApplication
@EntityScan("com.cerberus.artishub.events.database")
@EnableJpaRepositories("com.cerberus.artishub.events.database")
class EventsApplication {
    @PostConstruct
    fun started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Africa/Nairobi"))
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(EventsApplication::class.java)
}
