package com.cerberus.artishub.events.database.events

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EventsRepository : JpaRepository<EventEntity, Long> {
    fun findByIdentifier(identifier: String): EventEntity?
}
