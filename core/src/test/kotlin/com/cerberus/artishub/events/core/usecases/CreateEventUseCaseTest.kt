package com.cerberus.artishub.events.core.usecases

import com.cerberus.artishub.events.core.gateways.EventsDataStore
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException
import java.time.LocalDateTime
import java.time.LocalTime

class CreateEventUseCaseTest {
    private val mockEventsDataStore = mockk<EventsDataStore>()
    private val createEventUseCase by lazy {
        CreateEventUseCase(
            mockEventsDataStore
        )
    }

    @Test
    fun `Should throw illegal argument exception when executed with null params`() {
        val expectedErrorMsg = "Must pass in an event item"
        val actual = Assertions.assertThrows(IllegalArgumentException::class.java) {
            createEventUseCase.execute()
        }

        Assertions.assertEquals(expectedErrorMsg, actual.message)
    }

    @Test
    fun `Should return null when Events Exception is thrown by data store`() {
        val tickets = listOf(
            com.cerberus.artishub.events.core.models.Ticket(
                type = com.cerberus.artishub.events.core.models.TicketType.FULL_PASS,
                description = "Full meals",
                price = "200.00"
            )
        )
        val description = "Description"
        val organization = "TMEA"
        val type = "Workshop"
        val date = LocalDateTime.of(2020, 1, 1, 1, 1)
        val startTime = LocalTime.of(2, 1, 1)
        val endTime = LocalTime.of(4, 1, 1)
        val title = "Biggest Event Ever!"
        val location = "Kigali, Rwanda"
        val bannerImageUrl = "bannerimage url"
        val imageUrl = "image url"

        every {
            mockEventsDataStore.create(
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
            "Failed to create event"
        )

        val actual = createEventUseCase.execute(
            CreateEventUseCase.Params(
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

        Assertions.assertNull(actual)
    }

    @Test
    fun `Should return event identifier when an event is successfully created by data store`() {
        val tickets = listOf(
            com.cerberus.artishub.events.core.models.Ticket(
                type = com.cerberus.artishub.events.core.models.TicketType.FULL_PASS,
                description = "Full meals",
                price = "200.00"
            )
        )
        val description = "Description"
        val organization = "TMEA"
        val type = "Workshop"
        val date = LocalDateTime.of(2020, 1, 1, 1, 1)
        val startTime = LocalTime.of(2, 1, 1)
        val endTime = LocalTime.of(4, 1, 1)
        val title = "Biggest Event Ever!"
        val location = "Kigali, Rwanda"
        val bannerImageUrl = "bannerimage url"
        val imageUrl = "image url"

        every {
            mockEventsDataStore.create(
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
        } returns "identifier"

        val actual = createEventUseCase.execute(
            CreateEventUseCase.Params(
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

        Assertions.assertEquals("identifier", actual)
    }
}
