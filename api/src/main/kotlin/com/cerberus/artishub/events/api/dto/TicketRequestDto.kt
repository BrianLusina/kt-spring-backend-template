package com.cerberus.artishub.events.api.dto

import com.cerberus.artishub.events.core.models.TicketType
import com.fasterxml.jackson.annotation.JsonProperty

data class TicketResponseDto(
    @JsonProperty("identifier", required = true)
    val identifier: String,

    @JsonProperty("type", required = true)
    val type: TicketType,

    @JsonProperty("description", required = true)
    val description: String,

    @JsonProperty("price", required = true)
    val price: String
)

data class TicketRequestDto(
    @JsonProperty("type", required = true)
    val type: TicketType,

    @JsonProperty("description", required = true)
    val description: String,

    @JsonProperty("price", required = true)
    val price: String
)
