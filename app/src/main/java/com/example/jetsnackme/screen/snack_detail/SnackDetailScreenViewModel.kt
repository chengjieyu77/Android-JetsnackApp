package com.example.jetsnackme.screen.snack_detail

import androidx.lifecycle.ViewModel
import com.example.jetsnackme.data.snacks
import com.example.jetsnackme.model.Snack

class SnackDetailScreenViewModel :ViewModel(){
    val picksSnacks = snacks.subList(0,13)
    val popularSnacks = snacks.subList(14,20)
    fun getSnackById(id:String?): Snack {
        if (id == null){
            throw Exception("Id is null!")
        }else{
            return snacks[id.toInt().minus(1)]
        }

    }
}