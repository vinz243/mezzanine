package github.mezza.catalog.themoviedb

import info.movito.themoviedbapi.TmdbApi

class TmdbCatalogFactory(private val apiKey: String) {
    fun createCatalog () = TmdbCatalog(TmdbApi(apiKey))
}