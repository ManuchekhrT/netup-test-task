package tj.test.netup.data.repository

import tj.test.netup.data.model.MediaEntity

interface MediaRepository {
    suspend fun fetchMedias(): List<MediaEntity>
}