package de.sambalmueslie.open.col.app.data.world

import de.sambalmueslie.open.col.app.data.world.api.WorldChangeRequest
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

@MicronautTest
class WorldServiceTest {

    @Inject
    lateinit var service: WorldService

    @Test
    fun createSampleWorld() {
        val request = WorldChangeRequest("Sample World")
        val world = service.create(request)

        assertEquals(request.name, world.name)
        assertTrue(world.id > 0)
    }
}
