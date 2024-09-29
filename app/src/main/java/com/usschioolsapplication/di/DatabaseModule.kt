package com.usschioolsapplication.di

import android.content.Context
import androidx.room.Room
import com.usschioolsapplication.data.local.ApplicationDB
import com.usschioolsapplication.data.local.SchoolDetailsDao
import com.usschioolsapplication.data.local.SchoolDetailsRepository
import com.usschioolsapplication.data.local.SchoolDetailsRepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ApplicationDB {
        return Room.databaseBuilder(
            context,
            ApplicationDB::class.java,
            "app_db"
        ).build()
    }

    @Provides
    fun provideSchoolDetailsDao(appDB: ApplicationDB) = appDB.schoolDetailsDao()

    @Provides
    fun provideSchoolDetailsRepository(schoolDetailsDao: SchoolDetailsDao) = SchoolDetailsRepository(schoolDetailsDao) as SchoolDetailsRepositoryInterface
}