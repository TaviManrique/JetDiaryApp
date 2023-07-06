package com.manriquetavi.jetdiaryapp.navigation

sealed class Screen(val route: String) {
    object Authentication: Screen(route = "authentication_screen")
    object Home: Screen(route = "home_screen")
    object Update: Screen(route = "update_screen?diaryId={diaryId}") {
        fun passDiaryId(diaryId: String) = "update_screen?diaryId=$diaryId"
    }
    object NewDiary: Screen(route = "new_diary_screen")
    object NewDiaryStepTwo: Screen(route = "new_diary_step_two_screen?moodId={moodId}") {
        fun passMoodId(moodId: Int) = "new_diary_step_two_screen?moodId=$moodId"
    }

}
