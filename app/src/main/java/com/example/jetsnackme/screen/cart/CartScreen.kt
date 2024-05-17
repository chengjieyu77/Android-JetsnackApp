package com.example.jetsnackme.screen.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.jetsnackme.R
import com.example.jetsnackme.components.DeliveryToTopBar
import com.example.jetsnackme.data.snacks
import com.example.jetsnackme.model.Order
import com.example.jetsnackme.model.Snack
import com.example.jetsnackme.navigation.JetsnackScreens
import com.example.jetsnackme.screen.home.SectionBanner
import com.example.jetsnackme.screen.home.SnackRoundedRow
import com.example.jetsnackme.screen.snack_detail.brushedIconModifier
import com.example.jetsnackme.ui.theme.JetsnackMeTheme
import com.example.jetsnackme.ui.theme.Typography

@Composable
fun CartScreen(navController: NavController,
               modifier :Modifier = Modifier,
               viewModel: CartScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()){
    val scrollState = rememberScrollState()
    Scaffold(
        modifier = modifier.statusBarsPadding(),
        topBar = { DeliveryToTopBar() },
        containerColor = Color.Transparent,
    ) {contentPadding->
        Column(
            modifier
                .padding(
                    top = contentPadding.calculateTopPadding())
                .verticalScroll(scrollState)) {
            CartBannerLetter(label = "Order(${viewModel.cartOrderNew.size} items)",
                modifier = modifier.padding(horizontal = 16.dp))

            for (snackId in viewModel.cartOrderNew.keys){
                CartItem(
                    snack = snacks[snackId-1],
                    count = viewModel.cartOrderNew[snackId]!!,
                    onAddItem = viewModel::onAddItem,
                    onMinusItem = viewModel::onMinusItem
                )
                Spacer(modifier = modifier.height(16.dp))
            }
            Divider()
            SummarySection(subtotal = viewModel.subtotal,
                total = viewModel.total,
                shippingAndHandling = viewModel.shippingAndHandling)

            SectionBanner(label = R.string.banner7)
            SnackRoundedRow(snacks = viewModel.picksSnacks) {
                navController.navigate(JetsnackScreens.SnackDetailScreen.name+"/$it")
            }
            
            
            


        }
    }
}

@Composable
fun SummarySection(modifier: Modifier = Modifier,
                   subtotal:Double,
                   total:Double,
                   shippingAndHandling:Double){
    CartBannerLetter(label = "Summary",modifier = modifier.padding(horizontal = 16.dp))
    Row(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Subtotal",
            style = Typography.body1,
            color = JetsnackMeTheme.colors.textSecondary)
        Text(text = "$$subtotal",
            style = Typography.body1)
    }
    Row(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Shipping & Handling",
            style = Typography.body1,
            color = JetsnackMeTheme.colors.textSecondary)
        Text(text = "$$shippingAndHandling",
            style = Typography.body1,
            color = JetsnackMeTheme.colors.textSecondary)
    }
    Divider(modifier = modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp))
    Row(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Text(text = "Total ",
            style = Typography.body1,
            color = JetsnackMeTheme.colors.textSecondary)
        Text(text = " $${total}",
            style = Typography.h6,
            color =JetsnackMeTheme.colors.textSecondary)
    }
    Divider(modifier = modifier.padding(vertical = 8.dp))
    
}

@Composable
fun CartItem(modifier: Modifier = Modifier,
             snack: Snack,
             count:MutableState<Int>,
             onRemoveItem:()->Unit = {},
             onAddItem:(Int)->Unit = {},
             onMinusItem:(Int)->Unit = {}){
    Row(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box{
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(snack.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription =  "${snack.name} image",
                modifier = Modifier
                    .clip(CircleShape)
                    .requiredSize(100.dp),
                //.wrapContentSize(),
                contentScale = ContentScale.Crop)
        }
        Column(modifier = Modifier.padding(start = 8.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(text = snack.name,
                style = Typography.subtitle1)
            Text(text = snack.tagline, style = Typography.body2,
                color = JetsnackMeTheme.colors.textSecondary.copy(0.5f))
            Text(text = "$${snack.price}",
                style = Typography.h6,
                color = JetsnackMeTheme.colors.brand)
        }
        Spacer(modifier = Modifier.weight(1f))
        Column(modifier = modifier) {
            IconButton(onClick = onRemoveItem,
                modifier = modifier.align(Alignment.End)) {
                Icon(imageVector = Icons.Outlined.Close,
                    contentDescription = "remove icon")}

            QtyRow(
                snack = snack,
                count = count,
                onAddItem=onAddItem,
            onMinusItem=onMinusItem)

        }

    }
}

@Composable
fun QtyRow(modifier: Modifier = Modifier,
            snack: Snack,
            count:MutableState<Int>,
             brushedIconModifier: Modifier = Modifier.brushedIconModifier(),
             onAddItem:(Int)->Unit = {},
             onMinusItem:(Int)->Unit = {},
           ){

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Qty",
            style = Typography.subtitle1,
            color = JetsnackMeTheme.colors.textSecondary)
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(onClick = {onMinusItem(snack.id)} ) {
            Icon(
                modifier = brushedIconModifier.size(60.dp),
                painter = painterResource(id = R.drawable.ic_removv),
                contentDescription = "remove icon",
            )
        }

        Text(text = "${count.value}",
            style = Typography.subtitle1,
            color = JetsnackMeTheme.colors.textSecondary)


        IconButton(onClick = {onAddItem(snack.id)}) {
            Icon(
                modifier = brushedIconModifier.size(60.dp),
                painter = painterResource(id = R.drawable.ic_add_circle),
                contentDescription = "add icon")
        }}
}

@Composable
fun CartBannerLetter(
    modifier: Modifier = Modifier,
    label: String){
    Text(text = label,
        style = Typography.h6,
        color = JetsnackMeTheme.colors.brand,
        modifier = modifier.padding(vertical= 16.dp))
}