package github.mezza.core.catalog

import github.mezza.core.Context

interface Catalog<E: CatalogEntity> {
    val catalogId: String
    fun search (context: Context, query: String): List<E>
    fun findById (context: Context, id: String): E?
}