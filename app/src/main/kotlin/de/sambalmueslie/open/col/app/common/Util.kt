package de.sambalmueslie.open.col.app.common


import io.micronaut.data.repository.PageableRepository
import org.slf4j.LoggerFactory

fun <E, ID> PageableRepository<E, ID>.findByIdOrNull(id: ID): E? = this.findById(id).orElseGet { null }

inline fun <T> Iterable<T>.forEachWithTryCatch(action: (T) -> Unit) {
    for (element in this) {
        try {
            action(element)
        } catch (e: Exception) {
            LoggerFactory.getLogger("Iterable").error("Exception occurred", e)
        }
    }
}

inline fun <T> measureTimeMillisWithReturn(function: () -> T): Pair<Long, T> {
    val startTime = System.currentTimeMillis()
    val result: T = function.invoke()
    val duration = System.currentTimeMillis() - startTime
    return Pair(duration, result)
}

inline fun <T> executeWithReturn(result: T? = null, function: () -> Any): T? {
    function.invoke()
    return result
}
