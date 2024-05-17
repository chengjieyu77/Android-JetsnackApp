package com.example.jetsnackme.repository

import com.example.jetsnackme.data.snacks
import com.example.jetsnackme.model.Snack
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

object SearchRepo{
    suspend fun search(query:String):List<Snack> = withContext(Dispatchers.Default){
        delay(200L)
        snacks.filter { it.name.contains(query, ignoreCase = true) }
    }
}