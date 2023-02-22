package com.manriquetavi.jetdiaryapp.navigation

sealed class Screen(val route: String) {
    object Authentication: Screen(route = "authentication_screen")
    object Home: Screen(route = "home_screen")
    object Write: Screen(route = "write_screen?diaryId={diaryId}") {
        fun passDiaryId(diaryId: String) = "write_scree?diaryId=$diaryId"
    }

}
