package com.cerberus.artishub.events.database

import com.cerberus.artishub.events.core.utils.generateIdentifier
import com.cerberus.artishub.events.database.events.EventEntity
import com.cerberus.artishub.events.database.events.EventsRepository
import com.cerberus.artishub.events.database.tickets.TicketEntity
import com.cerberus.artishub.events.database.tickets.TicketsRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.LocalTime

class EventsDataStoreImplTest {

    private val mockEventsRepository = mockk<EventsRepository>()
    private val mockTicketsRepository = mockk<TicketsRepository>()
    private lateinit var eventsDataStore: EventsDataStoreImpl

    private val eventIdentifier = "event-identifier"
    private val organization = "TMEA"
    private val type = "Workshop"
    private val date = LocalDateTime.of(2020, 1, 1, 1, 1)
    private val startTime = LocalTime.of(2, 1, 1, 1)
    private val endTime = LocalTime.of(2, 0).plusHours(2)
    private val title = "Largest Biz Conference in East Africa"
    private val description = "Long description"
    private val location = "Kigali, Rwanda"
    private val bannerImageUrl = "https://image.png"
    private val imageUrl = "https://image.png"

    private val ticketIdentifier = "ticko"
    private val ticketType = com.cerberus.artishub.events.core.models.TicketType.FULL_PASS
    private val ticketPrice = "TZSH 2500"
    private val ticketDescription = "Full meals"

    private val ticketEntity = TicketEntity(
        identifier = ticketIdentifier,
        type = ticketType,
        price = ticketPrice,
        description = ticketDescription
    )

    private val ticket = com.cerberus.artishub.events.core.models.Ticket(
        identifier = ticketIdentifier,
        type = ticketType,
        price = ticketPrice,
        description = ticketDescription
    )
    private val tickets = mutableSetOf(ticket)

    private val ticketEntities = mutableSetOf(ticketEntity)

    private val eventEntity = EventEntity(
        identifier = eventIdentifier,
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
        tickets = ticketEntities
    )

    private val event = com.cerberus.artishub.events.core.models.Event(
        identifier = eventIdentifier,
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
        tickets = tickets
    )

    @BeforeEach
    fun setUp() {
        eventsDataStore =
            EventsDataStoreImpl(
                mockEventsRepository,
                mockTicketsRepository
            )
    }

    @Test
    @Disabled
    fun `Should get all events from database`() {
        every {
            mockEventsRepository.findAll()
        } returns listOf(eventEntity)

        val actual = eventsDataStore.getAll()
        Assertions.assertEquals(setOf(event), actual)
    }

    @Test
    fun `Should throw Exception when findAll throws an error`() {
        val exceptionMessage = "DB exception"

        every {
            mockEventsRepository.findAll()
        } throws Exception(exceptionMessage)

        val actual =
            Assertions.assertThrows(com.cerberus.artishub.events.core.usecases.exceptions.EventsException::class.java) {
                eventsDataStore.getAll()
            }
        Assertions.assertEquals("Failed to fetch Events, Err: $exceptionMessage", actual.message)
    }

    @Test
    fun `Should return false when event cant be found for identifier`() {
        val identifier = "identifier"
        every {
            mockEventsRepository.findByIdentifier(identifier)
        } returns null

        val actual = eventsDataStore.delete(identifier)

        Assertions.assertFalse(actual)
    }

    @Test
    fun `Should throw Exception when there is a failure to delete an event for a given identifier`() {
        val identifier = "identifier"
        val expectedExceptionMsg = "Failed to delete event $identifier"
        every {
            mockEventsRepository.findByIdentifier(identifier)
        } returns eventEntity

        every {
            mockEventsRepository.delete(eventEntity)
        } throws Exception("Some DB exception")

        val actual =
            Assertions.assertThrows(com.cerberus.artishub.events.core.usecases.exceptions.EventsException::class.java) {
                eventsDataStore.delete(identifier)
            }

        Assertions.assertEquals(expectedExceptionMsg, actual.message)
    }

    @Test
    fun `Should return true when successfully deleting an event for a given identifier`() {
        val identifier = "identifier"
        every {
            mockEventsRepository.findByIdentifier(identifier)
        } returns eventEntity

        every {
            mockEventsRepository.delete(eventEntity)
        } returns Unit

        val actual = eventsDataStore.delete(identifier)

        Assertions.assertTrue(actual)
    }

    @Test
    fun `Should return false when updating an event that cant be found for given identifier`() {
        val identifier = "identifier"
        every {
            mockEventsRepository.findByIdentifier(identifier)
        } returns null

        val actual = eventsDataStore.update(
            identifier,
            organization,
            type,
            date,
            startTime,
            endTime,
            title,
            description,
            location,
            bannerImageUrl,
            imageUrl,
            tickets
        )

        Assertions.assertFalse(actual)
    }

    @Test
    fun `Should throw Exception when there is a failure to updat an event for a given identifier`() {
        val identifier = "identifier"
        val expectedExceptionMsg = "Failed to update event"
        every {
            mockEventsRepository.findByIdentifier(identifier)
        } returns eventEntity

        every {
            mockEventsRepository.save(eventEntity)
        } throws Exception("Some DB exception")

        val actual =
            Assertions.assertThrows(com.cerberus.artishub.events.core.usecases.exceptions.EventsException::class.java) {
                eventsDataStore.update(
                    identifier,
                    organization,
                    type,
                    date,
                    startTime,
                    endTime,
                    title,
                    description,
                    location,
                    bannerImageUrl,
                    imageUrl,
                    tickets
                )
            }

        Assertions.assertEquals(expectedExceptionMsg, actual.message)
    }

    @Test
    fun `Should return true when successfully updating an event for a given identifier`() {
        val identifier = "identifier"
        every {
            mockEventsRepository.findByIdentifier(identifier)
        } returns eventEntity

        every {
            mockEventsRepository.save(eventEntity)
        } returns eventEntity

        val actual = eventsDataStore.update(
            identifier,
            organization,
            type,
            date,
            startTime,
            endTime,
            title,
            description,
            location,
            bannerImageUrl,
            imageUrl,
            tickets
        )

        Assertions.assertTrue(actual)
    }

    @Test
    fun `Should throw exception when failing to create an event`() {
        every {
            mockEventsRepository.save(eventEntity)
        } throws com.cerberus.artishub.events.core.usecases.exceptions.EventsException(
            "DB Exception"
        )

        val actual =
            Assertions.assertThrows(com.cerberus.artishub.events.core.usecases.exceptions.EventsException::class.java) {
                eventsDataStore.create(
                    organization,
                    type,
                    date,
                    startTime,
                    endTime,
                    title,
                    description,
                    location,
                    bannerImageUrl,
                    imageUrl,
                    tickets
                )
            }

        Assertions.assertEquals("Failed to create event", actual.message)
    }

    @Test
    @Disabled
    fun `Should return an identifier when an event is successfully created`() {
        mockkStatic("com.tmea.wit.core.utils.UtilsKt")
        every {
            generateIdentifier("$organization:$type:$title:$description:$location")
        } returns eventIdentifier

        every {
            mockEventsRepository.save(eventEntity)
        } returns eventEntity

        val actual = eventsDataStore.create(
            organization, type, date, startTime, endTime, title,
            description, location, bannerImageUrl, imageUrl, tickets
        )

        Assertions.assertEquals(eventIdentifier, actual)
    }

    @Test
    fun `Should return null if an event can't be found by the given identifier`() {
        every {
            mockEventsRepository.findByIdentifier(eventIdentifier)
        } returns null

        val actual = eventsDataStore.getItem(eventIdentifier)

        Assertions.assertNull(actual)
    }

    @Test
    fun `Should return event for the given identifier`() {
        every {
            mockEventsRepository.findByIdentifier(eventIdentifier)
        } returns eventEntity

        val actual = eventsDataStore.getItem(eventIdentifier)

        Assertions.assertEquals(event.identifier, actual?.identifier)
    }
}
