package com.example.jetsnackme.model

data class Snack(
    val id:Int,
    val name:String,
    val price:Long,
    //val details:String,
    val imageUrl:String,
    val tagline:String = ""
)
