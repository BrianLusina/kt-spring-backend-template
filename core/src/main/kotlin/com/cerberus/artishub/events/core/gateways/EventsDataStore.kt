package com.cerberus.artishub.events.core.gateways

import com.cerberus.artishub.events.core.models.Event
import com.cerberus.artishub.events.core.models.EventIdentifier
import com.cerberus.artishub.events.core.models.Ticket
import com.cerberus.artishub.events.core.usecases.exceptions.EventsException
import org.w3c.dom.events.EventException
import java.time.LocalDateTime
import java.time.LocalTime

interface EventsDataStore {
    /**
     * Get all events from data store
     * @return [Collection] Collection of Event
     */
    @Throws(EventsException::class)
    fun getAll(): Set<Event>

    @Throws(EventsException::class)
    fun getItem(identifier: EventIdentifier): Event?

    @Suppress("LongParameterList")
    @Throws(EventsException::class)
    fun create(
        organization: String,
        type: String,
        date: LocalDateTime,
        startTime: LocalTime,
        endTime: LocalTime,
        title: String,
        description: String,
        location: String,
        bannerImageUrl: String,
        imageUrl: String,
        tickets: Collection<Ticket>
    ): EventIdentifier

    @Suppress("LongParameterList")
    @Throws(EventException::class)
    fun update(
        identifier: EventIdentifier,
        organization: String,
        type: String,
        date: LocalDateTime,
        startTime: LocalTime,
        endTime: LocalTime,
        title: String,
        description: String,
        location: String,
        bannerImageUrl: String,
        imageUrl: String,
        tickets: Collection<Ticket>
    ): Boolean

    @Throws(EventException::class)
    fun delete(identifier: EventIdentifier): Boolean
}
