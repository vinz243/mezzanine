import github.mezza.catalog.themoviedb.TmdbCatalogFactory
import github.mezza.core.Context
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldEqual
import org.spekframework.spek2.Spek


object CatalogTest: Spek({
    val context = Context("en_US")
    group("Catalog.search") {
        val catalogFactory = TmdbCatalogFactory(System.getenv("TMDB_API_KEY"))
        test("returns empty list when no results") {
            val results = catalogFactory.createCatalog()
                    .search(context, "djsdoiiudhksdnei")

            results.isEmpty() shouldBe true
        }

        test("returns not empty list") {
            val results = catalogFactory.createCatalog()
                    .search(context, "lord of the rings")

            results.isEmpty() shouldBe false
        }
    }
    group("Catalog.findById") {
        val catalogFactory = TmdbCatalogFactory(System.getenv("TMDB_API_KEY"))
        test("returns nothing for not found") {
            catalogFactory.createCatalog().findById(context, "-41") shouldBe null
        }

        test("returns not empty list") {
            catalogFactory.createCatalog().findById(context, "120")
                    ?.title shouldEqual "The Lord of the Rings: The Fellowship of the Ring"
        }
    }
})