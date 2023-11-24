package com.manriquetavi.jetdiaryapp.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manriquetavi.jetdiaryapp.domain.repository.Diaries
import com.manriquetavi.jetdiaryapp.domain.repository.MongoDB
import com.manriquetavi.jetdiaryapp.util.RequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {

    private val _diaries: MutableStateFlow<RequestState<Diaries>> = MutableStateFlow(RequestState.Loading)
    val diaries: StateFlow<RequestState<Diaries>> = _diaries.asStateFlow()

    init {
        getAllDiaries()
    }

    private fun getAllDiaries() {
        _diaries.value = RequestState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            MongoDB.getAllDiaries().collect { result ->
                _diaries.value = result
            }
        }
    }
}