package com.example.jetsnackme.screen.search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import com.example.jetsnackme.data.DataSource
import com.example.jetsnackme.model.Snack
import java.util.Locale

class SearchScreenViewModel:ViewModel(){
    val allSnacks = DataSource.getSnacks()
    val filterItems = DataSource.getFilterItems()
    private val _searchInput = mutableStateOf("")
    private var _searchedItems = mutableSetOf<Snack>()
    val searchInput: MutableState<String>
        get() = _searchInput
    val searchedItems:MutableSet<Snack>
        get() = _searchedItems
    fun onSearchItems(searchItem:String){
//         = allSnacks.filter { snack->
//            snack.name.lowercase(Locale.ROOT).contains(searchItem.lowercase())
//        }

        for (snack in allSnacks){
            if (snack.name.lowercase().contains(searchItem.lowercase())){
                //_searchedItems= _searchedItems.clear()

                _searchedItems.add(snack)

            }else{
                _searchedItems.remove(snack)
            }
        }


    }

}