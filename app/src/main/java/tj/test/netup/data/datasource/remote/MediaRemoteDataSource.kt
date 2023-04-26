package tj.test.netup.data.datasource.remote

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import tj.test.netup.data.model.MediaResponse

class MediaRemoteDataSource(
    private val service: MediaService,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun fetchMedia(): MediaResponse = withContext(ioDispatcher) {
        service.fetchMedia()
    }
}
