package com.cerberus.artishub.events.api

import com.cerberus.artishub.events.api.dto.EventRequestDto
import com.cerberus.artishub.events.api.dto.EventResponseDto
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.Collections

@RestController
@RequestMapping(path = ["/api/services/events"])
class EventsRestApi(
    private val eventsService: EventsService
) {

    @PostMapping(value = ["/"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun create(
        @Validated
        @RequestBody
        payload: EventRequestDto
    ): ResponseEntity<Map<String, String>> {
        val result = eventsService.create(payload) ?: return ResponseEntity(
            Collections.singletonMap("Error", "failed to create event"),
            HttpStatus.NOT_ACCEPTABLE
        )
        return ResponseEntity(
            Collections.singletonMap("identifier", result),
            HttpStatus.CREATED
        )
    }

    @GetMapping(value = ["/"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAll(): ResponseEntity<Collection<EventResponseDto>> {
        val allEvents = eventsService.getAll()
        return ResponseEntity(allEvents, HttpStatus.OK)
    }

    @GetMapping(value = ["/{identifier}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getItem(@PathVariable identifier: String): ResponseEntity<EventResponseDto> {
        val item = eventsService.getItem(identifier) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        return ResponseEntity(item, HttpStatus.OK)
    }

    @PatchMapping(value = ["/"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun update(eventRequestDto: EventRequestDto): ResponseEntity<Map<String, Boolean>> {
        val result = eventsService.update(eventRequestDto)
        return ResponseEntity(
            Collections.singletonMap("success", result),
            if (result) HttpStatus.ACCEPTED else HttpStatus.NOT_ACCEPTABLE
        )
    }

    @DeleteMapping(value = ["/{identifier}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun delete(@PathVariable identifier: String): ResponseEntity<Map<String, Boolean>> {
        val result = eventsService.delete(identifier)
        return ResponseEntity(
            Collections.singletonMap("success", result),
            if (result) HttpStatus.ACCEPTED else HttpStatus.NOT_ACCEPTABLE
        )
    }
}
