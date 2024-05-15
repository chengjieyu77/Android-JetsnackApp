package com.example.jetsnackme.screen.search

import androidx.lifecycle.ViewModel
import com.example.jetsnackme.data.DataSource

class SearchScreenViewModel:ViewModel(){
    val allSnacks = DataSource.getSnacks()
    val filterItems = DataSource.getFilterItems()

}