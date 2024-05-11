package com.example.jetsnackme.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.jetsnackme.ui.theme.Typography

@Composable
fun ProfileScreen(navController: NavController){
    Text(text = "This is profile",
        style = Typography.body1)
}