package com.example.jetsnackme.screen.search

import androidx.compose.runtime.MutableState

enum class SearchDisplay{
    Categories,Suggestions,Results,NoResults
}

class SearchState(
    isSearchBarFocused:MutableState<Boolean>,
    isSearching:MutableState<Boolean>,
)