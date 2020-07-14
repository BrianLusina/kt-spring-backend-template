package com.cerberus.artishub.events.database

import com.cerberus.artishub.events.core.gateways.EventsDataStore
import com.cerberus.artishub.events.core.utils.generateIdentifier
import com.cerberus.artishub.events.database.events.EventEntity
import com.cerberus.artishub.events.database.events.EventsRepository
import com.cerberus.artishub.events.database.tickets.TicketEntity
import com.cerberus.artishub.events.database.tickets.TicketsRepository
import org.slf4j.LoggerFactory
import java.time.LocalDateTime
import java.time.LocalTime

@Suppress("TooGenericExceptionCaught")
class EventsDataStoreImpl(
    private val eventsRepository: EventsRepository,
    private val ticketsRepository: TicketsRepository
) : EventsDataStore {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun getAll(): Set<com.cerberus.artishub.events.core.models.Event> {
        logger.info("Fetching events from data store")

        @Suppress("TooGenericExceptionCaught")
        try {
            val events = eventsRepository.findAll()
            return events.map {
                com.cerberus.artishub.events.core.models.Event(
                    identifier = it.identifier,
                    organization = it.organization,
                    type = it.type,
                    date = it.date,
                    startTime = it.startTime,
                    endTime = it.endTime,
                    title = it.title,
                    description = it.description,
                    location = it.location,
                    bannerImageUrl = it.bannerImageUrl,
                    imageUrl = it.imageUrl,
                    tickets = it.tickets.map { ticket ->
                        com.cerberus.artishub.events.core.models.Ticket(
                            identifier = ticket.identifier,
                            type = ticket.type,
                            price = ticket.price,
                            description = ticket.description
                        )
                    }
                )
            }.toMutableSet()
        } catch (e: Exception) {
            logger.error("Failed to fetch events, Err: ${e.message}")
            throw com.cerberus.artishub.events.core.usecases.exceptions.EventsException(
                "Failed to fetch Events, Err: ${e.message}"
            )
        }
    }

    override fun getItem(identifier: com.cerberus.artishub.events.core.models.EventIdentifier): com.cerberus.artishub.events.core.models.Event? {
        val foundEvent = eventsRepository.findByIdentifier(identifier) ?: return null
        return com.cerberus.artishub.events.core.models.Event(
            identifier = foundEvent.identifier,
            organization = foundEvent.organization,
            type = foundEvent.type,
            date = foundEvent.date,
            startTime = foundEvent.startTime,
            endTime = foundEvent.endTime,
            title = foundEvent.title,
            description = foundEvent.description,
            location = foundEvent.location,
            bannerImageUrl = foundEvent.bannerImageUrl,
            imageUrl = foundEvent.imageUrl,
            tickets = foundEvent.tickets.map {
                com.cerberus.artishub.events.core.models.Ticket(
                    identifier = it.identifier,
                    type = it.type,
                    description = it.description,
                    price = it.price
                )
            }
        )
    }

    override fun create(
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
        tickets: Collection<com.cerberus.artishub.events.core.models.Ticket>
    ): com.cerberus.artishub.events.core.models.EventIdentifier {

        val identifier = generateIdentifier("$organization:$type:$title:$description:$location")

        val event = EventEntity(
            identifier = identifier,
            organization = organization,
            type = type,
            date = date,
            startTime = startTime,
            endTime = endTime,
            title = title,
            description = description,
            location = location,
            bannerImageUrl = bannerImageUrl,
            imageUrl = imageUrl,
            tickets = tickets.map {
                TicketEntity(
                    identifier = generateIdentifier("${it.type}:${it.description}"),
                    type = it.type,
                    description = it.description,
                    price = it.price
                )
            }.toMutableSet()
        )

        return try {
            eventsRepository.save(event)
            identifier
        } catch (e: Exception) {
            logger.error("Failed to create event ${e.message}")
            throw com.cerberus.artishub.events.core.usecases.exceptions.EventsException(
                "Failed to create event"
            )
        }
    }

    override fun update(
        identifier: com.cerberus.artishub.events.core.models.EventIdentifier,
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
        tickets: Collection<com.cerberus.artishub.events.core.models.Ticket>
    ): Boolean {
        val foundEvent = eventsRepository.findByIdentifier(identifier) ?: return false
        val updatedEvent = foundEvent.apply {
            this.organization = organization
            this.type = type
            this.date = date
            this.startTime = startTime
            this.endTime = endTime
            this.title = title
            this.description = description
            this.location = location
            this.bannerImageUrl = bannerImageUrl
            this.imageUrl = imageUrl
        }

        /** TODO get all found tickets */
        //  val foundTickets = ticketsRepository.findAllByEvent(foundEvent)

        return try {
            eventsRepository.save(updatedEvent)
            true
        } catch (e: Exception) {
            logger.error("Failed to update event $identifier")
            throw com.cerberus.artishub.events.core.usecases.exceptions.EventsException(
                "Failed to update event"
            )
        }
    }

    override fun delete(identifier: com.cerberus.artishub.events.core.models.EventIdentifier): Boolean {
        val event = eventsRepository.findByIdentifier(identifier) ?: return false

        return try {
            eventsRepository.delete(event)
            true
        } catch (e: Exception) {
            logger.error("Failed to delete event item ${e.message}")
            throw com.cerberus.artishub.events.core.usecases.exceptions.EventsException(
                "Failed to delete event $identifier"
            )
        }
    }
}
