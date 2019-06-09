package github.mezza.catalog.themoviedb

import info.movito.themoviedbapi.TmdbApi

interface TmdbCatalogFactory {
    fun createCatalog(): TmdbCatalog
}

class EnvCatalogFactory : TmdbCatalogFactory {
    override fun createCatalog() = TmdbCatalog(catalogId = "themoviedb", api = TmdbApi(System.getenv("TMDB_API_KEY")))
}