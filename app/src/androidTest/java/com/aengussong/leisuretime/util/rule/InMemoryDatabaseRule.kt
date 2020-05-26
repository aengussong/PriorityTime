package com.aengussong.leisuretime.util.rule

import com.aengussong.leisuretime.util.module.inMemoryDb
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin

class InMemoryDatabaseRule : TestWatcher() {

    override fun starting(description: Description?) {
        loadKoinModules(inMemoryDb)
    }

    override fun finished(description: Description?) {
        stopKoin()
    }
}