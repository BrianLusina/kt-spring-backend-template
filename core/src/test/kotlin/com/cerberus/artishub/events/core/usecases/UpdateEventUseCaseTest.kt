package com.cerberus.artishub.events.core.usecases

import com.cerberus.artishub.events.core.gateways.EventsDataStore
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.LocalTime

class UpdateEventUseCaseTest {
    private val mockEventsDataStore = mockk<EventsDataStore>()
    private val updateEventItemUseCase by lazy {
        UpdateEventUseCase(
            mockEventsDataStore
        )
    }

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

    private val ticket = com.cerberus.artishub.events.core.models.Ticket(
        identifier = ticketIdentifier,
        type = ticketType,
        price = ticketPrice,
        description = ticketDescription
    )

    private val tickets = mutableSetOf(ticket)

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

    @Test
    fun `Should throw illegal argument exception when null params are passed`() {
        val expectedErr = "Must pass in valid params to update an event"
        val actual = Assertions.assertThrows(IllegalArgumentException::class.java) {
            updateEventItemUseCase.execute()
        }
        assertEquals(expectedErr, actual.message)
    }

    @Test
    fun `Should return false when there is an exception updating an event`() {
        every {
            mockEventsDataStore.update(
                eventIdentifier,
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
        } throws com.cerberus.artishub.events.core.usecases.exceptions.EventsException(
            "DB Exception"
        )

        val actual = updateEventItemUseCase.execute(
            UpdateEventUseCase.Params(
                eventIdentifier,
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
        )

        assertFalse(actual)
    }

    @Test
    fun `Should return true when succesul update of an event occurs`() {
        every {
            mockEventsDataStore.update(
                eventIdentifier,
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
        } returns true

        val actual = updateEventItemUseCase.execute(
            UpdateEventUseCase.Params(
                eventIdentifier,
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
        )

        assertTrue(actual)
    }
}
