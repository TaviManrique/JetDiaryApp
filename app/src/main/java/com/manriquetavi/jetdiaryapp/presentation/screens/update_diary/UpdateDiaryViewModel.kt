package com.manriquetavi.jetdiaryapp.presentation.screens.update_diary

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manriquetavi.jetdiaryapp.domain.model.Diary
import com.manriquetavi.jetdiaryapp.domain.repository.MongoDB
import com.manriquetavi.jetdiaryapp.util.RequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId

class UpdateDiaryViewModel(
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _diary: MutableStateFlow<RequestState<Diary>> = MutableStateFlow(RequestState.Idle)
    val diary: StateFlow<RequestState<Diary>> = _diary.asStateFlow()

    var description = mutableStateOf("")
        private set

    init {
        getDiary()
    }

    private fun getDiary() {
        _diary.value = RequestState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val diaryId  = ObjectId.invoke(savedStateHandle.get<String>(key = "diaryId")!!)
            MongoDB.getDiary(diaryId = diaryId).collect {
                _diary.value = it
            }
        }
    }

    fun setDescription(description: String) {
        this.description.value = description
    }

}