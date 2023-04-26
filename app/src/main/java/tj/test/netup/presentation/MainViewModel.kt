package tj.test.netup.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import tj.test.netup.domain.usecases.MediaUseCase
import tj.test.netup.extensions.MediaUIList
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: MediaUseCase
) : ViewModel() {

    private val _media = MutableLiveData<State<MediaUIList>>()
    val media: LiveData<State<MediaUIList>> get() = _media

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        fetchMedia()
    }

    private fun fetchMedia() {
        viewModelScope.launch {
            useCase(Unit).onStart {
                _isLoading.value = true
            }.onCompletion {
                _isLoading.value = false
            }.collect { result ->
                result.onFailure { error ->
                    _media.value = State.error(error.message)
                }.onSuccess {
                    _media.value = State.success(it)
                }
            }
        }
    }
}