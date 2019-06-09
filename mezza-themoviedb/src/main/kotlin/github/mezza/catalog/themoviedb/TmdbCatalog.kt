package github.mezza.catalog.themoviedb


import github.mezza.catalog.themoviedb.model.TmdbMapper
import github.mezza.catalog.themoviedb.model.TmdbMovie
import github.mezza.core.Context
import github.mezza.core.catalog.Catalog
import info.movito.themoviedbapi.TmdbApi
import info.movito.themoviedbapi.model.MovieDb
import info.movito.themoviedbapi.model.core.ResponseStatusException
import info.movito.themoviedbapi.model.people.PersonPeople

class TmdbCatalog(override val catalogId: String, private val api: TmdbApi) : Catalog<TmdbMovie> {
    private val mapper = TmdbMapper()
    override fun findById(context: Context, id: String): TmdbMovie? {
        try {
            return mapper.movieEntity(api.movies.getMovie(id.toInt(), context.userLanguage))
        } catch (e: ResponseStatusException) {
            if (e.responseStatus.statusCode == 34) {
                return null
            }

            throw e
        }
    }

    override fun search(context: Context, query: String): List<TmdbMovie> {
        return api.search.searchMulti(query, context.userLanguage, 0)
                .results.mapNotNull {
            when (it) {
                is MovieDb -> mapper.movieEntity(it)
                is PersonPeople -> mapper.personEntity(it)
                else -> null
            }
        }
    }

}

