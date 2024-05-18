package com.example.jetsnackme.screen.cart

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.jetsnackme.R
import com.example.jetsnackme.components.DeliveryToTopBar
import com.example.jetsnackme.components.ImageFromInternet
import com.example.jetsnackme.data.snacks
import com.example.jetsnackme.model.Order
import com.example.jetsnackme.model.Snack
import com.example.jetsnackme.navigation.JetsnackScreens
import com.example.jetsnackme.screen.home.SectionBanner
import com.example.jetsnackme.screen.home.SnackRoundedRow
import com.example.jetsnackme.screen.snack_detail.brushedIconModifier
import com.example.jetsnackme.ui.theme.JetsnackMeTheme
import com.example.jetsnackme.ui.theme.Typography
import java.math.BigDecimal

@Composable
fun CartScreen(navController: NavController,
               modifier :Modifier = Modifier,
               viewModel: CartScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()){
    val scrollState = rememberScrollState()
    Scaffold(
        modifier = modifier.statusBarsPadding(),
        topBar = { DeliveryToTopBar() },
        bottomBar = {CheckOutRow(modifier)},
        containerColor = Color.Transparent,
    ) {contentPadding->
        Column(
            modifier
                .padding(
                    top = contentPadding.calculateTopPadding()
                )
                .verticalScroll(scrollState)) {

            CartBannerLetter(label = "Order(${viewModel.cartOrderNew.size} items)",
                modifier = modifier.padding(horizontal = 16.dp))

            for (snackId in viewModel.cartOrderNew.keys){
//                CartItem(
//                    snack = snacks[snackId-1],
//                    count = viewModel.cartOrderNew[snackId]!!,
//                    onRemoveItem = viewModel::removeItemFromCartOrder,
//                    onAddItem = viewModel::onAddItem,
//                    onMinusItem = viewModel::onMinusItem
//                )
                CartItemUsingConstraintLayout(
                    snack = snacks[snackId-1],
                    count = viewModel.cartOrderNew[snackId]!!,
                    onRemoveItem = viewModel::removeItemFromCartOrder,
                    onAddItem = viewModel::onAddItem,
                    onMinusItem = viewModel::onMinusItem){
                    navController.navigate(JetsnackScreens.SnackDetailScreen.name + "/$it")
                }
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
            Spacer(modifier = modifier.height(35.dp))
            
            
            


        }
    }
}

@Composable
fun CheckOutRow(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.background(color = JetsnackMeTheme.colors.uiBackground),
        shadowElevation = 5.dp,
        border = BorderStroke(width = 2.dp, color = JetsnackMeTheme.colors.uiBorder)
    ) {
        //Divider()
        Row(horizontalArrangement = Arrangement.End,
            modifier = modifier
                .background(JetsnackMeTheme.colors.uiBackground)
                .padding(8.dp)) {
            Spacer(modifier = modifier.weight(1f))
            TextButton(onClick = { /*TODO*/ },
                modifier = modifier
                    .fillMaxWidth(0.45f)
                    .background(Brush.horizontalGradient(JetsnackMeTheme.colors.gradient2_1))
                    .height(35.dp),
            ) {
                Text(text = "Checkout", modifier = modifier.offset((-40).dp),
                    style = Typography.subtitle2,
                    color = JetsnackMeTheme.colors.iconInteractive)
            }
        }
    }

}

@Composable
fun SummarySection(modifier: Modifier = Modifier,
                   subtotal:BigDecimal,
                   total:BigDecimal,
                   shippingAndHandling:BigDecimal){
    CartBannerLetter(label = "Summary",modifier = modifier.padding(horizontal = 16.dp))
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
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
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Shipping & Handling",
            style = Typography.body1,
            color = JetsnackMeTheme.colors.textSecondary)
        Text(text = "$${shippingAndHandling.divide(BigDecimal(100))}",
            style = Typography.body1,
            color = JetsnackMeTheme.colors.textSecondary)
    }
    Divider(modifier = modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp))
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
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
fun CartItemUsingConstraintLayout(
    modifier: Modifier = Modifier,
    snack: Snack,
    count:MutableState<Int>,
    onRemoveItem:(Int)->Unit = {},
    onAddItem:(Int)->Unit = {},
    onMinusItem:(Int)->Unit = {},
    onNavigateToSnackDetail:(String)->Unit = {}

){

    ConstraintLayout(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp)
            .clickable {  onNavigateToSnackDetail(snack.id.toString())}
    ) {
            val (image,name,tag,price,qty,closeButton) = createRefs()
            ImageFromInternet(
                modifier = modifier.constrainAs(image){
                    start.linkTo(parent.start)
                },
                imageUrl = snack.imageUrl,
                size = 100.dp,
                contentDescription = snack.name)

            Text(text = snack.name,
                modifier = modifier.constrainAs(name){
                    start.linkTo(image.end, margin = 16.dp)
                    top.linkTo(parent.top, margin = 16.dp)

                },
                style = Typography.subtitle1)
            Text(text = snack.tagline,
                modifier = modifier.constrainAs(tag){
                    start.linkTo(image.end, margin = 16.dp)
                    top.linkTo(name.bottom, margin = 8.dp)
                },
                style = Typography.body2,
                color = JetsnackMeTheme.colors.textSecondary.copy(0.5f))

            Text(text = "$${snack.price.toBigDecimal().divide(BigDecimal(100))}",
                modifier = modifier.constrainAs(price){
                    start.linkTo(image.end, margin = 16.dp)
                    bottom.linkTo(parent.bottom, margin = 8.dp)
                },
                style = Typography.h6,
                color = JetsnackMeTheme.colors.brand)

            IconButton(onClick = {onRemoveItem(snack.id)},
                modifier = modifier.constrainAs(closeButton){
                    end.linkTo(parent.end, margin = 4.dp)
                    top.linkTo(parent.top,margin = 4.dp)
                }) {
                Icon(imageVector = Icons.Outlined.Close,
                    contentDescription = "remove icon")}


            QtyRow(
                modifier = modifier.constrainAs(qty){
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                },
                snack = snack,
                count =count,
                onAddItem=onAddItem,
                onMinusItem=onMinusItem)


        }


}

@Composable
fun CartItem(modifier: Modifier = Modifier,
             snack: Snack,
             count:MutableState<Int>,
             onRemoveItem:(Int)->Unit = {},
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
        SnackNameAndPrice(snack = snack)
        Spacer(modifier = Modifier.weight(1f))

        Column(modifier = modifier) {
            IconButton(onClick = {onRemoveItem(snack.id)},
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
fun SnackNameAndPrice(
    modifier: Modifier = Modifier,
    snack:Snack){
    Column(modifier = modifier.padding(start = 8.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(text = snack.name,
            style = Typography.subtitle1)
        Text(text = snack.tagline, style = Typography.body2,
            color = JetsnackMeTheme.colors.textSecondary.copy(0.5f))
        Text(text = "$${snack.price.toBigDecimal().divide(BigDecimal(100))}",
            style = Typography.h6,
            color = JetsnackMeTheme.colors.brand)
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
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Qty",
            style = Typography.subtitle1,
            color = JetsnackMeTheme.colors.textSecondary)
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(onClick = {onMinusItem(snack.id)} ) {
            Icon(
                modifier = brushedIconModifier.size(40.dp),
                painter = painterResource(id = R.drawable.ic_removv),
                contentDescription = "remove icon",
            )
        }

        Text(text = "${count.value}",
            style = Typography.subtitle1,
            color = JetsnackMeTheme.colors.textSecondary)


        IconButton(onClick = {onAddItem(snack.id)}) {
            Icon(
                modifier = brushedIconModifier.size(40.dp),
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


@Preview
@Composable
fun CartItemUsingConstraintLayoutPreview(){
    JetsnackMeTheme {
        CartItemUsingConstraintLayout(snack = snacks[0],
            count = remember{ mutableIntStateOf(1) })
    }
}
@Preview(showBackground = true)
@Composable
fun CheckOutPreview(){
    JetsnackMeTheme {
        CheckOutRow()
    }

}