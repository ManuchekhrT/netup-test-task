package tj.test.netup.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tj.test.netup.data.repository.MediaRepository
import tj.test.netup.domain.mapper.MediaMapper
import tj.test.netup.domain.usecases.MediaUseCase
import tj.test.netup.domain.usecases.MediaUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideMediaMapper() = MediaMapper()

    @Singleton
    @Provides
    fun provideMediaUseCase(
        mapper: MediaMapper,
        repository: MediaRepository
    ): MediaUseCase = MediaUseCaseImpl(mapper, repository)
}