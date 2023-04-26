package tj.test.netup.data.datasource.remote

import retrofit2.http.GET
import tj.test.netup.data.model.MediaResponse

interface MediaService {

    @GET("downloads/media.json")
    suspend fun fetchMedia(): MediaResponse

}