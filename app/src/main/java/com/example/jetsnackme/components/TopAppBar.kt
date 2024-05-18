package com.example.jetsnackme.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetsnackme.ui.theme.JetsnackMeTheme
import com.example.jetsnackme.ui.theme.Neutral1
import com.example.jetsnackme.ui.theme.Neutral3
import com.example.jetsnackme.ui.theme.Shapes
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
        windowInsets = WindowInsets(top = 0.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent
        )
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



@Composable
fun SearchTopBar(
    searchInput:MutableState<String>,
    modifier: Modifier = Modifier,
    imeAction:ImeAction = ImeAction.Search,
    onAction:()->Unit,
    isSearchBarFocused:MutableState<Boolean>,
    isSearchingByTextField:MutableState<Boolean>,
    onBackIconClicked:()->Unit = {}
){
    Surface(
        modifier = modifier
            .background(color = JetsnackMeTheme.colors.uiBackground)
            .fillMaxWidth()
           ,
        shadowElevation = 5.dp
    ) {
        TextField(value = searchInput.value,
            onValueChange = {searchInput.value = it},
            modifier = modifier
                .height(80.dp)
                .padding(horizontal = 16.dp, vertical = 14.dp)
                .onFocusChanged { focusState ->
                    if (isSearchBarFocused.value != focusState.isFocused) {
                        isSearchBarFocused.value = focusState.isFocused
                    }
                },
            placeholder = { SearchTopBarPlaceholder()},
            leadingIcon = {
                          if (isSearchBarFocused.value){
                              IconButton(onClick = {onBackIconClicked.invoke()}) {
                                  Icon(imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                                      contentDescription ="back icon",
                                      tint = JetsnackMeTheme.colors.brand)
                              }

                          }
            },
            trailingIcon = {
                           if (isSearchingByTextField.value){
                               CircularProgressIndicator()
                           }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onSearch = {onAction()}
            ),
            singleLine = true,
            shape = RoundedCornerShape(50.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.LightGray.copy(0.3f),
                focusedContainerColor = Color.LightGray.copy(0.3f),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                cursorColor = Color.Gray
            )
        )
    }






}

@Composable
fun SearchTopBarPlaceholder(
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Outlined.Search,
            contentDescription = "search icon")
        Text(text = "Search Jetsnack",
            modifier = modifier.padding(start = 8.dp)
            )
    }
}

