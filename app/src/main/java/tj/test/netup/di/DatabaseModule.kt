package tj.test.netup.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import tj.test.netup.data.datasource.local.AppDatabase
import tj.test.netup.data.datasource.local.MediaDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "my-database"
        ).build()
    }

    @Provides
    fun provideMediaDao(database: AppDatabase): MediaDao {
        return database.userDao()
    }
}