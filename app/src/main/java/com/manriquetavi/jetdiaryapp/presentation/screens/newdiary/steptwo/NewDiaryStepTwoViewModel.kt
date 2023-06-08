package com.manriquetavi.jetdiaryapp.presentation.screens.newdiary.steptwo

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel


class NewDiaryStepTwoViewModel(
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    var moodId = mutableStateOf(0)
        private set
    var title = mutableStateOf("")
        private set
    var description = mutableStateOf("")
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
}