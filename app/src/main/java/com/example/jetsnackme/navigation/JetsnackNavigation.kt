package com.example.jetsnackme.navigation

import android.graphics.drawable.Icon
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.jetsnackme.R
import com.example.jetsnackme.model.BottomNav
import com.example.jetsnackme.screen.CartScreen
import com.example.jetsnackme.screen.HomeScreen
import com.example.jetsnackme.screen.ProfileScreen
import com.example.jetsnackme.screen.SearchScreen
import com.example.jetsnackme.ui.theme.JetsnackColors
import com.example.jetsnackme.ui.theme.JetsnackMeTheme
import com.example.jetsnackme.ui.theme.ProvideJetsnackColors
import com.example.jetsnackme.ui.theme.Shapes
import com.example.jetsnackme.ui.theme.Typography
import kotlinx.coroutines.selects.select

@Composable
fun JetsnackNavigation(){
    val navController = rememberNavController()
    val items = listOf(
        JetsnackScreens.HomeScreen,
        JetsnackScreens.SearchScreen,
        JetsnackScreens.CartScreen,
        JetsnackScreens.ProfileScreen
    )
    val screensAndIcon = mapOf<JetsnackScreens,BottomNav>(
        JetsnackScreens.HomeScreen to BottomNav(icon = Icons.Outlined.Home, R.string.bottomNav1),
        JetsnackScreens.SearchScreen to BottomNav(Icons.Outlined.Search, R.string.bottomNav2),
        JetsnackScreens.CartScreen to BottomNav(Icons.Outlined.ShoppingCart,R.string.bottomNav3),
        JetsnackScreens.ProfileScreen to BottomNav(Icons.Outlined.AccountCircle,R.string.bottomNav4)
    )

    val navBottomItemWidthMin = 110.dp
    val navBottomItemWidthMax = 130.dp
    val selectedNavModifierOuter = Modifier
        .requiredWidthIn( navBottomItemWidthMin,navBottomItemWidthMax)
        .padding(horizontal = 10.dp)
        .background(color = JetsnackMeTheme.colors.brand, shape = Shapes.small)


    val selectedNavModifierInner = Modifier
        .requiredWidthIn(navBottomItemWidthMin,navBottomItemWidthMax)
        .requiredHeight(40.dp)
        .border(
            width = 2.dp, color = JetsnackMeTheme.colors.iconInteractive, shape = Shapes.small
        )
    val unSelectedNavModifier = Modifier



    Scaffold(
        bottomBar = {
            BottomNavigation(
               // modifier = Modifier.navigationBarsPadding(),
                backgroundColor = JetsnackMeTheme.colors.brand
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                screensAndIcon.forEach { (screen,bottomNav) ->
                    val selected = currentDestination?.hierarchy?.any { it.route == screen.name } == true

                    BottomNavigationItem(
                            selected = selected,
                            icon = {
                                if (selected){
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                            .offset(when(screen.name){
                                                "HomeScreen" -> 10.dp
                                                "ProfileScreen" -> -10.dp
                                                else -> 0.dp
                                            } )
                                            .then(selectedNavModifierInner),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center) {
                                        Icon(bottomNav.icon,
                                            contentDescription = null,
                                            tint = JetsnackMeTheme.colors.iconInteractive,
                                            modifier = Modifier.padding(horizontal = 2.dp)
                                        )
                                        Text(text = stringResource(bottomNav.label),
                                            style = Typography.overline,
                                            color = JetsnackMeTheme.colors.iconInteractive,
                                        )
                                    }


                                }else{
                                    Icon(bottomNav.icon,
                                        contentDescription = null,
                                        tint = JetsnackMeTheme.colors.iconInteractiveInactive)
                                }

                            },

                            modifier = Modifier
                                .navigationBarsPadding()
                                .then(if (selected) selectedNavModifierOuter else unSelectedNavModifier),
                            //.width(120.dp)
                            //.padding(horizontal = 2.dp),
                            alwaysShowLabel = false,
                            selectedContentColor = JetsnackMeTheme.colors.iconInteractive,
                            unselectedContentColor = JetsnackMeTheme.colors.iconInteractiveInactive,

                            onClick = {
                                navController.navigate(screen.name) {
                                    // Pop up to the start destination of the graph to
                                    // avoid building up a large stack of destinations
                                    // on the back stack as users select items
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    // Avoid multiple copies of the same destination when
                                    // reselecting the same item
                                    launchSingleTop = true
                                    // Restore state when reselecting a previously selected item
                                    restoreState = true
                                }
                            }
                        )
                    }

                }

        }
    ) { innerPadding ->
        NavHost(navController, startDestination = JetsnackScreens.HomeScreen.name, Modifier.padding(innerPadding)) {
            composable(JetsnackScreens.HomeScreen.name) {
                HomeScreen(navController = navController)
            }
            composable(JetsnackScreens.SearchScreen.name) {
                SearchScreen(navController = navController)
            }
            composable(JetsnackScreens.CartScreen.name) {
                CartScreen(navController = navController)
            }
            composable(JetsnackScreens.ProfileScreen.name) {
                ProfileScreen(navController = navController)
            }
           
        }
    }

}

@Preview
@Composable
fun BottomNav(){
    val selectedNavModifier = Modifier
        .padding(start = 2.dp)
        .height(30.dp)
        .clip(Shapes.medium)
        .border(
            width = 1.dp, color = Color.White, shape = Shapes.medium
        )
    Row(modifier = selectedNavModifier
        .width(100.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center) {
        Icon(Icons.Outlined.Home,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.padding(vertical = 2.dp)
        )
        Text("Home",
            modifier = Modifier.padding(end = 0.dp))
    }
}