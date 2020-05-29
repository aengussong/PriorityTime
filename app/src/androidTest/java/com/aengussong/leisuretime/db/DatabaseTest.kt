package com.aengussong.leisuretime.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.aengussong.leisuretime.DbRelatedTest
import com.aengussong.leisuretime.data.local.dao.LeisureDao
import com.aengussong.leisuretime.util.getOrAwaitValue
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.koin.core.inject

class DatabaseTest : DbRelatedTest() {

    private val leisureDao: LeisureDao by inject()

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @Test
    fun getLowestCounter_returnsLowestCounterForCurrentLevel() = runBlocking {
        databaseManager.populateDatabase()
        val entityWithLowestCounterOnSecondLevel = databaseManager.lowestSecondLevel
        val secondLevelAncestry = entityWithLowestCounterOnSecondLevel.ancestry

        val result = leisureDao.getLowestCounter(secondLevelAncestry)

        Assert.assertEquals(entityWithLowestCounterOnSecondLevel.counter, result)
    }

    @Test
    fun getLeisures_shouldReturnOrderedByAncestry() = runBlocking {
        databaseManager.populateDatabase()
        val expected = databaseManager.getOrderedByAncestry()

        val result = leisureDao.getLeisures().getOrAwaitValue()

        Assert.assertEquals(expected, result)
    }

    @Test
    fun incrementLeisureCounter_counterShouldBeIncremented() = runBlocking {
        databaseManager.populateDatabase()
        val testEntity = databaseManager.lowestSecondLevel
        val parentEntity = databaseManager.lowestFirstLevel

        leisureDao.incrementLeisures(listOf(testEntity.id, parentEntity.id))

        val resultTestEntity = leisureDao.getLeisure(testEntity.id)
        val resultParentEntity = leisureDao.getLeisure(parentEntity.id)
        Assert.assertEquals(testEntity.counter + 1, resultTestEntity.counter)
        Assert.assertEquals(parentEntity.counter + 1, resultParentEntity.counter)
    }

}