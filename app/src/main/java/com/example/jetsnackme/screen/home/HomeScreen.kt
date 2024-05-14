package com.example.jetsnackme.screen.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.jetsnackme.R
import com.example.jetsnackme.components.DeliveryToTopBar
import com.example.jetsnackme.components.FilterDialog
import com.example.jetsnackme.model.Snack
import com.example.jetsnackme.ui.theme.JetsnackMeTheme
import com.example.jetsnackme.ui.theme.Lavender3
import com.example.jetsnackme.ui.theme.Rose2
import com.example.jetsnackme.ui.theme.Rose3
import com.example.jetsnackme.ui.theme.Rose4
import com.example.jetsnackme.ui.theme.Rose8
import com.example.jetsnackme.ui.theme.Shapes
import com.example.jetsnackme.ui.theme.Typography

@Composable
fun HomeScreen(navController: NavController,
               modifier: Modifier = Modifier,
               viewModel: HomeScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
               navigateToSnackDetailScreen:(id:String)->Unit){
    val scrollState = rememberScrollState()
    val filterItems = listOf(
        R.string.filter1,
        R.string.filter2,
        R.string.filter3,
        R.string.filter4,
        R.string.filter5
    )

    val cardBackgroundColorCollectionPicks = listOf(
            JetsnackMeTheme.colors.tornado1,
            JetsnackMeTheme.colors.gradient2_2,
            JetsnackMeTheme.colors.gradient2_2.reversed()

        )

    val cardBackgroundColorCollectionFavorites = listOf(
        listOf(Rose8,Rose4),
        listOf(Rose4,Lavender3),
        listOf(Lavender3,Rose3),
        listOf(Rose3,Rose2),
        listOf(Rose2,Rose3),
        JetsnackMeTheme.colors.gradient2_3,
        JetsnackMeTheme.colors.gradient2_3.reversed()

    )

   Scaffold(
       topBar = { DeliveryToTopBar() },
       contentWindowInsets = WindowInsets(top = 0.dp)
   ) {contentPadding->
        Column(modifier = Modifier
            .padding(top = contentPadding.calculateTopPadding())
            .verticalScroll(scrollState)) {
            FilterRow(filterItems = filterItems,
                isFilterShow = viewModel.isFilterShow)

            SectionBanner(label = R.string.banner1)
            SnackCardRow(
                modifier = modifier,
                cardBackgroundColorCollectionPicks = cardBackgroundColorCollectionPicks,
                snacks = viewModel.picksSnacks,
                colorCount =cardBackgroundColorCollectionPicks.size,
                onClickToDetail = navigateToSnackDetailScreen)
            Divider()

            SectionBanner(label = R.string.banner2)
            SnackRoundedRow(snacks = viewModel.popularSnacks)
            Divider()

            SectionBanner(label = R.string.banner3)
            SnackCardRow(
                modifier = modifier,
                cardBackgroundColorCollectionPicks = cardBackgroundColorCollectionFavorites,
                snacks = viewModel.picksSnacks,
                colorCount =cardBackgroundColorCollectionFavorites.size,
                onClickToDetail = navigateToSnackDetailScreen)
            Divider()

            SectionBanner(label = R.string.banner4)
            SnackRoundedRow(snacks = viewModel.popularSnacks)
            Divider()

            SectionBanner(label = R.string.banner5)
            SnackCardRow(
                modifier = modifier,
                cardBackgroundColorCollectionPicks = cardBackgroundColorCollectionPicks,
                snacks = viewModel.picksSnacks,
                colorCount =cardBackgroundColorCollectionPicks.size ,
                onClickToDetail = navigateToSnackDetailScreen)

        }
   }

    if (viewModel.isFilterShow.value){
        FilterDialog(
            selectedSortItem = viewModel.filterSortItemResponse,
            onFilterSortItemSelect = viewModel::onSelectedFilterSortItemResponse,
            maxCalories = viewModel.maxCaloriesResponse,
        ) {
            viewModel.onFilterClose()
            Log.d("","")
        }
    }
}

@Composable
fun SnackRoundedRow(snacks:List<Snack>,
                    modifier: Modifier = Modifier){
    LazyRow(
        modifier = modifier.padding(horizontal = 16.dp,vertical = 8.dp)
    ) {
        items(snacks){snack->
            SnackRoundedItem(snack = snack)
            Spacer(modifier = modifier.width(16.dp))
        }
    }
}

@Composable
fun SnackRoundedItem(modifier: Modifier = Modifier,
                     snack: Snack){
    Column(modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(snack.imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = "${snack.name} image",
            modifier = modifier
                .clip(CircleShape)
                .size(112.dp)
                .wrapContentSize()
                .shadow(elevation = 10.dp, shape = CircleShape),
            contentScale = ContentScale.Crop)
        Text(text = snack.name,
            style = Typography.subtitle1,
            color = JetsnackMeTheme.colors.textSecondary,
            modifier = modifier.padding(8.dp))
    }
}

@Composable
fun SnackCardRow(
    modifier: Modifier = Modifier,
    cardBackgroundColorCollectionPicks:List<List<Color>>,
    snacks:List<Snack>,
    colorCount:Int,
    onClickToDetail: (id: String) -> Unit
){
    LazyRow(modifier = modifier.padding(top = 8.dp,start = 16.dp,bottom = 16.dp)) {
            items(snacks){snack->
                SnackCardItem(snack = snack,
                    modifier = modifier,
                    cardBackgroundColor = cardBackgroundColorCollectionPicks[snack.id%colorCount],
                    onClickToDetail = onClickToDetail)
                Spacer(modifier = modifier.padding(end = 16.dp))
            }

    }
}
@Composable
fun SnackCardItem(modifier: Modifier = Modifier,
                  cardSizeModifier:Modifier = Modifier.homeCardSizeModifier(),
                  snack: Snack,
                  cardBackgroundColor:List<Color>,
                  cardColoredBackgroundHeight: Dp = 100.dp,
                  onClickToDetail:(id:String)->Unit = {}){


    Box(modifier = cardSizeModifier
        //.background(color = Color.White)
        .clickable { onClickToDetail(snack.id.toString()) }){
            Card(
                modifier = cardSizeModifier
                    .background(color = JetsnackMeTheme.colors.uiBackground)
                    .border(
                        width = 1.dp,
                        color = JetsnackMeTheme.colors.uiBorder,
                        shape = Shapes.medium
                    )
                    .shadow(elevation = 5.dp, shape = Shapes.medium)
            ) {
                Box(modifier = modifier
                    .fillMaxWidth()
                    .height(cardColoredBackgroundHeight)
                    .background(Brush.horizontalGradient(cardBackgroundColor))
                    )

                Box(modifier = modifier
                    .fillMaxWidth()
                    .background(color = JetsnackMeTheme.colors.uiBackground)
                    .weight(1f)
                    .padding(top = 2.dp)){
                    Column(modifier = modifier.padding(16.dp)
                        ) {

                        Spacer(modifier = modifier.height(cardColoredBackgroundHeight / 3))

                        Text(text = snack.name,
                            style = Typography.subtitle1,
                            color = JetsnackMeTheme.colors.textSecondary,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1)
                        Text(text = snack.tagline,
                            style = Typography.caption,
                            color = JetsnackMeTheme.colors.textHelp)
                    }
                }
                

            }
        Row(
            modifier = modifier
                .padding(top = cardColoredBackgroundHeight / 3)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(snack.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "${snack.name} image",
                modifier = modifier
                    .clip(CircleShape)
                    .size(112.dp)
                    .wrapContentSize(),
                contentScale = ContentScale.Crop)
        }


    }
    
}


@Composable
fun SectionBanner(label:Int,
                  modifier: Modifier = Modifier){
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = stringResource(id = label),
            color = JetsnackMeTheme.colors.textPrimary,
            style = Typography.h5,
            modifier = modifier.padding(16.dp))
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
                contentDescription = "forward icon",
                tint = JetsnackMeTheme.colors.iconPrimary)
        }
    }
}


@Composable
fun FilterRow(filterItems:List<Int>,
              modifier: Modifier = Modifier,
              isFilterShow:MutableState<Boolean>){

        LazyRow(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 16.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            item {
                Icon(painterResource(id = R.drawable.ic_filter) ,
                        contentDescription = "icon filter",
                        modifier = Modifier.coloredBorder()
                            .clickable {
                                       isFilterShow.value = true
                            },
                        tint = JetsnackMeTheme.colors.brand)

                Spacer(modifier = modifier.width(16.dp))
            }
            items(filterItems){item->

                FilterRowItem(item = item)
                Spacer(modifier = modifier.width(5.dp))
            }
        }


}

@Composable
fun Modifier.homeCardSizeModifier():Modifier{
    val homeCardSizeModifier = Modifier
        .width(150.dp)
        .height(200.dp)
    return this then homeCardSizeModifier
}

@Composable
fun Modifier.backgroundChangedModifier():Modifier{
    val backgroundChangedModifier = Modifier
        .background(
            color = JetsnackMeTheme.colors.brandSecondary,
            shape = Shapes.medium
        )
        .height(25.dp)

        .border(width = 0.dp, color = JetsnackMeTheme.colors.brandSecondary)

    return this then backgroundChangedModifier
}

@Composable
fun Modifier.coloredBorder():Modifier{
    val coloredBorder = Modifier
        .border(
            width = 2.dp,
            brush = Brush.horizontalGradient(JetsnackMeTheme.colors.gradient2_2),
            shape = CircleShape
        )
        .height(25.dp)

    return this then coloredBorder
}

@Composable
fun FilterRowItem(modifier: Modifier = Modifier,
                  item:Int,
                  ){
    var selected by rememberSaveable {
        mutableStateOf(false)
    }
    Row(
        modifier = modifier
            .clickable { selected = !selected }
            .then(if (selected) Modifier.backgroundChangedModifier() else Modifier.coloredBorder())
    ) {
        Text(text = stringResource(id =item ),
            style = Typography.overline,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp))
    }
}


