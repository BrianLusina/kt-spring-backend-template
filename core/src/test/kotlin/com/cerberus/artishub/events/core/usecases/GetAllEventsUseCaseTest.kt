package com.cerberus.artishub.events.core.usecases

import com.cerberus.artishub.events.core.gateways.EventsDataStore
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.LocalTime

class GetAllEventsUseCaseTest {

    private val mockEventsDataStore = mockk<EventsDataStore>()
    private lateinit var getAllEventsUseCase: GetAllEventsUseCase

    @BeforeEach
    fun setUp() {
        getAllEventsUseCase =
            GetAllEventsUseCase(
                mockEventsDataStore
            )
    }

    @Test
    fun `Should return an empty list when EventNotFoundException is thrown by data store`() {
        every {
            mockEventsDataStore.getAll()
        } throws com.cerberus.artishub.events.core.usecases.exceptions.EventsException(
            "DB error"
        )

        val actual = getAllEventsUseCase.execute()

        Assertions.assertEquals(0, actual.size)
    }

    @Test
    fun `Should return all events when data store retrieves all events`() {
        val ticket = com.cerberus.artishub.events.core.models.Ticket(
            identifier = "identifier",
            type = com.cerberus.artishub.events.core.models.TicketType.FULL_PASS,
            price = "TZSH 2500",
            description = "Full meals"
        )
        val event = com.cerberus.artishub.events.core.models.Event(
            identifier = "identifier",
            organization = "TMEA",
            type = "Workshop",
            date = LocalDateTime.now(),
            startTime = LocalTime.now(),
            endTime = LocalTime.now().plusHours(2),
            title = "Largest Biz Conference in East Africa",
            description = "Long description",
            location = "Kigali, Rwanda",
            bannerImageUrl = "https://image.png",
            imageUrl = "https://image.png",
            tickets = mutableSetOf(ticket)
        )

        every {
            mockEventsDataStore.getAll()
        } returns mutableSetOf(event)

        val actualResponse = getAllEventsUseCase.execute()

        Assertions.assertEquals(mutableSetOf(event), actualResponse)
    }
}
