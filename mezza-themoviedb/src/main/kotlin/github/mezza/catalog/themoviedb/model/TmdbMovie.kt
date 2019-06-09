package github.mezza.catalog.themoviedb.model

import github.mezza.core.catalog.CatalogEntity

data class TmdbMovie(
        override val id: String,
        val title: String,
        val genres: List<TmdbGenre>
) : CatalogEntity