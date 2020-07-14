package com.cerberus.artishub.events.database.tickets

import com.cerberus.artishub.events.database.events.EventEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TicketsRepository : JpaRepository<TicketEntity, Long> {
    fun findByIdentifier(identifier: String): TicketEntity?

    fun findAllByEvent(event: EventEntity): List<TicketEntity>
}
