package com.example.jetsnackme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.example.jetsnackme.navigation.JetsnackNavigation
import com.example.jetsnackme.navigation.JetsnackScreens
import com.example.jetsnackme.ui.theme.JetsnackMeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        WindowCompat.setDecorFitsSystemWindows(window,false)
        setContent {
            JetsnackApp()
        }
    }
}

@Composable
fun JetsnackApp(){
    JetsnackMeTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = JetsnackMeTheme.colors.brand
        ) {
            JetsnackNavigation()
        }
    }
}

