package com.example.maps.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.maps.model.entity.RunEntity

@Dao
interface RunDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRun(runEntity: RunEntity)

    @Delete
    suspend fun deleteRun(runEntity: RunEntity)

    @Query("SELECT * FROM running_table ORDER BY timestamp DESC")
    fun getAllSortedByDate(): List<RunEntity>

    @Query("SELECT * FROM running_table ORDER BY timeInMillis DESC")
    fun getAllSortedByTimeInMillis(): List<RunEntity>

    @Query("SELECT * FROM running_table ORDER BY avgSpeedInKMH DESC")
    fun getAllSortedByAvgSpeedInKMH(): List<RunEntity>

    @Query("SELECT * FROM running_table ORDER BY distanceInMeters DESC")
    fun getAllSortedByDistanceInMeters(): List<RunEntity>

    @Query("SELECT * FROM running_table ORDER BY caloriesBurned DESC")
    fun getAllSortedByCaloriesBurned(): List<RunEntity>

    @Query("SELECT SUM(timeInMillis) FROM running_table")
    fun getTotalTimeInMillis(): Long

    @Query("SELECT SUM(caloriesBurned) FROM running_table")
    fun getTotalCaloriesBurned(): Int

    @Query("SELECT SUM(distanceInMeters) FROM running_table")
    fun getTotalDistance(): Int

    @Query("SELECT AVG(avgSpeedInKMH) FROM running_table")
    fun getTotalAvgSpeed(): Float

}
