package com.example.jetsnackme.screen.snack_detail

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.jetsnackme.R
import com.example.jetsnackme.model.Snack
import com.example.jetsnackme.navigation.JetsnackScreens
import com.example.jetsnackme.screen.home.SectionBanner
import com.example.jetsnackme.screen.home.SnackRoundedRow
import com.example.jetsnackme.ui.theme.JetsnackMeTheme
import com.example.jetsnackme.ui.theme.Neutral8
import com.example.jetsnackme.ui.theme.Typography
import java.lang.Math.min
import kotlin.math.max

private val BottomBarHeight = 56.dp
private val TitleHeight = 128.dp
private val GradientScroll = 180.dp
private val ImageOverlap = 115.dp
private val MinTitleOffset = 56.dp
private val MinImageOffset = 12.dp
private val MaxTitleOffset = ImageOverlap + MinTitleOffset + GradientScroll
private val ExpandedImageSize = 300.dp
private val CollapsedImageSize = 150.dp
private val HzPadding = Modifier.padding(horizontal = 24.dp)

@Preview
@Composable
fun SnackDetailScreenNew(
    snackId:String? = "1",
    navController: NavController = rememberNavController(),
    viewModel: SnackDetailScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
){
    val snack = viewModel.getSnackById(snackId)
    Box(Modifier.fillMaxSize()) {
        val scroll = rememberScrollState(0)
        Header()
        Body(scroll = scroll, snacksPick = viewModel.picksSnacks, snacksPopular = viewModel.popularSnacks) {
            navController.navigate(JetsnackScreens.SnackDetailScreen.name+"/$snackId")
        }
        Title(snack = snack){scroll.value}
        Image(imageUrl = snack.imageUrl) {scroll.value}
        Up { navController.popBackStack() }
        AddToCartRow(modifier = Modifier.align(Alignment.BottomCenter))

    }
}

@Composable
private fun Header(){
    Spacer(modifier = Modifier
        .height(200.dp)
        .fillMaxWidth()
        .background(Brush.horizontalGradient(JetsnackMeTheme.colors.tornado1)))
}

@Composable
private fun Up(upPress:()->Unit){
    IconButton(onClick = upPress,
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .size(36.dp)
            .background(
                color = Neutral8.copy(0.32f),
                shape = CircleShape
            )) {
        Icon(imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
            contentDescription = "back icon")

    }
}

@Composable
private fun Body(
    scroll:ScrollState,
    snacksPick:List<Snack>,
    snacksPopular: List<Snack>,
    onClickToDetail:(String)->Unit
){
    var isExpanded by remember {
        mutableStateOf(false)
    }

    Column {
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .height(MinTitleOffset))
        Column(
            modifier = Modifier.verticalScroll(scroll)
        ) {
            Spacer(modifier = Modifier.height(GradientScroll))
            Column {
                Spacer(modifier = Modifier.height(ImageOverlap))
                Spacer(modifier = Modifier.height(TitleHeight))

                Spacer(modifier = Modifier.height(16.dp))
                Text(text = stringResource(id = R.string.details),
                    style = Typography.subtitle2,
                    color = JetsnackMeTheme.colors.textSecondary.copy(0.8f),
                    modifier = Modifier.padding(vertical = 8.dp))
                Text(text = stringResource(id = R.string.detailsContent),
                    style = Typography.body2,
                    color = JetsnackMeTheme.colors.textSecondary.copy(0.5f),
                    lineHeight = 28.sp,
                    modifier =Modifier.padding(vertical = 8.dp),
                    maxLines = if (isExpanded) 100 else 5)
                Text(text = if (isExpanded) "SEE LESS" else "SEE MORE",
                    style = Typography.button,
                    color = JetsnackMeTheme.colors.brand,
                    modifier = Modifier
                        .clickable { isExpanded = !isExpanded }
                        .align(Alignment.CenterHorizontally))
                Spacer(modifier = Modifier.height(32.dp))
                Text(text = stringResource(id = R.string.ingredients),
                    style = Typography.subtitle2,
                    color = JetsnackMeTheme.colors.textSecondary.copy(0.8f),
                    modifier = Modifier.padding(vertical = 2.dp))
                Text(text = stringResource(id = R.string.ingredientsContents),
                    style = Typography.body2,
                    color = JetsnackMeTheme.colors.textSecondary.copy(0.5f),
                    lineHeight = 28.sp,)

                SectionBanner(label = R.string.banner6)
                SnackRoundedRow(snacks = snacksPick,
                    onClickToDetail = onClickToDetail)


                SectionBanner(label = R.string.banner2)
                SnackRoundedRow(snacks = snacksPopular,
                    onClickToDetail = onClickToDetail)

                Spacer(modifier = Modifier
                    .padding(bottom = BottomBarHeight)
                    .navigationBarsPadding()
                    .height(8.dp))
            }

        }
    }
}

@Composable
private fun Title(
    snack:Snack,
    scrollProvider:()->Int
){
    val maxOffset = with(LocalDensity.current){ MaxTitleOffset.toPx()}
    val minOffset = with(LocalDensity.current){ MinTitleOffset.toPx()}
    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .heightIn(min = TitleHeight)
            .fillMaxWidth()
            .statusBarsPadding()
            .offset {
                val scroll = scrollProvider()
                val offset = (maxOffset - scroll).coerceAtLeast(minOffset)
                IntOffset(x = 0, y = offset.toInt())
            }
            .background(color = JetsnackMeTheme.colors.uiBackground)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = snack.name,
            style = Typography.h4,
            color = JetsnackMeTheme.colors.textPrimary,
            modifier = HzPadding)
        Text(text = snack.tagline,
            style = Typography.h5,
            color = JetsnackMeTheme.colors.textSecondary.copy(0.5f),
            modifier = HzPadding)
        Text(text = "$"+snack.price.toString(),
            style = Typography.h5,
            color = JetsnackMeTheme.colors.brand,
            modifier = HzPadding)
    }
}

@Composable
private fun Image(
    imageUrl:String,
    scrollProvider: () -> Int
){
    val collapseRange = with(LocalDensity.current){ (MaxTitleOffset - MinTitleOffset).toPx()}
    val collapseFractionProvider = {
        (scrollProvider()/collapseRange).coerceIn(0f,1f)
    }
    CollapsingImageLayout(
        collapseFractionProvider = collapseFractionProvider,
        modifier = HzPadding.statusBarsPadding()
    ){
        AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                        //.requiredSize(imageSize),
                        //.wrapContentSize(),
                    contentScale = ContentScale.Crop)
    }
}
@Composable
private fun CollapsingImageLayout(
    collapseFractionProvider:()->Float,
    modifier: Modifier = Modifier,
    content:@Composable ()->Unit
){
    Layout(
        modifier = modifier,
        content = content
    ){measurables,constraints->
        check(measurables.size == 1)
        val collapseFraction = collapseFractionProvider()

        val imageMaxSize = min(ExpandedImageSize.roundToPx(),constraints.maxWidth)
        val imageMinSize = max(CollapsedImageSize.roundToPx(),constraints.minWidth)
        val imageWidth = lerp(imageMaxSize,imageMinSize,collapseFraction)
        val imagePlaceable = measurables[0].measure(Constraints.fixed(imageWidth,imageWidth))

        val imageY = lerp(MinTitleOffset, MinImageOffset,collapseFraction).roundToPx()
        val imageX = lerp(
            (constraints.maxWidth - imageWidth)/2,
            constraints.maxWidth - imageWidth,
            collapseFraction
        )
        layout(
            width = constraints.maxWidth,
            height = imageY + imageWidth
        ){
            imagePlaceable.placeRelative(imageX,imageY)
        }
    }
}

@Composable
private fun CartBottomBar(modifier:Modifier = Modifier){

}

@Preview
@Composable
fun DetailNewPreview(){
    JetsnackMeTheme {
        SnackDetailScreenNew()
    }
}
