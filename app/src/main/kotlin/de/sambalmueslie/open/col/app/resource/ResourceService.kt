package de.sambalmueslie.open.col.app.resource


import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import de.sambalmueslie.open.col.app.common.findByIdOrNull
import de.sambalmueslie.open.col.app.resource.api.Resource
import de.sambalmueslie.open.col.app.resource.api.ResourceChangeRequest
import de.sambalmueslie.open.col.app.resource.db.ResourceData
import de.sambalmueslie.open.col.app.resource.db.ResourceRepository
import io.micronaut.core.io.ResourceLoader
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class ResourceService(
    private val loader: ResourceLoader,
    private val mapper: ObjectMapper,

    private val repository: ResourceRepository
) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(ResourceService::class.java)
    }

    fun setup() {
        logger.info("Run initial setup")
        repository.deleteAll()
        val rawData = loader.getResourceAsStream("setup/resources.json").get()
        val data: List<ResourceChangeRequest> = mapper.readValue(rawData)
        if (data.isNotEmpty()) repository.saveAll(data.map { ResourceData.create(it) })
    }

    fun findByName(name: String): Resource? {
        return repository.findByName(name)?.convert()
    }

    fun get(id: Long): Resource? {
        return repository.findByIdOrNull(id)?.convert()
    }

    fun getAll(pageable: Pageable): Page<Resource> {
        return repository.findAll(pageable).map { it.convert() }
    }


}
