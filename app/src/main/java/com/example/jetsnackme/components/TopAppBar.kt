package com.example.jetsnackme.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetsnackme.ui.theme.JetsnackMeTheme
import com.example.jetsnackme.ui.theme.Typography

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeliveryToTopBar(){
    TopAppBar(
        title = { Text(text = "Delivery to 1600 Amphitheater Way",
            style = Typography.subtitle1) },
        actions = { IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Outlined.KeyboardArrowDown,
                contentDescription = "arrow down icon")

        }},
        windowInsets = WindowInsets(top = 0.dp)
        )
}

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterTopBar(
    modifier: Modifier = Modifier,
    onNavClick:()->Unit = {},
    onActionClick:()->Unit = {},
){
    CenterAlignedTopAppBar(title = {
        Text(text = "Filters",style = Typography.subtitle1)
    },
        modifier = Modifier.shadow(elevation = 5.dp),
        navigationIcon = {
                         IconButton(onClick = onNavClick) {
                             Icon(imageVector = Icons.Outlined.Close,
                                 contentDescription = "close filter icon")
                         }
        },
        actions = {
            IconButton(onClick = onActionClick) {
                Text(text = "Reset",
                    style = Typography.subtitle2,
                    color = Color.Gray)
            }
        })
}