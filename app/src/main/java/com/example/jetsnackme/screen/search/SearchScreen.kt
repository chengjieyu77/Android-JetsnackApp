package com.example.jetsnackme.screen.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.jetsnackme.R
import com.example.jetsnackme.components.SearchTopBar
import com.example.jetsnackme.data.DataSource
import com.example.jetsnackme.data.searchCategories
import com.example.jetsnackme.data.searchLifestyles
import com.example.jetsnackme.model.SearchCategory
import com.example.jetsnackme.model.Snack
import com.example.jetsnackme.screen.home.FilterRow
import com.example.jetsnackme.ui.theme.JetsnackMeTheme
import com.example.jetsnackme.ui.theme.Shapes
import com.example.jetsnackme.ui.theme.Typography

@Composable
fun SearchScreen(navController: NavController,
                 modifier: Modifier = Modifier,
                 viewModel: SearchScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()){
    val searchInput = rememberSaveable {
        mutableStateOf("")
    }
    val isSearchBarFocused = rememberSaveable {
        mutableStateOf(false)
    }
    val isSearching = rememberSaveable {
        mutableStateOf(false)
    }
    val isFilterShown = rememberSaveable {
        mutableStateOf(false)
    }
    Scaffold(
        topBar = { SearchTopBar(searchInput = searchInput,
            isSearchBarFocused = isSearchBarFocused){
            isSearching.value = false
            searchInput.value = ""
        }}
    ) {
        LazyColumn(modifier = modifier.padding(it)) {
            if (!isSearchBarFocused.value){
                item {
                    SearchBannerLetter(label = R.string.searchBanner1)
                    SearchCardItemGroup(searchTopicList = searchCategories,
                        backgroundColor = JetsnackMeTheme.colors.gradient2_2)
                    SearchBannerLetter(label = R.string.searchBanner2)
                    SearchCardItemGroup(searchTopicList = searchLifestyles,
                        backgroundColor = JetsnackMeTheme.colors.gradient2_3)
                }
            }

            if (isSearchBarFocused.value && !isSearching.value){
                item {
                    SearchesList(recentSearches = DataSource.getRecentSearches(),
                        popularSearches = DataSource.getPopularSearches()){
                        searchInput.value = it
                        isSearching.value = true
                        //go on search this value
                    }
                }
            }

            if (isSearchBarFocused.value && isSearching.value){
                item {
                    FilterRow(filterItems =viewModel.filterItems ,
                        isFilterShow = isFilterShown)
                }
            }


        }
    }
}

@Composable
fun SearchingItem(snack: Snack,
                  modifier: Modifier = Modifier){
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Box(modifier = modifier){
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(snack.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription =  "${snack.name} image",
                modifier = modifier
                    .clip(CircleShape)
                    .requiredSize(120.dp),
                //.wrapContentSize(),
                contentScale = ContentScale.Crop)
        }
        Column {
            Text(text = snack.name,
                style = Typography.subtitle2)
        }
    }
}
@Composable
fun SearchesList(recentSearches:List<String>,
                 popularSearches:List<String>,
                 modifier: Modifier = Modifier,
                 onSearchesClicked: (String) -> Unit){
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        SearchBannerLetter(label = R.string.searches1)
        recentSearches.forEach {
            SearchListItem(item = it,modifier = modifier, onSearchesClicked = onSearchesClicked)
        }
        SearchBannerLetter(label = R.string.searches2)
        popularSearches.forEach {
            SearchListItem(item = it,modifier = modifier, onSearchesClicked = onSearchesClicked)
        }
    }
}

@Composable
fun SearchListItem(
    modifier: Modifier = Modifier,
    item:String,
    onSearchesClicked:(String)->Unit){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onSearchesClicked(item) }
    ) {
        Text(text = item,
            style = Typography.subtitle1,
            color = JetsnackMeTheme.colors.textSecondary,
            modifier = modifier.padding(16.dp))
    }

}
@Composable
fun SearchBannerLetter(label:Int,
                       modifier: Modifier = Modifier){
    Text(text = stringResource(id = label),
        color = JetsnackMeTheme.colors.textPrimary,
        style = Typography.h5,
        modifier = modifier.padding( 16.dp))
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchCardItemGroup(
    modifier: Modifier = Modifier,
    searchTopicList:List<SearchCategory>,
    backgroundColor: List<Color>){
    FlowRow(
        modifier = modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        maxItemsInEachRow = 2
    ) {
        searchTopicList.forEach{ searchCategory->
            SearchCardItem(searchCategory = searchCategory,
                backgroundColor = backgroundColor)
        }
    }
}
@Composable
fun SearchCardItem(searchCategory: SearchCategory,
                   modifier: Modifier = Modifier,
                   backgroundColor:List<Color>){
    Card(
        modifier = modifier
            .padding(vertical = 8.dp)
            .height(120.dp)
            .width(180.dp)
            .clickable { }
    ) {
        Row(
            modifier = modifier
                .weight(1f)
                .background(Brush.horizontalGradient(backgroundColor), shape = Shapes.medium),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(id = searchCategory.label),
                style = Typography.subtitle1,
                color = JetsnackMeTheme.colors.textSecondary,
                modifier = modifier
                    .fillMaxWidth(0.45f)
                    .padding(start = 8.dp))

            Box(modifier = modifier.offset(x=20.dp)){
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(searchCategory.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = stringResource(id = searchCategory.label) +" image",
                    modifier = modifier
                        .clip(CircleShape)
                        .requiredSize(120.dp),
                    //.wrapContentSize(),
                    contentScale = ContentScale.Crop)
            }


        }
    }
}