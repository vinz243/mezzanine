package github.mezza.catalog.themoviedb.model

import info.movito.themoviedbapi.model.MovieDb
import info.movito.themoviedbapi.model.people.PersonPeople

class TmdbMapper {
    fun personEntity(it: PersonPeople): TmdbMovie {
        TODO()
    }

    fun movieEntity(it: MovieDb): TmdbMovie {
        return TmdbMovie(
                id = it.id.toString(),
                genres = it.genres?.map { TmdbGenre(it.name, it.id.toString()) } ?: emptyList(),
                title = it.title
        )
    }
}