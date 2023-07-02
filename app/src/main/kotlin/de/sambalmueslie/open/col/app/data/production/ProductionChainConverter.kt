package de.sambalmueslie.open.col.app.data.production


import de.sambalmueslie.open.col.app.common.DataObjectConverter
import de.sambalmueslie.open.col.app.data.item.ItemService
import de.sambalmueslie.open.col.app.data.item.api.Item
import de.sambalmueslie.open.col.app.data.production.api.ProductionChain
import de.sambalmueslie.open.col.app.data.production.db.ProductionChainData
import io.micronaut.data.model.Page
import jakarta.inject.Singleton

@Singleton
class ProductionChainConverter(
    private val itemService: ItemService
) : DataObjectConverter<ProductionChain, ProductionChainData> {
    override fun convert(obj: ProductionChainData): ProductionChain {
        val itemIds = obj.source.itemIds + obj.deliver.itemIds
        val items = itemService.getByIds(itemIds).associateBy { it.id }

        return convert(obj, items)
    }

    override fun convert(objs: List<ProductionChainData>): List<ProductionChain> {
        val itemIds = objs.map { it.source.itemIds + it.deliver.itemIds }.flatten().toSet()
        val items = itemService.getByIds(itemIds).associateBy { it.id }
        return objs.map { convert(it, items) }
    }

    override fun convert(page: Page<ProductionChainData>): Page<ProductionChain> {
        val itemIds = page.content.map { it.source.itemIds + it.deliver.itemIds }.flatten().toSet()
        val items = itemService.getByIds(itemIds).associateBy { it.id }
        return page.map { convert(it, items) }
    }

    private fun convert(data: ProductionChainData, items: Map<Long, Item>): ProductionChain {
        return ProductionChain(
            data.id,
            data.source.itemIds.mapNotNull { items[it] },
            data.deliver.itemIds.mapNotNull { items[it] }
        )
    }


}
