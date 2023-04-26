package tj.test.netup.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import tj.test.netup.data.repository.MediaRepository
import tj.test.netup.domain.mapper.MediaMapper
import tj.test.netup.extensions.FlowUseCase
import tj.test.netup.presentation.MediaUI
import javax.inject.Inject

interface MediaUseCase : FlowUseCase<Unit, List<MediaUI>>

class MediaUseCaseImpl @Inject constructor(
    private val mapper: MediaMapper,
    private val remote: MediaRepository
) : MediaUseCase {

    override fun execute(param: Unit): Flow<Result<List<MediaUI>>> = flow {
        emit(Result.success(remote.fetchMedias().map(mapper::entityToDomain)))
    }
}