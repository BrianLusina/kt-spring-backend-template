package com.cerberus.artishub.events.api

import com.cerberus.artishub.events.api.dto.EventRequestDto
import com.cerberus.artishub.events.api.dto.EventResponseDto
import com.cerberus.artishub.events.core.models.EventIdentifier

interface EventsService {

    fun getAll(): Collection<EventResponseDto>

    fun getItem(identifier: EventIdentifier): EventResponseDto?

    fun create(
        eventRequestDto: EventRequestDto
    ): EventIdentifier?

    fun update(eventRequestDto: EventRequestDto): Boolean

    fun delete(identifier: EventIdentifier): Boolean
}
