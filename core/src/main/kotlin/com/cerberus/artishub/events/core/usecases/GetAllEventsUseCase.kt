package com.cerberus.artishub.events.core.usecases

import com.cerberus.artishub.events.core.gateways.EventsDataStore
import com.cerberus.artishub.events.core.interactor.UseCase
import com.cerberus.artishub.events.core.models.Event
import com.cerberus.artishub.events.core.usecases.exceptions.EventsException

class GetAllEventsUseCase(private val eventsDataStore: EventsDataStore) :
    UseCase<Unit, Set<Event>>() {

    override fun execute(params: Unit?): Set<Event> {
        return try {
            eventsDataStore.getAll()
        } catch (e: EventsException) {
            emptySet()
        }
    }
}
