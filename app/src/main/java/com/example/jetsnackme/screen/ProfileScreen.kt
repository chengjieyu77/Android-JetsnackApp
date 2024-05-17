package com.example.jetsnackme.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.jetsnackme.ui.theme.Typography

@Composable
fun ProfileScreen(navController: NavController){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Gray
    ) {
        Text(text = "This is profile",
            style = Typography.body1)
    }

}