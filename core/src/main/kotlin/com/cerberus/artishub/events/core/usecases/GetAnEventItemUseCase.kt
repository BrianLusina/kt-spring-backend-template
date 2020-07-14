package com.cerberus.artishub.events.core.usecases

import com.cerberus.artishub.events.core.gateways.EventsDataStore
import com.cerberus.artishub.events.core.interactor.UseCase
import com.cerberus.artishub.events.core.models.Event
import com.cerberus.artishub.events.core.models.EventIdentifier

class GetAnEventItemUseCase(private val eventsDataStore: EventsDataStore) :
    UseCase<EventIdentifier, Event?>() {
    override fun execute(params: EventIdentifier?): Event? {
        requireNotNull(params) { "Must have an identifier to get an event item" }
        return try {
            eventsDataStore.getItem(params)
        } catch (e: com.cerberus.artishub.events.core.usecases.exceptions.EventsException) {
            null
        }
    }
}
