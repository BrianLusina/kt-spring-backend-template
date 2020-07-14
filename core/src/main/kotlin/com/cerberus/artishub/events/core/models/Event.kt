package com.cerberus.artishub.events.core.models

import java.time.LocalDateTime
import java.time.LocalTime

/**
 * Event
 */
data class Event(
    /**
     * Unique identifier of the Event
     */
    val identifier: String,

    /**
     * The organizer of the event
     */
    val organization: String,

    /**
     * Type of event, Workshop, Training, etc
     */
    val type: String,

    /**
     * Date of event
     */
    val date: LocalDateTime,

    /**
     * Time event starts
     */
    val startTime: LocalTime,

    /**
     * Time event ends
     */
    val endTime: LocalTime,

    /**
     * Title of event
     */
    val title: String,

    /**
     * Description of event
     */
    val description: String,

    /**
     * Location of event
     */
    val location: String,

    /**
     * Banner Image URL
     */
    val bannerImageUrl: String,

    /**
     * Image URL
     */
    val imageUrl: String,

    /**
     * Tickets of event
     */
    val tickets: Collection<com.cerberus.artishub.events.core.models.Ticket>
)
