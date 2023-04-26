package tj.test.netup.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import tj.test.netup.data.datasource.local.MediaDao
import tj.test.netup.data.datasource.remote.MediaRemoteDataSource
import tj.test.netup.data.datasource.remote.MediaService
import tj.test.netup.data.repository.MediaRepository
import tj.test.netup.data.repository.MediaRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Singleton
    @Provides
    fun provideMediaRemoteDataSource(
        service: MediaService,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ) = MediaRemoteDataSource(service, coroutineDispatcher)

    @Singleton
    @Provides
    fun provideMediaRepository(
        mediaRemoteDataSource: MediaRemoteDataSource,
        mediaDao: MediaDao
    ): MediaRepository = MediaRepositoryImpl(mediaRemoteDataSource, mediaDao)

}