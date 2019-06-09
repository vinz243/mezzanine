package github.mezza.catalog.themoviedb

import info.movito.themoviedbapi.model.config.Account
import info.movito.themoviedbapi.model.core.SessionToken

data class TmdbSession(val sessionToken: SessionToken, val account: Account)