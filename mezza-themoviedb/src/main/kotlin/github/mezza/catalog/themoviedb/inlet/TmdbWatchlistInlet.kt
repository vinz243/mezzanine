package github.mezza.catalog.themoviedb.inlet

import github.mezza.catalog.themoviedb.TmdbSession
import github.mezza.catalog.themoviedb.model.TmdbMapper
import github.mezza.catalog.themoviedb.model.TmdbMovie
import github.mezza.core.catalog.EntityRegistry
import info.movito.themoviedbapi.TmdbApi
import info.movito.themoviedbapi.model.MovieDb
import info.movito.themoviedbapi.model.core.AccountID
import info.movito.themoviedbapi.model.core.MovieResultsPage

class TmdbWatchlistInlet(
        private val api: TmdbApi,
        private val session: TmdbSession,
        private val registry: EntityRegistry<TmdbMovie>,
        private val handler: TmdbMovie.() -> Unit
) {
    private val mapper = TmdbMapper()

    fun processWatchlist() {
        getFullWatchlist()
                .map { mapper.movieEntity(it) }
                .filter { !registry.isRegistered(it) }
                .onEach { handler(it) }
                .forEach { registry.register(it) }
    }

    private fun getFullWatchlist(): List<MovieDb> {
        val watchListMovies = getWatchlistPage(1)

        val totalPages = watchListMovies.totalPages
        val results = watchListMovies.results.toMutableList()

        if (totalPages >= 2) {
            for (page in 2..totalPages) {
                results.addAll(getWatchlistPage(page).results)
            }
        }

        return results.toList()
    }

    private fun getWatchlistPage(page: Int): MovieResultsPage {
        return api.account.getWatchListMovies(
                session.sessionToken,
                AccountID(session.account.id),
                page
        ) ?: throw IllegalStateException()
    }
}