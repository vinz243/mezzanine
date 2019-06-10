package github.mezza.exposed

import github.mezza.core.catalog.CatalogEntity
import org.amshove.kluent.`should be`
import org.jetbrains.exposed.sql.Database
import org.spekframework.spek2.Spek

data class TestEntity(override val id: String) : CatalogEntity

object ExposedRegistryTest : Spek({
    group("registry") {
        Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver")

        val registry = SimpleExposedRegistry<TestEntity>()

        beforeGroup {
            registry.init()
        }

        test("isRegistered returns false") {
            registry.isRegistered(TestEntity(id = "42")) `should be` false
            registry.isRegistered(TestEntity(id = "0")) `should be` false
        }

        test("register then isRegistered returns true") {
            registry.register(TestEntity(id = "42"))
            registry.isRegistered(TestEntity(id = "42")) `should be` true
            registry.isRegistered(TestEntity(id = "43")) `should be` false
        }
    }
})