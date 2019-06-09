import github.mezza.core.Context
import github.mezza.core.catalog.Catalog
import github.mezza.core.catalog.CatalogEntity
import github.mezza.web.catalogApi
import github.mezza.web.context.QueryContextFactory
import github.mezza.web.setup
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.restassured.RestAssured.`when`
import org.hamcrest.Matchers.`is`
import org.spekframework.spek2.Spek
import java.util.concurrent.TimeUnit

fun rest() = `when`()
fun <T> Is(value: T) = `is`(value)

data class TestCatalogEntity(override val id: String, val name: String) : CatalogEntity

object CatalogApiTest : Spek({

    val catalog = mockk<Catalog<TestCatalogEntity>>()

    every {
        catalog.catalogId
    } returns "testcat"

    val engine = embeddedServer(Netty, 8080) {
        setup()
        routing {
            catalogApi(
                    catalog = catalog,
                    contextFactory = QueryContextFactory()
            )
        }
    }.start(wait = false)

    beforeEachTest {
        clearAllMocks()
    }

    group("GET /") {
        test("return 400 error for missing params ") {
            every {
                catalog.search(context = any(), query = "foo")
            } returns emptyList()

            rest().get("/testcat?query=foo")
                    .then()
                    .statusCode(400)
        }

        test("return 0 element ") {
            every {
                catalog.search(context = any(), query = "foo")
            } returns emptyList()

            rest().get("/testcat?query=foo&language=en_US")
                    .then()
                    .statusCode(200)
                    .body("size()", Is(0))
        }

        test("return 1 element ") {
            every {
                catalog.search(context = any(), query = any())
            } returns listOf(TestCatalogEntity(id = "foobar", name = "Foobar"))

            rest().get("/testcat?query=foo&language=en_US")
                    .then()
                    .statusCode(200)
                    .body("size()", Is(1))

            verify(exactly = 1) {
                catalog.search(context = Context("en_US"), query = "foo")
            }
        }

    }

    group("GET /{id}") {

        test("return 404 error for not found") {
            every {
                catalog.findById(context = any(), id = "foo")
            } returns null

            rest().get("/testcat/foo?language=en_US")
                    .then()
                    .statusCode(404)
        }

        test("return 1 element ") {
            every {
                catalog.findById(context = any(), id = "foo")
            } returns TestCatalogEntity(id = "foo", name = "Bar")

            rest().get("/testcat/foo?language=en_US")
                    .then()
                    .statusCode(200)
                    .body("id", Is("foo"))
                    .body("name", Is("Bar"))
        }
    }

    afterGroup {
        engine.stop(gracePeriod = 0, timeout = 0, timeUnit = TimeUnit.SECONDS)
    }

})