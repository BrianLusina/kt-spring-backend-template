package com.cerberus.artishub.events.core.usecases

import com.cerberus.artishub.events.core.gateways.EventsDataStore
import com.cerberus.artishub.events.core.interactor.UseCase
import com.cerberus.artishub.events.core.models.EventIdentifier
import com.cerberus.artishub.events.core.models.Ticket
import com.cerberus.artishub.events.core.usecases.exceptions.EventsException
import java.time.LocalDateTime
import java.time.LocalTime

class CreateEventUseCase(private val eventsDataStore: EventsDataStore) :
    UseCase<CreateEventUseCase.Params, EventIdentifier?>() {

    override fun execute(params: Params?): EventIdentifier? {
        requireNotNull(params) { "Must pass in an event item" }
        return try {
            eventsDataStore.create(
                description = params.description,
                organization = params.organization,
                type = params.type,
                date = params.date,
                startTime = params.startTime,
                endTime = params.endTime,
                title = params.title,
                location = params.location,
                bannerImageUrl = params.bannerImageUrl,
                imageUrl = params.imageUrl,
                tickets = params.tickets
            )
        } catch (e: EventsException) {
            null
        }
    }

    data class Params(
        val organization: String,
        val type: String,
        val date: LocalDateTime,
        val startTime: LocalTime,
        val endTime: LocalTime,
        val title: String,
        val description: String,
        val location: String,
        val bannerImageUrl: String,
        val imageUrl: String,
        val tickets: Collection<Ticket>
    )
}
