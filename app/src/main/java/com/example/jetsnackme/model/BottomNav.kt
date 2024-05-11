package com.example.jetsnackme.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNav(
    val icon:ImageVector,
    @StringRes val label:Int
)
