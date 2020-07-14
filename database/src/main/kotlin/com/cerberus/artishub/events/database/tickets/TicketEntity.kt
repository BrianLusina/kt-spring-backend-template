package com.cerberus.artishub.events.database.tickets

import com.cerberus.artishub.events.database.BaseEntity
import com.cerberus.artishub.events.database.events.EventEntity
import org.hibernate.annotations.Proxy
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Proxy(lazy = false)
@Entity(name = "ticket")
@Table(name = "tickets")
data class TicketEntity(
    @Column(name = "identifier", nullable = false)
    val identifier: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "ticket_type", nullable = false)
    val type: com.cerberus.artishub.events.core.models.TicketType,

    @Column(name = "description", nullable = false)
    val description: String,

    @Column(name = "price", nullable = false)
    val price: String,

    @ManyToOne
    @JoinColumn(name = "event_id")
    var event: EventEntity? = null
) : BaseEntity() {
    override fun toString(): String {
        return "Ticket identifier: $identifier, type: $type, price: $price, Event Identifier: ${event?.identifier}"
    }
}
