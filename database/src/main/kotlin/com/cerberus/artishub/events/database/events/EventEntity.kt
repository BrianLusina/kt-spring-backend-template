package com.cerberus.artishub.events.database.events

import com.cerberus.artishub.events.database.BaseEntity
import com.cerberus.artishub.events.database.tickets.TicketEntity
import java.time.LocalDateTime
import java.time.LocalTime
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity(name = "event")
@Table(name = "events")
data class EventEntity(
    @Column(name = "identifier", nullable = false)
    var identifier: String,

    @Column(name = "organization", nullable = false, unique = true)
    var organization: String,

    @Column(name = "event_type", nullable = false, unique = true)
    var type: String,

    @Column(name = "date_of_event", nullable = false, unique = true)
    var date: LocalDateTime,

    @Column(name = "start_time", nullable = false, unique = true)
    var startTime: LocalTime,

    @Column(name = "end_time", nullable = false, unique = true)
    var endTime: LocalTime,

    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "description", nullable = false)
    var description: String,

    @Column(name = "location", nullable = false)
    var location: String,

    @Column(name = "banner_image_url", nullable = false)
    var bannerImageUrl: String,

    @Column(name = "image_url", nullable = false)
    var imageUrl: String,

    @OneToMany(
        fetch = FetchType.EAGER,
        mappedBy = "event",
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    var tickets: MutableSet<TicketEntity> = mutableSetOf()
) : BaseEntity() {
    override fun toString(): String {
        return "Event: Identifier: $identifier"
    }
}
