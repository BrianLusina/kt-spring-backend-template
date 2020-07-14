package com.cerberus.artishub.events.config.api

import com.cerberus.artishub.events.api.EventsService
import com.cerberus.artishub.events.api.EventsServiceImpl
import com.cerberus.artishub.events.core.usecases.CreateEventUseCase
import com.cerberus.artishub.events.core.usecases.DeleteAnEventUseCase
import com.cerberus.artishub.events.core.usecases.GetAllEventsUseCase
import com.cerberus.artishub.events.core.usecases.GetAnEventItemUseCase
import com.cerberus.artishub.events.core.usecases.UpdateEventUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EventsApiConfig {

    @Bean
    fun createEventsService(
        getAllEventsUseCase: GetAllEventsUseCase,
        getAnEventItemUseCase: GetAnEventItemUseCase,
        createEventUseCase: CreateEventUseCase,
        updateEventUseCase: UpdateEventUseCase,
        deleteAnEventUseCase: DeleteAnEventUseCase
    ): EventsService {
        return EventsServiceImpl(
            getAllEventsUseCase,
            getAnEventItemUseCase,
            createEventUseCase,
            updateEventUseCase,
            deleteAnEventUseCase
        )
    }
}
