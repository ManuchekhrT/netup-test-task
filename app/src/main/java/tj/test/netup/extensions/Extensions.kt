package tj.test.netup.extensions

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import tj.test.netup.data.model.MediaDto
import tj.test.netup.data.model.MediaEntity
import tj.test.netup.presentation.MediaUI

@Suppress("InjectDispatcher")
interface FlowUseCase<in Input, Output> {
    /**
     * Executes the flow on Dispatchers.IO and wraps exceptions inside it into Result
     */
    operator fun invoke(param: Input): Flow<Result<Output>> =
        execute(param)
            .catch { e -> emit(Result.failure(e)) }
            .flowOn(Dispatchers.IO)

    fun execute(param: Input): Flow<Result<Output>>
}

fun Context.showToast(msg: String?) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

typealias MediaUIList = List<MediaUI>

fun MediaDto.asEntity() = MediaEntity(
    name = this.name,
    url = this.url
)
