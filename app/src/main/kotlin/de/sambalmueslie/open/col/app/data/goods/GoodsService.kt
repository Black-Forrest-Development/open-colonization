package de.sambalmueslie.open.col.app.data.goods


import de.sambalmueslie.open.col.app.cache.CacheService
import de.sambalmueslie.open.col.app.common.GenericCrudService
import de.sambalmueslie.open.col.app.common.PageableSequence
import de.sambalmueslie.open.col.app.common.TimeProvider
import de.sambalmueslie.open.col.app.data.goods.api.Goods
import de.sambalmueslie.open.col.app.data.goods.api.GoodsChangeRequest
import de.sambalmueslie.open.col.app.data.goods.db.GoodsData
import de.sambalmueslie.open.col.app.data.goods.db.GoodsRepository
import de.sambalmueslie.open.col.app.data.world.api.World
import de.sambalmueslie.open.col.app.error.InvalidRequestException
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class GoodsService(
    private val repository: GoodsRepository,
    private val timeProvider: TimeProvider,
    cacheService: CacheService,
) : GenericCrudService<Long, Goods, GoodsChangeRequest, GoodsData>(
    repository, cacheService, Goods::class, logger
) {


    companion object {
        private val logger: Logger = LoggerFactory.getLogger(GoodsService::class.java)
        private const val WORLD_REFERENCE = "world"
    }

    fun create(world: World, request: GoodsChangeRequest) {
        create(request, mapOf(Pair(WORLD_REFERENCE, world)))
    }

    override fun createData(request: GoodsChangeRequest, properties: Map<String, Any>): GoodsData {
        val world = properties[WORLD_REFERENCE] as? World ?: throw InvalidRequestException("Cannot find world")
        return GoodsData.create(world, request, timeProvider.now())
    }

    override fun updateData(data: GoodsData, request: GoodsChangeRequest): GoodsData {
        return data.update(request, timeProvider.now())
    }

    override fun isValid(request: GoodsChangeRequest) {
        if (request.name.isBlank()) throw InvalidRequestException("Name cannot be blank")
    }

    fun findByName(name: String): Goods? {
        return repository.findByName(name)?.convert()
    }

    fun delete(world: World) {
        val sequence = PageableSequence() { repository.findByWorldId(world.id, it) }
        sequence.forEach { delete(it) }
    }




}
