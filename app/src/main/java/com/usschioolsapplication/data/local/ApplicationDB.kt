package com.usschioolsapplication.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SchoolDetailsEntity::class], version = 1)
abstract class ApplicationDB : RoomDatabase() {
    abstract fun schoolDetailsDao(): SchoolDetailsDao

}