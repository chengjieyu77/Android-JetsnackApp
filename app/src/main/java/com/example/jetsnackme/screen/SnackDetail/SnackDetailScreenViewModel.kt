package com.example.jetsnackme.screen.SnackDetail

import androidx.lifecycle.ViewModel
import com.example.jetsnackme.data.snacks
import com.example.jetsnackme.model.Snack

class SnackDetailScreenViewModel :ViewModel(){

    fun getSnackById(id:String?): Snack {
        if (id == null){
            throw Exception("Id is null!")
        }else{
            return snacks[id.toInt().minus(1)]
        }

    }
}