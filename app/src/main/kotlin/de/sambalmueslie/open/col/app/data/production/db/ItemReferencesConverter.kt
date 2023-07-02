package de.sambalmueslie.open.col.app.data.production.db


import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.micronaut.core.convert.ConversionContext
import io.micronaut.data.model.runtime.convert.AttributeConverter
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class ItemReferencesConverter(private val mapper: ObjectMapper) : AttributeConverter<ItemReferences, String> {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(ItemReferencesConverter::class.java)
    }

    override fun convertToPersistedValue(entityValue: ItemReferences?, context: ConversionContext): String? {
        return entityValue?.let { mapper.writeValueAsString(entityValue) }
    }

    override fun convertToEntityValue(persistedValue: String?, context: ConversionContext): ItemReferences {
        if (persistedValue == null) return ItemReferences()
        return mapper.readValue(persistedValue)
    }


}
