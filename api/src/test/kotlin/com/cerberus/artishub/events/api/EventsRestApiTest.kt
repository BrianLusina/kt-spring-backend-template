package com.cerberus.artishub.events.api

import com.cerberus.artishub.events.api.dto.EventRequestDto
import com.cerberus.artishub.events.api.dto.EventResponseDto
import com.cerberus.artishub.events.api.dto.TicketRequestDto
import com.cerberus.artishub.events.api.dto.TicketResponseDto
import com.cerberus.artishub.events.core.models.TicketType
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Collections

class EventsRestApiTest {

    private val mockEventsService = mockk<EventsService>()
    private var eventsRestApi: EventsRestApi =
        EventsRestApi(mockEventsService)

    private val eventIdentifier = "identifier"

    private val ticketDto = TicketResponseDto(
        identifier = "identifier",
        type = TicketType.FULL_PASS,
        price = "TZSH 2500",
        description = "Full meals"
    )

    private val eventResponseDto =
        EventResponseDto(
            identifier = eventIdentifier,
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
            tickets = mutableSetOf(ticketDto)
        )

    private val ticketRequestDto =
        TicketRequestDto(
            type = TicketType.FULL_PASS,
            price = "TZSH 2500",
            description = "Full meals"
        )
    private val eventRequestDto =
        EventRequestDto(
            organization = "TMEA",
            type = "Workshop",
            date = LocalDateTime.of(2020, 1, 1, 1, 1),
            startTime = LocalTime.of(2, 1),
            endTime = LocalTime.of(2, 1).plusHours(2),
            title = "Largest Biz Conference in East Africa",
            description = "Long description",
            location = "Kigali, Rwanda",
            bannerImageUrl = "https://image.png",
            imageUrl = "https://image.png",
            tickets = mutableSetOf(ticketRequestDto)
        )

    private val eventsDto = arrayListOf(eventResponseDto)

    private val organization = "TMEA"
    private val type = "Workshop"
    private val date = LocalDateTime.of(2020, 1, 1, 1, 1)
    private val startTime = LocalTime.of(2, 1)
    private val endTime = LocalTime.of(2, 1).plusHours(2)
    private val title = "Largest Biz Conference in East Africa"
    private val description = "Long description"
    private val location = "Kigali, Rwanda"
    private val bannerImageUrl = "https://image.png"
    private val imageUrl = "https://image.png"
    private val tickets = setOf(ticketDto)
    private val ticketRequests = setOf(ticketRequestDto)

    @Test
    fun `Should return a list of event dto when events service returns events`() {
        every {
            mockEventsService.getAll()
        } returns eventsDto

        val expectedResponse = ResponseEntity(eventsDto, HttpStatus.OK)

        val actual = eventsRestApi.getAll()
        Assertions.assertEquals(expectedResponse, actual)
    }

    @Test
    fun `Should return an event DTO when event service returns an event item`() {
        every {
            mockEventsService.getItem(eventIdentifier)
        } returns eventResponseDto

        val expectedResponse = ResponseEntity(eventResponseDto, HttpStatus.OK)

        val actual = eventsRestApi.getItem(eventIdentifier)
        Assertions.assertEquals(expectedResponse, actual)
    }

    @Test
    fun `Should return identifier when event has been created successfully by event service `() {
        every {
            mockEventsService.create(eventRequestDto)
        } returns eventIdentifier

        val expectedResponse =
            ResponseEntity(Collections.singletonMap("identifier", eventIdentifier), HttpStatus.CREATED)

        val actual = eventsRestApi.create(eventRequestDto)
        Assertions.assertEquals(expectedResponse, actual)
    }

    @Test
    fun `Should return error when failed to create an event`() {
        every {
            mockEventsService.create(eventRequestDto)
        } returns null

        val expectedResponse =
            ResponseEntity(
                Collections.singletonMap("Error", "failed to create event"),
                HttpStatus.NOT_ACCEPTABLE
            )

        val actual = eventsRestApi.create(eventRequestDto)
        Assertions.assertEquals(expectedResponse, actual)
    }
}
