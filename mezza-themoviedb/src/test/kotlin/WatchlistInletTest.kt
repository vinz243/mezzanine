import github.mezza.catalog.themoviedb.EnvApiFactory
import github.mezza.catalog.themoviedb.SimpleEnvSessionFactory
import github.mezza.catalog.themoviedb.TmdbSession
import github.mezza.catalog.themoviedb.inlet.TmdbWatchlistInlet
import github.mezza.catalog.themoviedb.model.TmdbMovie
import github.mezza.core.catalog.EntityRegistry
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.spekframework.spek2.Spek


object WatchlistInletTest : Spek({
    group("processWatchlist") {
        val tmdbApi = EnvApiFactory().createApi()
        val session = SimpleEnvSessionFactory(tmdbApi).createSession()

        val registry = mockk<EntityRegistry<TmdbMovie>>(relaxed = true)
        val handler = mockk<TmdbMovie.() -> Unit>(relaxed = true)

        val inlet = TmdbWatchlistInlet(
                api = tmdbApi,
                registry = registry,
                session = TmdbSession(session.sessionToken, session.account),
                handler = handler
        )

        beforeEachTest {
            clearAllMocks()
        }

        test("does not call handler when everything registered") {

            every {
                registry.isRegistered(any())
            } returns true

            inlet.processWatchlist()

            verify(exactly = 0) {
                registry.register(any())
            }

            verify(exactly = 2) {
                registry.isRegistered(any())
            }

            verify(exactly = 0) {
                handler.invoke(any())
            }
        }

        test("call handler twice when nothing registered") {

            every {
                registry.isRegistered(any())
            } returns false

            inlet.processWatchlist()

            verify(exactly = 2) {
                registry.register(any())
            }

            verify(exactly = 2) {
                registry.isRegistered(any())
            }

            verify {
                handler.invoke(TmdbMovie(id = "122", title = "The Lord of the Rings: The Return of the King", genres = emptyList()))
                handler.invoke(TmdbMovie(id = "120", title = "The Lord of the Rings: The Fellowship of the Ring", genres = emptyList()))
            }
        }
    }

})