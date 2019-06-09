package github.mezza.catalog.themoviedb

import info.movito.themoviedbapi.TmdbApi

interface TmdbApiFactory {
    fun createApi(): TmdbApi
}

class EnvApiFactory : TmdbApiFactory {
    override fun createApi() = TmdbApi(System.getenv("TMDB_API_KEY"))
}