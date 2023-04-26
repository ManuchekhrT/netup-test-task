package tj.test.netup.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import tj.test.netup.data.model.MediaEntity

@Database(entities = [MediaEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): MediaDao
}