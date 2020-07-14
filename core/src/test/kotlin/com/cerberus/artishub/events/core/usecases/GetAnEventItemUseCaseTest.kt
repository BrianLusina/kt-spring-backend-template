package com.cerberus.artishub.events.core.usecases

import com.cerberus.artishub.events.core.gateways.EventsDataStore
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException
import java.time.LocalDateTime
import java.time.LocalTime

class GetAnEventItemUseCaseTest {

    private val mockEventDataStore = mockk<EventsDataStore>()
    private val getAnEventItemUseCase by lazy {
        GetAnEventItemUseCase(
            mockEventDataStore
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
    fun `Should throw an illegal argument exception when a null identifier is passed`() {
        val actual = Assertions.assertThrows(IllegalArgumentException::class.java) {
            getAnEventItemUseCase.execute()
        }

        assertEquals("Must have an identifier to get an event item", actual.message)
    }

    @Test
    fun `Should return null when an EventsException is thrown`() {
        every {
            mockEventDataStore.getItem("identifier")
        } throws com.cerberus.artishub.events.core.usecases.exceptions.EventsException(
            "Failed to get an event"
        )

        val actual = getAnEventItemUseCase.execute("identifier")
        Assertions.assertNull(actual)
    }

    @Test
    fun `Should return an event an event is found for given identifier`() {
        every {
            mockEventDataStore.getItem("identifier")
        } returns event

        val actual = getAnEventItemUseCase.execute("identifier")

        assertEquals(event, actual)
    }
}
