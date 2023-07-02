package de.sambalmueslie.open.col.app.data.production


import de.sambalmueslie.open.col.app.cache.CacheService
import de.sambalmueslie.open.col.app.common.GenericCrudService
import de.sambalmueslie.open.col.app.common.PageableSequence
import de.sambalmueslie.open.col.app.common.TimeProvider
import de.sambalmueslie.open.col.app.data.item.ItemService
import de.sambalmueslie.open.col.app.data.production.api.ProductionChain
import de.sambalmueslie.open.col.app.data.production.api.ProductionChainChangeRequest
import de.sambalmueslie.open.col.app.data.production.db.ProductionChainData
import de.sambalmueslie.open.col.app.data.production.db.ProductionChainRepository
import de.sambalmueslie.open.col.app.data.world.api.World
import de.sambalmueslie.open.col.app.error.InvalidRequestException
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class ProductionChainService(
    private val repository: ProductionChainRepository,
    private val timeProvider: TimeProvider,
    cacheService: CacheService,
    private val converter: ProductionChainConverter,

    private val itemService: ItemService,
) : GenericCrudService<Long, ProductionChain, ProductionChainChangeRequest, ProductionChainData>(
    repository, converter, cacheService, ProductionChain::class, logger
) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(ProductionChainService::class.java)
    }

    override fun createData(request: ProductionChainChangeRequest, properties: Map<String, Any>): ProductionChainData {
        val source = request.source.mapNotNull { itemService.findByName(it) }
        val deliver = request.deliver.mapNotNull { itemService.findByName(it) }

        return ProductionChainData.create(request, source, deliver, timeProvider.now())
    }

    override fun updateData(data: ProductionChainData, request: ProductionChainChangeRequest): ProductionChainData {
        return data.update(request, timeProvider.now())
    }

    override fun isValid(request: ProductionChainChangeRequest) {
        if (request.deliver.isEmpty()) throw InvalidRequestException("Deliver could not be empty")
    }


    fun getByIds(ids: Set<Long>): List<ProductionChain> {
        val data =  repository.findByIdIn(ids)
        return converter.convert(data)
    }
}
