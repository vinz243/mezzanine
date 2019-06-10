package github.mezza.core.catalog

interface EntityRegistry<E : CatalogEntity> {
    fun register(entity: E)
    fun isRegistered(entity: E): Boolean
}