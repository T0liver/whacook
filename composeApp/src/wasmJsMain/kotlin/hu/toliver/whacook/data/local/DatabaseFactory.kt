package hu.toliver.whacook.data.local

import app.cash.sqldelight.async.coroutines.await
import app.cash.sqldelight.driver.worker.WebWorkerDriver
import hu.toliver.whacook.db.WebDatabase
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.w3c.dom.Worker

object WebDatabaseFactory {

    private val databaseDeferred: Deferred<WebDatabase> = GlobalScope.async {
        // Initialize WebWorkerDriver with the worker script path.
        // Ensure "sqlite.worker.js" is available in your build output (e.g. via copy-webpack-plugin).
        val worker = Worker("sqlite.worker.js")
        val driver = WebWorkerDriver(worker)

        // Asynchronously create the database schema
        WebDatabase.Schema.create(driver).await()

        WebDatabase(driver)
    }

    // Suspending provider to ensure initialization is complete before use
    suspend fun getDatabase(): WebDatabase = databaseDeferred.await()
}

