package com.example.jetsnackme.screen

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.jetsnackme.R
import com.example.jetsnackme.components.DeliveryToTopBar
import com.example.jetsnackme.data.snacks
import com.example.jetsnackme.model.Snack
import com.example.jetsnackme.ui.theme.JetsnackMeTheme
import com.example.jetsnackme.ui.theme.Shapes
import com.example.jetsnackme.ui.theme.Typography

@Composable
fun HomeScreen(navController: NavController){
    val scrollState = rememberScrollState()
    val filterItems = listOf(
        R.string.filter1,
        R.string.filter2,
        R.string.filter3,
        R.string.filter4,
        R.string.filter5
    )
    val snacks = snacks
   Scaffold(
       topBar = { DeliveryToTopBar() },
       contentWindowInsets = WindowInsets(top = 0.dp)
   ) {contentPadding->
        Column(modifier = Modifier
            .padding(top = contentPadding.calculateTopPadding())
            .verticalScroll(scrollState)) {
            FilterRow(filterItems = filterItems)
            SectionBanner(label = R.string.banner1)
            SnackCardItem(snack = snacks[0])
        }
   }
}

@Composable
fun SnackCardItem(modifier: Modifier = Modifier,
                snack: Snack){

    Box(modifier = modifier){
            Card(
                modifier = modifier
                    .height(250.dp)
                    .width(150.dp)
            ) {
                Box(modifier = modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Brush.horizontalGradient(JetsnackMeTheme.colors.gradient2_1)))
            }
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(snack.imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = modifier
                .clip(CircleShape)
                .size(100.dp)
                .wrapContentSize(),
            contentScale = ContentScale.Crop)
    }
    
}


@Composable
fun SectionBanner(label:Int,
                  modifier: Modifier = Modifier){
    Row(
        modifier = modifier.fillMaxWidth(),
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
              modifier: Modifier = Modifier){

        val coloredBorder = Modifier
            .border(
                width = 2.dp,
                brush = Brush.horizontalGradient(JetsnackMeTheme.colors.gradient2_2),
                shape = CircleShape
            )
            .height(25.dp)
        val backgroundChangedModifier = Modifier
            .background(
                color = JetsnackMeTheme.colors.brandSecondary,
                shape = Shapes.medium
            )
            .height(25.dp)
            .border(width = 0.dp, color = JetsnackMeTheme.colors.brandSecondary)


        LazyRow(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            item {
                Icon(painterResource(id = R.drawable.ic_filter) ,
                        contentDescription = "icon filter",
                        modifier = coloredBorder
                            .clickable {  },
                        tint = JetsnackMeTheme.colors.brand)

                Spacer(modifier = modifier.width(16.dp))
            }
            items(filterItems){item->
                var selected by rememberSaveable {
                    mutableStateOf(false)
                }
                Row(
                    modifier = modifier
                        .clickable { selected = !selected }
                        .then(if (selected) backgroundChangedModifier else coloredBorder)
                ) {
                    Text(text = stringResource(id =item ),
                        style = Typography.overline,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp))
                }
                Spacer(modifier = modifier.width(5.dp))
            }
        }


}

