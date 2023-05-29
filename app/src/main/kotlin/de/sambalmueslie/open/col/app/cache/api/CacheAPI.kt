package de.sambalmueslie.open.col.app.cache.api

import io.micronaut.security.authentication.Authentication

interface CacheAPI {
    companion object {
        const val PERMISSION_READ = "opencol.cache.read"
        const val PERMISSION_WRITE = "opencol.cache.write"
    }

    fun get(auth: Authentication, key: String): CacheInfo?
    fun getAll(auth: Authentication): List<CacheInfo>

    fun reset(auth: Authentication, key: String): CacheInfo?
    fun resetAll(auth: Authentication): List<CacheInfo>


}
