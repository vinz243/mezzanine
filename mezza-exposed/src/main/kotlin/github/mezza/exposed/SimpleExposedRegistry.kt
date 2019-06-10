package github.mezza.exposed

import github.mezza.core.catalog.CatalogEntity
import github.mezza.core.catalog.EntityRegistry
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object RegisteredEntities : Table() {
    val entityId = varchar("entityId", 20)
}

class SimpleExposedRegistry<E : CatalogEntity> : EntityRegistry<E> {
    fun init() {
        transaction {
            SchemaUtils.create(RegisteredEntities)
        }
    }

    override fun register(entity: E) {
        transaction {
            RegisteredEntities.insert {
                it[entityId] = entity.id
            }
        }
    }

    override fun isRegistered(entity: E): Boolean {
        return transaction {
            !RegisteredEntities.select { RegisteredEntities.entityId eq entity.id }.empty()
        }
    }

}