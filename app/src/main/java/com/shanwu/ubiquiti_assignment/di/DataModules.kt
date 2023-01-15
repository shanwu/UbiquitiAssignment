package com.shanwu.ubiquiti_assignment.di

import com.shanwu.ubiquiti_assignment.data.source.AirQualityDataSource
import com.shanwu.ubiquiti_assignment.data.source.AirQualityRepository
import com.shanwu.ubiquiti_assignment.data.source.AirQualityRepositoryImpl
import com.shanwu.ubiquiti_assignment.data.source.remote.AirQualityRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RemoteTasksDataSource

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideTasksRepository(
        @RemoteTasksDataSource remoteDataSource: AirQualityDataSource,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): AirQualityRepository {
        return AirQualityRepositoryImpl(remoteDataSource)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @RemoteTasksDataSource
    @Provides
    fun provideTasksRemoteDataSource(
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): AirQualityDataSource {
        return AirQualityRemoteDataSource(ioDispatcher)
    }
}
