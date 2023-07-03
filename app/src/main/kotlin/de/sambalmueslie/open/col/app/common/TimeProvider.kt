package de.sambalmueslie.open.col.app.common

import java.time.LocalDateTime

fun interface TimeProvider {
    fun now(): LocalDateTime
}
