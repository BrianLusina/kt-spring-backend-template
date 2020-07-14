package com.cerberus.artishub.events.api.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import java.time.LocalTime

data class EventResponseDto(
    @JsonProperty("identifier", required = true)
    val identifier: String,

    @JsonProperty("organization", required = true)
    val organization: String,

    @JsonProperty("event_type", required = true)
    val type: String,

    @JsonProperty("date", required = true)
    val date: LocalDateTime,

    @JsonProperty("start_time", required = true)
    val startTime: LocalTime,

    @JsonProperty("end_time", required = true)
    val endTime: LocalTime,

    @JsonProperty("title", required = true)
    val title: String,

    @JsonProperty("description", required = true)
    val description: String,

    @JsonProperty("location", required = true)
    val location: String,

    @JsonProperty("banner_image_url", required = true)
    val bannerImageUrl: String,

    @JsonProperty("image_url", required = true)
    val imageUrl: String,

    @JsonProperty("tickets", required = true)
    val tickets: Set<TicketResponseDto>
)

data class EventRequestDto(
    @JsonProperty("identifier", required = false)
    val identifier: String? = null,

    @JsonProperty("organization", required = true)
    val organization: String,

    @JsonProperty("event_type", required = true)
    val type: String,

    @JsonProperty("date", required = true)
    val date: LocalDateTime,

    @JsonProperty("start_time", required = true)
    val startTime: LocalTime,

    @JsonProperty("end_time", required = true)
    val endTime: LocalTime,

    @JsonProperty("title", required = true)
    val title: String,

    @JsonProperty("description", required = true)
    val description: String,

    @JsonProperty("location", required = true)
    val location: String,

    @JsonProperty("banner_image_url", required = true)
    val bannerImageUrl: String,

    @JsonProperty("image_url", required = true)
    val imageUrl: String,

    @JsonProperty("tickets", required = true)
    val tickets: Set<TicketRequestDto>
)
