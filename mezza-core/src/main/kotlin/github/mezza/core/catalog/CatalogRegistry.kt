package github.mezza.core.catalog

interface CatalogRegistry<E : CatalogEntity> {
    fun register(entity: E)
    fun isRegistered(entity: E): Boolean
}