package hu.toliver.whacook.data.local

import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.driver.worker.WebWorkerDriver
import hu.toliver.whacook.db.Recipes
import hu.toliver.whacook.db.WebDatabase
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.w3c.dom.Worker

@OptIn(ExperimentalWasmJsInterop::class)

object WebDatabaseFactory {

    @OptIn(DelicateCoroutinesApi::class)
    private val databaseDeferred: Deferred<WebDatabase> = GlobalScope.async {
        // Initialize WebWorkerDriver with the worker script path.
        // Ensure "sqlite.worker.js" is available in your build output (e.g. via copy-webpack-plugin).
        val worker = Worker("sqlite.worker.js")
        val driver = WebWorkerDriver(worker)

        // Check current database version
        val currentVersion = driver.executeQuery(
            null,
            "PRAGMA user_version;",
            { cursor ->
                QueryResult.Value(
                    if (cursor.next().value) {
                        cursor.getLong(0)
                    } else {
                        null
                    }
                )
            },
            0
        ).await() ?: 0L

        val schemaVersion = WebDatabase.Schema.version

        if (currentVersion == 0L) {
            WebDatabase.Schema.create(driver).await()
            driver.execute(null, "PRAGMA user_version = $schemaVersion;", 0).await()
        } else if (currentVersion < schemaVersion) {
            WebDatabase.Schema.migrate(driver, currentVersion, schemaVersion).await()
            driver.execute(null, "PRAGMA user_version = $schemaVersion;", 0).await()
        }

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
