package tj.test.netup.data.repository

import tj.test.netup.data.datasource.local.MediaDao
import tj.test.netup.data.datasource.remote.MediaRemoteDataSource
import tj.test.netup.data.model.MediaEntity
import tj.test.netup.extensions.asEntity
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val dataSource: MediaRemoteDataSource,
    private val dao: MediaDao
) : MediaRepository {
    override suspend fun fetchMedias(): List<MediaEntity> {
        val localMedia = dao.getAllMedia()

        return localMedia.ifEmpty {
            val remoteMedia = dataSource.fetchMedia().mediaList
            val mediaEntityList = remoteMedia.map { it.asEntity() }

            dao.insertAll(mediaEntityList)

            remoteMedia.map { it.asEntity() }
        }
    }
}