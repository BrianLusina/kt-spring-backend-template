package com.cerberus.artishub.events.core.usecases

import com.cerberus.artishub.events.core.gateways.EventsDataStore
import com.cerberus.artishub.events.core.interactor.UseCase
import com.cerberus.artishub.events.core.models.EventIdentifier
import com.cerberus.artishub.events.core.usecases.exceptions.EventsException

class DeleteAnEventUseCase(private val eventsDataStore: EventsDataStore) :
    UseCase<EventIdentifier, Boolean>() {

    override fun execute(params: EventIdentifier?): Boolean {
        requireNotNull(params) { "Must pass in valid identifier" }
        return try {
            eventsDataStore.delete(params)
        } catch (e: EventsException) {
            false
        }
    }
}
