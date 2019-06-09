package github.mezza.catalog.themoviedb

import info.movito.themoviedbapi.TmdbApi
import info.movito.themoviedbapi.model.core.SessionToken

interface TmdbSessionFactory {
    fun createSession(): TmdbSession
}

class SimpleEnvSessionFactory(
        private val api: TmdbApi
) : TmdbSessionFactory {
    override fun createSession(): TmdbSession {
        val sessionLogin = api.authentication.getSessionLogin(
                System.getenv("TMDB_USER"),
                System.getenv("TMDB_PASSWORD")
        ) ?: throw IllegalStateException()

        val sessionToken = SessionToken(sessionLogin.sessionId)
        val account = api.account.getAccount(sessionToken)

        return TmdbSession(sessionToken = sessionToken, account = account)
    }
}