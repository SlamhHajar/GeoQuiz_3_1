package com.example.geoquiz_3_1

import androidx.annotation.StringRes

data class Question(@StringRes val textResId: Int, val answer: Boolean, var wasAnswered :Boolean)
