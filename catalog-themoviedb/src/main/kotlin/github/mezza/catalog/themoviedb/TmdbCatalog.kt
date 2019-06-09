package github.mezza.catalog.themoviedb


import github.mezza.catalog.themoviedb.model.TmdbGenre
import github.mezza.catalog.themoviedb.model.TmdbMovie
import github.mezza.core.Context
import github.mezza.core.catalog.Catalog
import info.movito.themoviedbapi.TmdbApi
import info.movito.themoviedbapi.model.MovieDb
import info.movito.themoviedbapi.model.core.ResponseStatusException
import info.movito.themoviedbapi.model.people.PersonPeople

class TmdbCatalog(override val catalogId: String, private val api: TmdbApi) : Catalog<TmdbMovie> {
    override fun findById(context: Context, id: String): TmdbMovie? {
        try {
            return movieEntity(api.movies.getMovie(id.toInt(), context.userLanguage))
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
                is MovieDb -> movieEntity(it)
                is PersonPeople -> personEntity(it)
                else -> null
            }
        }
    }

    private fun personEntity(it: PersonPeople): TmdbMovie {
        TODO()
    }

    private fun movieEntity(it: MovieDb): TmdbMovie {
        return TmdbMovie(
                id = it.id.toString(),
                genres = it.genres?.map { TmdbGenre(it.name, it.id.toString()) } ?: emptyList(),
                title = it.title
        )
    }
}

