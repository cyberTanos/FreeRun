package com.example.maps.domain

import com.example.maps.data.local.RunDAO
import com.example.maps.model.entity.RunEntity
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val dao: RunDAO
) : MainRepository {

    override suspend fun insertRun(runEntity: RunEntity) {
        dao.insertRun(runEntity)
    }

    override suspend fun deleteRun(runEntity: RunEntity) {
        dao.deleteRun(runEntity)
    }

    override fun getAllRunSortedByDate(): List<RunEntity> {
        return dao.getAllSortedByDate()
    }

    override fun getAllRunSortedByDistance(): List<RunEntity> {
        return dao.getAllSortedByDistanceInMeters()
    }

    override fun getAllRunSortedByTimeInMillis(): List<RunEntity> {
        return dao.getAllSortedByTimeInMillis()
    }

    override fun getAllRunSortedByAvgSpeed(): List<RunEntity> {
        return dao.getAllSortedByAvgSpeedInKMH()
    }

    override fun getAllRunSortedByCaloriesBurned(): List<RunEntity> {
        return dao.getAllSortedByCaloriesBurned()
    }

    override fun getTotalAvgSpeed(): Float {
        return dao.getTotalAvgSpeed()
    }

    override fun getTotalDistance(): Int {
        return dao.getTotalDistance()
    }

    override fun getTotalCaloriesBurned(): Int {
        return dao.getTotalCaloriesBurned()
    }

    override fun getTotalTimeInMillis(): Long {
        return dao.getTotalTimeInMillis()
    }
}

interface MainRepository {

    suspend fun insertRun(runEntity: RunEntity)

    suspend fun deleteRun(runEntity: RunEntity)

    fun getAllRunSortedByDate(): List<RunEntity>

    fun getAllRunSortedByDistance(): List<RunEntity>

    fun getAllRunSortedByTimeInMillis(): List<RunEntity>

    fun getAllRunSortedByAvgSpeed(): List<RunEntity>

    fun getAllRunSortedByCaloriesBurned(): List<RunEntity>

    fun getTotalAvgSpeed(): Float

    fun getTotalDistance(): Int

    fun getTotalCaloriesBurned(): Int

    fun getTotalTimeInMillis(): Long

}
