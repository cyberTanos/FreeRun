package com.example.maps.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.maps.model.entity.RunEntity

@Database(entities = [RunEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class RunDatabase : RoomDatabase() {

    abstract fun getRunDao(): RunDAO
}
