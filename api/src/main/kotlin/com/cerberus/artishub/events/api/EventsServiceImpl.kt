package com.cerberus.artishub.events.api

import com.cerberus.artishub.events.api.dto.EventRequestDto
import com.cerberus.artishub.events.api.dto.EventResponseDto
import com.cerberus.artishub.events.api.dto.TicketResponseDto
import com.cerberus.artishub.events.core.usecases.CreateEventUseCase
import com.cerberus.artishub.events.core.usecases.DeleteAnEventUseCase
import com.cerberus.artishub.events.core.usecases.GetAllEventsUseCase
import com.cerberus.artishub.events.core.usecases.GetAnEventItemUseCase
import com.cerberus.artishub.events.core.usecases.UpdateEventUseCase

class EventsServiceImpl(
    private val getAllEventsUseCase: GetAllEventsUseCase,
    private val getAnEventItemUseCase: GetAnEventItemUseCase,
    private val createEventUseCase: CreateEventUseCase,
    private val updateEventUseCase: UpdateEventUseCase,
    private val deleteAnEventUseCase: DeleteAnEventUseCase
) : EventsService {

    /**
     * Gets all events
     * @return [Collection] Collection of events
     */
    override fun getAll(): Collection<EventResponseDto> {
        val events = getAllEventsUseCase.execute()
        return events.map {
            EventResponseDto(
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
                    TicketResponseDto(
                        identifier = ticket.identifier!!,
                        type = ticket.type,
                        price = ticket.price,
                        description = ticket.description
                    )
                }.toSet()
            )
        }
    }

    override fun getItem(identifier: com.cerberus.artishub.events.core.models.EventIdentifier): EventResponseDto? {
        val item = getAnEventItemUseCase.execute(identifier) ?: return null
        return EventResponseDto(
            identifier,
            organization = item.organization,
            type = item.type,
            date = item.date,
            startTime = item.startTime,
            endTime = item.endTime,
            title = item.title,
            description = item.description,
            location = item.location,
            bannerImageUrl = item.bannerImageUrl,
            imageUrl = item.imageUrl,
            tickets = item.tickets.map {
                TicketResponseDto(
                    identifier = it.identifier!!,
                    type = it.type,
                    description = it.description,
                    price = it.price
                )
            }.toSet()
        )
    }

    override fun create(
        eventRequestDto: EventRequestDto
    ): com.cerberus.artishub.events.core.models.EventIdentifier? {
        return createEventUseCase.execute(
            CreateEventUseCase.Params(
                eventRequestDto.organization,
                eventRequestDto.type,
                eventRequestDto.date,
                eventRequestDto.startTime,
                eventRequestDto.endTime,
                eventRequestDto.title,
                eventRequestDto.description,
                eventRequestDto.location,
                eventRequestDto.bannerImageUrl,
                eventRequestDto.imageUrl,
                eventRequestDto.tickets.map {
                    com.cerberus.artishub.events.core.models.Ticket(
                        type = it.type, description = it.description, price = it.price
                    )
                }
            )
        ) ?: return null
    }

    override fun update(
        eventRequestDto: EventRequestDto
    ): Boolean {
        return updateEventUseCase.execute(
            UpdateEventUseCase.Params(
                eventRequestDto.identifier!!,
                eventRequestDto.organization,
                eventRequestDto.type,
                eventRequestDto.date,
                eventRequestDto.startTime,
                eventRequestDto.endTime,
                eventRequestDto.title,
                eventRequestDto.description,
                eventRequestDto.location,
                eventRequestDto.bannerImageUrl,
                eventRequestDto.imageUrl,
                eventRequestDto.tickets.map {
                    com.cerberus.artishub.events.core.models.Ticket(
                        type = it.type, description = it.description, price = it.price
                    )
                }
            )
        )
    }

    override fun delete(identifier: com.cerberus.artishub.events.core.models.EventIdentifier): Boolean {
        return deleteAnEventUseCase.execute(identifier)
    }
}
