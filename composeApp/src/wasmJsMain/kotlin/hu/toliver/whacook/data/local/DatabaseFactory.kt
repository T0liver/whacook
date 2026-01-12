package hu.toliver.whacook.data.local

import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.driver.worker.WebWorkerDriver
import hu.toliver.whacook.db.Recipes
import hu.toliver.whacook.db.WebDatabase
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.w3c.dom.Worker

@OptIn(kotlin.js.ExperimentalWasmJsInterop::class)

object WebDatabaseFactory {

    @OptIn(DelicateCoroutinesApi::class)
    private val databaseDeferred: Deferred<WebDatabase> = GlobalScope.async {
        // Initialize WebWorkerDriver with the worker script path.
        // Ensure "sqlite.worker.js" is available in your build output (e.g. via copy-webpack-plugin).
        val worker = Worker("sqlite.worker.js")
        val driver = WebWorkerDriver(worker)

        // Asynchronously create the database schema
        WebDatabase.Schema.create(driver).await()

        val recipesAdapter = Recipes.Adapter(
            ratingAdapter = object : ColumnAdapter<Int, Long> {
                override fun decode(databaseValue: Long): Int = databaseValue.toInt()
                override fun encode(value: Int): Long = value.toLong()
            }
        )

        WebDatabase(driver, recipesAdapter)
    }

    // Suspending provider to ensure initialization is complete before use
    suspend fun getDatabase(): WebDatabase = databaseDeferred.await()
}
