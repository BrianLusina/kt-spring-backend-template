package com.cerberus.artishub.events.api

import com.cerberus.artishub.events.api.dto.EventRequestDto
import com.cerberus.artishub.events.api.dto.EventResponseDto
import com.cerberus.artishub.events.api.dto.TicketRequestDto
import com.cerberus.artishub.events.api.dto.TicketResponseDto
import com.cerberus.artishub.events.core.usecases.CreateEventUseCase
import com.cerberus.artishub.events.core.usecases.DeleteAnEventUseCase
import com.cerberus.artishub.events.core.usecases.GetAllEventsUseCase
import com.cerberus.artishub.events.core.usecases.GetAnEventItemUseCase
import com.cerberus.artishub.events.core.usecases.UpdateEventUseCase
import com.cerberus.artishub.events.core.models.Event
import com.cerberus.artishub.events.core.models.Ticket
import com.cerberus.artishub.events.core.models.TicketType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.LocalTime

class EventsServiceImplTest {

    private val mockGetAllEventsUseCase = mockk<GetAllEventsUseCase>()
    private val mockGetAnEventUseCase = mockk<GetAnEventItemUseCase>()
    private val mockCreateEventUseCase = mockk<CreateEventUseCase>()
    private val mockUpdateEventUseCase = mockk<UpdateEventUseCase>()
    private val mockDeleteAnEventUseCase = mockk<DeleteAnEventUseCase>()

    private lateinit var eventsService: EventsServiceImpl

    private val ticket = Ticket(
        identifier = "identifier",
        type = TicketType.FULL_PASS,
        price = "TZSH 2500",
        description = "Full meals"
    )

    private val ticketDto = TicketResponseDto(
        identifier = "identifier",
        type = TicketType.FULL_PASS,
        price = "TZSH 2500",
        description = "Full meals"
    )

    private val ticketRequestDto =
        TicketRequestDto(
            type = TicketType.FULL_PASS,
            price = "TZSH 2500",
            description = "Full meals"
        )

    private val identifier = "identifier"
    private val event = Event(
        identifier,
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
        tickets = mutableSetOf(ticket)
    )

    private val events = setOf(event)

    private val eventResponseDto =
        EventResponseDto(
            identifier = "identifier",
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
            tickets = mutableSetOf(ticketDto)
        )

    private val ticketRequests = setOf(ticketRequestDto)
    private val eventRequestDto =
        EventRequestDto(
            identifier,
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
    private val expectedIdentifier = "some-identifier"

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

    @BeforeEach
    fun setUp() {
        eventsService = EventsServiceImpl(
            mockGetAllEventsUseCase,
            mockGetAnEventUseCase,
            mockCreateEventUseCase,
            mockUpdateEventUseCase,
            mockDeleteAnEventUseCase
        )
    }

    @Test
    fun `Should return events DTO when executed use case returns events`() {
        every {
            mockGetAllEventsUseCase.execute()
        } returns events

        val actual = eventsService.getAll()

        Assertions.assertEquals(eventsDto, actual)
    }

    @Test
    fun `Should return an event DTO when executed use case returns an event found by given identifier`() {
        every {
            mockGetAnEventUseCase.execute(identifier)
        } returns event

        val actual = eventsService.getItem(identifier)

        Assertions.assertEquals(eventResponseDto, actual)
    }

    @Test
    fun `Should execute use case to create a new event & return identifier`() {
        every {
            mockCreateEventUseCase.execute(
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
                    tickets.map {
                        Ticket(
                            type = it.type, description = it.description, price = it.price
                        )
                    }
                )
            )
        } returns expectedIdentifier

        val actual = eventsService.create(eventRequestDto)

        Assertions.assertEquals(expectedIdentifier, actual)
    }

    @Test
    fun `Should return a boolean value when executing use case to update an event`() {
        every {
            mockUpdateEventUseCase.execute(
                UpdateEventUseCase.Params(
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
                    tickets.map {
                        Ticket(
                            type = it.type, description = it.description, price = it.price
                        )
                    }
                )
            )
        } returns true

        val actual = eventsService.update(eventRequestDto)

        Assertions.assertTrue(actual)
    }

    @Test
    fun `Should return a boolean value when executing use case to delete an event`() {
        every {
            mockDeleteAnEventUseCase.execute(identifier)
        } returns true

        val actual = eventsService.delete(identifier)

        Assertions.assertTrue(actual)
    }
}
