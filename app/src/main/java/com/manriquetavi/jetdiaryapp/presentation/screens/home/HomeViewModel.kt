package com.manriquetavi.jetdiaryapp.presentation.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manriquetavi.jetdiaryapp.domain.repository.Diaries
import com.manriquetavi.jetdiaryapp.domain.repository.MongoDB
import com.manriquetavi.jetdiaryapp.util.RequestState
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {

    private val _diaries: MutableState<RequestState<Diaries>> = mutableStateOf(RequestState.Idle)
    val diaries: State<RequestState<Diaries>> = _diaries

    init {
        observeAllDiaries()
    }

    private fun observeAllDiaries() {
        viewModelScope.launch {
            MongoDB.getAllDiaries().collect { result ->
                _diaries.value = result
            }
        }
    }
}