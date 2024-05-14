package com.example.jetsnackme.screen.SnackDetail

import android.health.connect.datatypes.units.Velocity
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.jetsnackme.R
import com.example.jetsnackme.model.Snack
import com.example.jetsnackme.navigation.JetsnackScreens
import com.example.jetsnackme.screen.home.SectionBanner
import com.example.jetsnackme.screen.home.SnackCardRow
import com.example.jetsnackme.ui.theme.ChangeStatusBarColor
import com.example.jetsnackme.ui.theme.JetsnackMeTheme
import com.example.jetsnackme.ui.theme.Ocean3
import com.example.jetsnackme.ui.theme.Shadow3
import com.example.jetsnackme.ui.theme.Shapes
import com.example.jetsnackme.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SnackDetailScreen(navController: NavHostController,
                      modifier: Modifier = Modifier,
                      snackId: String?,
                      viewModel: SnackDetailScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    Log.d("snack id from detail",snackId.toString())
    Log.d("current route",navController.currentBackStackEntry?.destination?.route.toString())
    val snack = viewModel.getSnackById(snackId)
    val scrollState = rememberScrollState()
    val lazyListState = rememberLazyListState()
    ChangeStatusBarColor(Color.Transparent)
    val backgroundColorMaxHeight = 300.dp
    val backgroundColorMinHeight = 70.dp
    var backgroundColorHeight by remember{ mutableStateOf(backgroundColorMaxHeight) }
    val backgroundWhiteMaxHeight = 200.dp
    val backgroundWhiteMinHeight = 0.dp
    var backgroundWhiteHeight by remember { mutableStateOf(backgroundWhiteMaxHeight) }
    val canScroll = backgroundColorHeight == backgroundColorMinHeight && backgroundWhiteHeight == backgroundWhiteMinHeight
    val canScrollState = remember {
        mutableStateOf(canScroll)
    }

    var scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val density = LocalDensity.current
    val nestedScrollConnectionCustom = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                fun deltaDp() = with(density) { available.y.toDp() }
                // Handle scroll up
                if (available.y < 0 && backgroundColorHeight > backgroundColorMinHeight) {
                    backgroundColorHeight = max(backgroundColorMinHeight, backgroundColorHeight + deltaDp())
                }
                if (backgroundColorHeight == backgroundColorMinHeight && available.y < 0 && backgroundWhiteHeight > backgroundWhiteMinHeight){
                    backgroundWhiteHeight = max(backgroundWhiteMinHeight, backgroundWhiteHeight+ deltaDp())
                }
                if (available.y<0 && canScrollState.value){

                }
                // Handle scroll down
                if (available.y > 0 && backgroundColorHeight < backgroundColorMaxHeight) {
                    backgroundColorHeight = min(backgroundColorMaxHeight, backgroundColorHeight + deltaDp())
                }
                if (backgroundColorHeight == backgroundColorMaxHeight && available.y > 0 && backgroundWhiteHeight < backgroundWhiteMaxHeight){
                    backgroundWhiteHeight = min(backgroundWhiteMaxHeight, backgroundWhiteHeight + deltaDp())
                }


                return Offset.Zero
            }
        }
    }

    val nestedScrollConnectionContent = remember {
        object:NestedScrollConnection{
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset =
                if (canScrollState.value) Offset.Zero else available
            override suspend fun onPreFling(available: androidx.compose.ui.unit.Velocity): androidx.compose.ui.unit.Velocity =
                if (canScrollState.value) androidx.compose.ui.unit.Velocity.Zero else available
        }
    }
    
    
    Scaffold(
        modifier = Modifier
            //.verticalScroll(scrollState)
            .nestedScroll(nestedScrollConnectionCustom),
        topBar = {
            Box(modifier = modifier){
                Column {
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .background(brush = Brush.horizontalGradient(JetsnackMeTheme.colors.tornado1))
                            .height(backgroundColorHeight)
                    ) {
                        TopAppBar(title = { },
                            modifier = modifier.background(brush = Brush.horizontalGradient(JetsnackMeTheme.colors.tornado1)),
                            navigationIcon = {
                                DetailBackIconButton()
                            },
                            windowInsets = WindowInsets(0.dp),
                            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                            //scrollBehavior = scrollBehavior
                        )
                    }
                    Box(modifier = modifier.height(backgroundWhiteHeight))
                    SnackNameAndPriceRow(snack = snack)
                }
                //Asyncimage
            }

            },
        bottomBar = {
            AddToCartRow()
        }

    ) {paddingValues ->

        LazyColumn(
            modifier
                .padding(paddingValues)
                .nestedScroll(nestedScrollConnectionCustom)
                ) {
                item{
                    SnackDetailDetails()
                }
                item {
                    SectionBanner(label = R.string.banner6)
//                    SnackCardRow(
//                        cardBackgroundColorCollectionPicks = ,
//                        snacks = ,
//                        colorCount =
//                    ) {snackId->
//                        navController.navigate(JetsnackScreens.SnackDetailScreen.name + "/$snackId")
//                    }
                }



        }
    }



//    Column(
//        modifier = modifier.verticalScroll(scrollState)
//    ) {
//        ShrinkingCard(snack = snack) {
//            Column {
//
//
//

//            Row(
//                modifier = modifier
//                    .padding(top = 100.dp)
//                    .fillMaxWidth(),
//                horizontalArrangement = Arrangement.Center
//            ) {
//                AsyncImage(
//                    model = ImageRequest.Builder(LocalContext.current)
//                        .data(snack.imageUrl)
//                        .crossfade(true)
//                        .build(),
//                    contentDescription = "${snack.name} image",
//                    modifier = modifier
//                        .clip(CircleShape)
//                        .size(112.dp)
//                        .wrapContentSize(),
//                    contentScale = ContentScale.Crop)
//            }
//            }
//
//        }
//
//    }
//
//










}

@Composable
fun AddToCartRow(
    modifier: Modifier = Modifier
){
    var count:Int by remember {
        mutableStateOf(1)
    }
    val brushedIconModifier = Modifier
        .graphicsLayer(alpha = 0.9f)
        .drawWithCache {
            onDrawWithContent {
                drawContent()
                drawCircle(
                    brush = Brush.horizontalGradient(listOf(Ocean3, Shadow3)),
                    blendMode = BlendMode.SrcAtop
                )
            }
        }
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(text = "Qty",
            style = Typography.subtitle1,
            color = JetsnackMeTheme.colors.textSecondary)
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { if (count == 1) count = 1 else count-- }) {
                    Icon(
                        modifier = brushedIconModifier.size(60.dp),
                        painter = painterResource(id = R.drawable.ic_removv),
                        contentDescription = "remove icon",
                        )
            }

            Text(text = "$count",
                style = Typography.subtitle1,
                color = JetsnackMeTheme.colors.textSecondary)


            IconButton(onClick = { count++}) {
                Icon(
                    modifier = brushedIconModifier.size(60.dp),
                    painter = painterResource(id = R.drawable.ic_add_circle),
                    contentDescription = "add icon")
            }

        }
        Button(onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            modifier = modifier
                .background(
                    Brush.horizontalGradient(JetsnackMeTheme.colors.gradient2_1),
                    shape = Shapes.medium
                )
                .width(220.dp)
                .height(30.dp)
        ) {
            Text(text = "ADD TO CART",
                style = Typography.button,
                color = JetsnackMeTheme.colors.textInteractive,
                modifier = modifier
                    .padding(horizontal = 5.dp, vertical = 0.dp)
                    .align(Alignment.CenterVertically))


        }

    }
}
@Composable
fun DetailBackIconButton(
    modifier: Modifier = Modifier,
    onClick:()->Unit = {}
){
    IconButton(onClick = onClick,
        ) {
        Box(modifier = modifier
            .size(60.dp)
            .clip(CircleShape)
            .background(color = JetsnackMeTheme.colors.brand.copy(0.5f))){
            Icon(imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                contentDescription = "back icon",
                tint = JetsnackMeTheme.colors.uiBackground,
                modifier = modifier.align(Alignment.Center))
        }

    }
}

@Composable
fun ShrinkingCard(
    modifier: Modifier = Modifier,
    snack: Snack,
    content:@Composable()(BoxScope.() -> Unit)
){
    val density = LocalDensity.current
    val backgroundColorMaxSize = 300.dp
    val backgroundColorMinSize = 100.dp
    var backgroundColorSize by remember{ mutableStateOf(backgroundColorMaxSize) }

    //val imageStartDestination = mapOf(150.dp to )
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                fun deltaDp() = with(density) { available.y.toDp() }
                // Handle scroll up
                if (available.y < 0 && backgroundColorSize > backgroundColorMinSize) {
                    backgroundColorSize = max(backgroundColorMinSize, backgroundColorSize + deltaDp())
                }
                // Handle scroll down
                if (available.y > 0 && backgroundColorSize < backgroundColorMinSize) {
                    backgroundColorSize = min(backgroundColorMinSize, backgroundColorSize + deltaDp())
                }
                return Offset.Zero
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ) {
        Column {
            Box(modifier = modifier
                .fillMaxWidth()
                .height(backgroundColorSize)
                .background(Brush.horizontalGradient(JetsnackMeTheme.colors.tornado1))
                ,
                content = content
            )
            SnackNameAndPriceRow(snack = snack)
        }

    }





}

@Composable
fun SnackNameAndPriceRow(snack: Snack) {
    Column {
        Text(text = snack.name,
            style = Typography.h4,
            color = JetsnackMeTheme.colors.textPrimary)
        Text(text = snack.tagline,
            style = Typography.h5,
            color = JetsnackMeTheme.colors.textSecondary.copy(0.5f))
        Text(text = "$"+snack.price.toString(),
            style = Typography.h5,
            color = JetsnackMeTheme.colors.brand)
    }

}

@Composable
fun SnackDetailDetails(
    modifier: Modifier = Modifier,
){
    var isExpanded by remember {
        mutableStateOf(false)
    }
   Column(modifier = modifier.padding(horizontal = 16.dp),

       ) {
       Text(text = stringResource(id = R.string.details),
           style = Typography.subtitle2,
           color = JetsnackMeTheme.colors.textSecondary.copy(0.8f),
           modifier = modifier.padding(vertical = 8.dp))
       Text(text = stringResource(id = R.string.detailsContent),
           style = Typography.body2,
           color = JetsnackMeTheme.colors.textSecondary.copy(0.5f),
           lineHeight = 28.sp,
           modifier = modifier.padding(vertical = 8.dp),
           maxLines = if (isExpanded) 100 else 5)
       Text(text = if (isExpanded) "SEE LESS" else "SEE MORE",
           style = Typography.button,
           color = JetsnackMeTheme.colors.brand,
           modifier = modifier
               .clickable { isExpanded = !isExpanded }
               .align(Alignment.CenterHorizontally))
       Spacer(modifier = modifier.height(32.dp))
       Text(text = stringResource(id = R.string.ingredients),
           style = Typography.subtitle2,
           color = JetsnackMeTheme.colors.textSecondary.copy(0.8f),
           modifier = modifier.padding(vertical = 2.dp))
       Text(text = stringResource(id = R.string.ingredientsContents),
           style = Typography.body2,
           color = JetsnackMeTheme.colors.textSecondary.copy(0.5f),
           lineHeight = 28.sp,)
   }
}

@Composable
fun Modifier.detailCardModifier():Modifier{
    val detailCardModifier = Modifier
        .fillMaxWidth()
        .height(300.dp)

    return this then detailCardModifier
}