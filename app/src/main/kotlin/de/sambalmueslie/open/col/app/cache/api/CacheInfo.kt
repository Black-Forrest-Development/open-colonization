package de.sambalmueslie.open.col.app.cache.api

data class CacheInfo(
    val key: String,
    val name: String,
    val hitCount: Long,
    val missCount: Long,
    val loadSuccessCount: Long,
    val loadFailureCount: Long,
    val totalLoadTime: Long,
    val evictionCount: Long,
    val evictionWeight: Long,
)
