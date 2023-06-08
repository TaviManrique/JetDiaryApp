package com.manriquetavi.jetdiaryapp.presentation.screens.newdiary.steptwo

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manriquetavi.jetdiaryapp.domain.model.Diary
import com.manriquetavi.jetdiaryapp.domain.repository.MongoDB
import com.manriquetavi.jetdiaryapp.util.RequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class NewDiaryStepTwoViewModel(
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    var moodId = mutableStateOf(0)
        private set
    var title = mutableStateOf("")
        private set
    var description = mutableStateOf("")
        private set
    var resultAddDiary = mutableStateOf<RequestState<Diary>>(RequestState.Idle)
        private set

    init {
        getMoodIdArgument()
    }

    fun setTitle(title: String) {
        this.title.value = title
    }

    fun setDescription(description: String) {
        this.description.value = description
    }

    private fun getMoodIdArgument() {
        moodId.value = savedStateHandle.get<Int>(key = "moodId")!!
    }

    fun addNewDiary(
        diary: Diary
    ) {
        resultAddDiary.value = RequestState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            resultAddDiary.value = MongoDB.addDiary(diary)
        }

    }
}