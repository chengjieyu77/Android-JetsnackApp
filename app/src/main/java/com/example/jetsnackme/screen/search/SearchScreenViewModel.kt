package com.example.jetsnackme.screen.search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import com.example.jetsnackme.data.DataSource
import com.example.jetsnackme.model.Snack
import com.example.jetsnackme.repository.SearchRepo
import java.util.Locale

class SearchScreenViewModel:ViewModel(){
    private val allSnacks = DataSource.getSnacks()
    val filterItems = DataSource.getFilterItems()
    private val _searchInput = mutableStateOf("")
    private var _searchedItems = mutableSetOf<Snack>()
    private var _searchedItemsUsingLaunchedEffect = mutableListOf<Snack>()
    val searchInput: MutableState<String>
        get() = _searchInput
    val searchedItems:MutableSet<Snack>
        get() = _searchedItems

    val searchedItemsUsingLaunchedEffect : MutableList<Snack>
        get() = _searchedItemsUsingLaunchedEffect
    fun onSearchItems(searchItem:String){
//        allSnacks.filter { snack->
//            snack.name.lowercase(Locale.ROOT).contains(searchItem.lowercase())
//        }
        for (snack in searchedItems){
            if (snack.name.lowercase().contains(searchItem.lowercase())){
                //_searchedItems= _searchedItems.clear()
                _searchedItems.add(snack)

            }else{
                _searchedItems.remove(snack)
            }
        }


    }

    suspend fun onSearch(query:String){
        _searchedItemsUsingLaunchedEffect = SearchRepo.search(query).toMutableList()
    }

}