package de.sambalmueslie.open.col.app.data.item


import de.sambalmueslie.open.col.app.cache.CacheService
import de.sambalmueslie.open.col.app.common.GenericCrudService
import de.sambalmueslie.open.col.app.common.PageableSequence
import de.sambalmueslie.open.col.app.common.SimpleDataObjectConverter
import de.sambalmueslie.open.col.app.common.TimeProvider
import de.sambalmueslie.open.col.app.data.item.api.Item
import de.sambalmueslie.open.col.app.data.item.api.ItemChangeRequest
import de.sambalmueslie.open.col.app.data.item.db.ItemData
import de.sambalmueslie.open.col.app.data.item.db.ItemRepository
import de.sambalmueslie.open.col.app.data.world.api.World
import de.sambalmueslie.open.col.app.error.InvalidRequestException
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class ItemService(
    private val repository: ItemRepository,
    private val timeProvider: TimeProvider,
    cacheService: CacheService,
) : GenericCrudService<Long, Item, ItemChangeRequest, ItemData>(
    repository, SimpleDataObjectConverter(), cacheService, Item::class, logger
) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(ItemService::class.java)
        private const val WORLD_REFERENCE = "world"
    }


    fun create(world: World, request: ItemChangeRequest) {
        create(request, mapOf(Pair(WORLD_REFERENCE, world)))
    }


    override fun createData(request: ItemChangeRequest, properties: Map<String, Any>): ItemData {
        val world = properties[WORLD_REFERENCE] as? World ?: throw InvalidRequestException("Cannot find world")
        return ItemData.create(world, request, timeProvider.now())
    }

    override fun updateData(data: ItemData, request: ItemChangeRequest): ItemData {
        return data.update(request, timeProvider.now())
    }

    override fun isValid(request: ItemChangeRequest) {
        if (request.name.isBlank()) throw InvalidRequestException("Name cannot be blank")
    }

    fun findByName(name: String): Item? {
        return repository.findByName(name)?.convert()
    }

    fun delete(world: World) {
        val sequence = PageableSequence() { repository.findByWorldId(world.id, it) }
        sequence.forEach { delete(it) }
    }

    fun getByIds(ids: Set<Long>): List<Item> {
        return repository.findByIdIn(ids).map { it.convert() }
    }


}
