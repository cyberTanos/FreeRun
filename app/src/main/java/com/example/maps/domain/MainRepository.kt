package com.example.maps.domain

import com.example.maps.data.local.RunDAO
import com.example.maps.model.entity.RunEntity
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepositoryImpl @Inject constructor(
    private val dao: RunDAO
) : MainRepository {

    override suspend fun insertRun(runEntity: RunEntity) {
        dao.insertRun(runEntity)
    }

    override suspend fun deleteRun(runEntity: RunEntity) {
        dao.deleteRun(runEntity)
    }

    override suspend fun getAllRunSortedByDate(): List<RunEntity> {
        return withContext(Dispatchers.IO) {
            dao.getAllSortedByDate()
        }
    }

    override suspend fun getAllRunSortedByDistance(): List<RunEntity> {
        return withContext(Dispatchers.IO) {
            dao.getAllSortedByDistanceInMeters()
        }
    }

    override suspend fun getAllRunSortedByTimeInMillis(): List<RunEntity> {
        return withContext(Dispatchers.IO) {
            dao.getAllSortedByTimeInMillis()
        }
    }

    override suspend fun getAllRunSortedByAvgSpeed(): List<RunEntity> {
        return withContext(Dispatchers.IO) {
            dao.getAllSortedByAvgSpeedInKMH()
        }
    }

    override suspend fun getAllRunSortedByCaloriesBurned(): List<RunEntity> {
        return withContext(Dispatchers.IO) {
            dao.getAllSortedByCaloriesBurned()
        }
    }

    override suspend fun getTotalAvgSpeed(): Float {
        return withContext(Dispatchers.IO) {
            dao.getTotalAvgSpeed()
        }
    }

    override suspend fun getTotalDistance(): Int {
        return withContext(Dispatchers.IO) {
            dao.getTotalDistance()
        }
    }

    override suspend fun getTotalCaloriesBurned(): Int {
        return withContext(Dispatchers.IO) {
            dao.getTotalCaloriesBurned()
        }
    }

    override suspend fun getTotalTimeInMillis(): Long {
        return withContext(Dispatchers.IO) {
            dao.getTotalTimeInMillis()
        }
    }
}

interface MainRepository {

    suspend fun insertRun(runEntity: RunEntity)

    suspend fun deleteRun(runEntity: RunEntity)

    suspend fun getAllRunSortedByDate(): List<RunEntity>

    suspend fun getAllRunSortedByDistance(): List<RunEntity>

    suspend fun getAllRunSortedByTimeInMillis(): List<RunEntity>

    suspend fun getAllRunSortedByAvgSpeed(): List<RunEntity>

    suspend fun getAllRunSortedByCaloriesBurned(): List<RunEntity>

    suspend fun getTotalAvgSpeed(): Float

    suspend fun getTotalDistance(): Int

    suspend fun getTotalCaloriesBurned(): Int

    suspend fun getTotalTimeInMillis(): Long

}
