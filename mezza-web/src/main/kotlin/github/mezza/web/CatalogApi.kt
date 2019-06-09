package github.mezza.web

import github.mezza.core.catalog.Catalog
import github.mezza.core.catalog.CatalogEntity
import github.mezza.web.context.ContextFactory
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get

fun Routing.catalogApi(catalog: Catalog<out CatalogEntity>, contextFactory: ContextFactory) {
    get(catalog.catalogId + "/") {
        val query = call.parameters["query"] ?: throw MissingParamException("Missing query")

        call.respond(catalog.search(
                context = contextFactory.getContext(call),
                query = query
        ))
    }

    get(catalog.catalogId + "/{id}") {
        val id = call.parameters["id"] ?: throw MissingParamException("Missing id")
        val language = call.parameters["language"] ?: throw MissingParamException("Missing language")

        val message = catalog.findById(
                context = contextFactory.getContext(call),
                id = id
        ) ?: throw EntityNotFoundException("Entity with id $id not found")

        call.respond(message)
    }
}