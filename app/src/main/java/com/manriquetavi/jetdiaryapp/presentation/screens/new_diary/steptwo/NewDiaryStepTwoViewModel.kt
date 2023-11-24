package com.manriquetavi.jetdiaryapp.presentation.screens.new_diary.steptwo

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manriquetavi.jetdiaryapp.domain.model.Diary
import com.manriquetavi.jetdiaryapp.domain.repository.MongoDB
import com.manriquetavi.jetdiaryapp.util.RequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NewDiaryStepTwoViewModel(
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    var moodId = mutableIntStateOf(0)
        private set
    var description = mutableStateOf("")
        private set


    var uiEvent: MutableState<NewDiaryStepTwoUiEvent> = mutableStateOf(NewDiaryStepTwoUiEvent.CreateNewDiary)
        private set

    init {
        getMoodIdArgument()
    }

    fun setDescription(description: String) {
        this.description.value = description
    }

    private fun getMoodIdArgument() {
        moodId.intValue = savedStateHandle.get<Int>(key = "moodId")!!
    }

    fun addNewDiary(
        diary: Diary,
        navigateBack: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.Main) {
            uiEvent.value = NewDiaryStepTwoUiEvent.Loading
            val result = MongoDB.addDiary(diary = diary)
            if (result is RequestState.Success) {
                navigateBack()
            } else if (result is RequestState.Error){
                onError(result.error.message.toString())
            }
        }
    }
}