package com.example.jetsnackme.screen.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.jetsnackme.R
import com.example.jetsnackme.components.SortItem
import com.example.jetsnackme.data.snacks
import com.example.jetsnackme.ui.theme.JetsnackMeTheme
import dagger.hilt.android.lifecycle.HiltViewModel


class HomeScreenViewModel : ViewModel(){
    val picksSnacks = snacks.subList(0,13)
    val popularSnacks = snacks.subList(14,20)
    private val _isFilterShow = mutableStateOf(false)


    private val _filterSortItemResponse = mutableStateOf<SortItem>(
        SortItem(R.drawable.ic_android,R.string.sort1)
    )
    private val _maxCaloriesResponse = mutableStateOf(0f)

    val isFilterShow
        get() = _isFilterShow
    val filterSortItemResponse: SortItem
        get() = _filterSortItemResponse.value

    val maxCaloriesResponse: MutableState<Float>
        get() = _maxCaloriesResponse

    fun onSelectedFilterSortItemResponse(sortItem: SortItem){
        _filterSortItemResponse.value = sortItem
    }

    fun onMaxCaloriesResponse(maxCalories:Float){
        _maxCaloriesResponse.value = maxCalories
    }

    fun onFilterClose(){
        _isFilterShow.value = false
    }


}