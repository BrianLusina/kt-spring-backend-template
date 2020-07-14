package com.cerberus.artishub.events.config.core

import com.cerberus.artishub.events.core.gateways.EventsDataStore
import com.cerberus.artishub.events.core.usecases.CreateEventUseCase
import com.cerberus.artishub.events.core.usecases.DeleteAnEventUseCase
import com.cerberus.artishub.events.core.usecases.GetAllEventsUseCase
import com.cerberus.artishub.events.core.usecases.GetAnEventItemUseCase
import com.cerberus.artishub.events.core.usecases.UpdateEventUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EventsUseCasesConfig {

    @Bean
    fun createGetAllEventsUseCase(
        eventsDataStore: EventsDataStore
    ): GetAllEventsUseCase {
        return GetAllEventsUseCase(
            eventsDataStore
        )
    }

    @Bean
    fun createGetAnEventItemUseCase(
        eventsDataStore: EventsDataStore
    ): GetAnEventItemUseCase {
        return GetAnEventItemUseCase(
            eventsDataStore
        )
    }

    @Bean
    fun createCreateAnEventUseCase(
        eventsDataStore: EventsDataStore
    ): CreateEventUseCase {
        return CreateEventUseCase(
            eventsDataStore
        )
    }

    @Bean
    fun createUpdateAnEventUseCase(
        eventsDataStore: EventsDataStore
    ): UpdateEventUseCase {
        return UpdateEventUseCase(
            eventsDataStore
        )
    }

    @Bean
    fun createDeleteAnEventUseCase(
        eventsDataStore: EventsDataStore
    ): DeleteAnEventUseCase {
        return DeleteAnEventUseCase(
            eventsDataStore
        )
    }
}
