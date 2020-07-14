package com.cerberus.artishub.events.core.models

data class Ticket(
    val identifier: String? = null,
    val type: TicketType,
    val description: String,
    val price: String
)
