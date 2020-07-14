package com.cerberus.artishub.events.config.core

import com.cerberus.artishub.events.core.gateways.EventsDataStore
import com.cerberus.artishub.events.database.EventsDataStoreImpl
import com.cerberus.artishub.events.database.events.EventsRepository
import com.cerberus.artishub.events.database.tickets.TicketsRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EventDataStoreConfig {

    @Bean
    fun createEventDataStore(
        eventsRepository: EventsRepository,
        ticketsRepository: TicketsRepository
    ): EventsDataStore {
        return EventsDataStoreImpl(
            eventsRepository,
            ticketsRepository
        )
    }
}
