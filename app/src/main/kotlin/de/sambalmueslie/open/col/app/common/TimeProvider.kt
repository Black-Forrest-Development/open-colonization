package de.sambalmueslie.open.col.app.common

import java.time.LocalDateTime

interface TimeProvider {
    fun now(): LocalDateTime
}