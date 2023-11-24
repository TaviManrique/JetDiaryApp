package com.manriquetavi.jetdiaryapp.presentation.screens.update_diary

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manriquetavi.jetdiaryapp.domain.model.Diary
import com.manriquetavi.jetdiaryapp.domain.repository.MongoDB
import com.manriquetavi.jetdiaryapp.util.RequestState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.mongodb.kbson.ObjectId

internal class UpdateDiaryViewModel(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _diary: MutableStateFlow<RequestState<Diary>> = MutableStateFlow(RequestState.Loading)
    val diary: StateFlow<RequestState<Diary>> = _diary.asStateFlow()

    var uiState: StateFlow<UiState> = MongoDB
        .getDiary(diaryId = ObjectId.invoke(savedStateHandle.get<String>(key = "diaryId")!!))
        .map { requestState ->
            when (requestState) {
                is RequestState.Success -> UiState.Success(
                    diary = requestState.data
                )
                RequestState.Loading -> UiState.Loading
                is RequestState.Error -> UiState.Error(requestState.error.message.orEmpty())
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState.Loading,
        )

}

/*
internal data class UiState(
    val selectedDiaryId: String? = null,
    val selectedDiary: Diary? = null,
    val title: String = "",
    val description: String = "",
    val mood: Mood = Mood.Neutral,
    val updatedDateTime: RealmInstant? = null
)*/

internal sealed interface UiState {
    object Loading : UiState
    data class Success(
        val diary: Diary
    ) : UiState

    data class Error(val message: String) : UiState
}