package com.example.jetsnackme.model

import androidx.annotation.StringRes

data class SearchCategory(
    @StringRes val label:Int,
    val imageUrl:String
)
