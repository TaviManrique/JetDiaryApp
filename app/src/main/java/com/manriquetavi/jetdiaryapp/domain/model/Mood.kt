package com.manriquetavi.jetdiaryapp.domain.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.manriquetavi.jetdiaryapp.R
import com.manriquetavi.jetdiaryapp.ui.theme.*

enum class Mood(
    @DrawableRes
    val icon: Int,
    val contentColor: Color
) {
    Angry(
        icon = R.drawable.angry,
        contentColor = AngryColor
    ),
    Crying(
        icon = R.drawable.crying,
        contentColor = CryingColor
    ),
    Embarrassed(
        icon = R.drawable.embarrassed,
        contentColor = EmbarrassedColor
    ),
    Happy(
        icon = R.drawable.happy,
        contentColor = HappyColor
    ),
    Hungry(
        icon = R.drawable.hungry,
        contentColor = HungryColor
    ),
    Laughing(
        icon = R.drawable.laughing,
        contentColor = LaughingColor
    ),
    Love(
        icon = R.drawable.love,
        contentColor = LoveColor
    ),
    Neutral(
        icon = R.drawable.neutral,
        contentColor = NeutralColor
    ),
    Sad(
        icon = R.drawable.sad,
        contentColor = SadColor
    ),
    Sarcastic(
        icon = R.drawable.sarcastic,
        contentColor = SarcasticColor
    ),
    Sick(
        icon = R.drawable.sick,
        contentColor = SickColor
    ),
    Sleep(
        icon = R.drawable.sleep,
        contentColor = SleepColor
    ),
    Smile(
        icon = R.drawable.smile,
        contentColor = SmileColor
    ),
    Thinking(
        icon = R.drawable.thinking,
        contentColor = ThinkingColor
    ),
    Wow(
        icon = R.drawable.wow,
        contentColor = WowColor
    )
}