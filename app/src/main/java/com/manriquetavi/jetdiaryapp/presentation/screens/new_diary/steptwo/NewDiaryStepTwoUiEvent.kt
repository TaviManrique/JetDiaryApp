package com.manriquetavi.jetdiaryapp.presentation.screens.new_diary.steptwo

sealed class NewDiaryStepTwoUiEvent {
    object CreateNewDiary: NewDiaryStepTwoUiEvent()
    object Loading: NewDiaryStepTwoUiEvent()
}
