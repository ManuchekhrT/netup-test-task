package tj.test.netup.data.datasource.local

import androidx.room.*
import tj.test.netup.data.model.MediaEntity

@Dao
interface MediaDao {
    @Query("SELECT * FROM media")
    fun getAllMedia(): List<MediaEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedia(media: MediaEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<MediaEntity>)

    @Update
    suspend fun updateMedia(media: MediaEntity)

    @Delete
    suspend fun deleteMedia(media: MediaEntity)

    @Query("DELETE FROM media")
    suspend fun deleteAll()
}