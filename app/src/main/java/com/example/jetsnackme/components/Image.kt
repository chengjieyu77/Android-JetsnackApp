package com.example.jetsnackme.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun ImageFromInternet(
    modifier: Modifier = Modifier,
    imageUrl: String,
    size: Dp,
    contentDescription: String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = "$contentDescription image",
        modifier = modifier
            .clip(CircleShape)
            .size(size)
            .wrapContentSize()
            .shadow(elevation = 10.dp, shape = CircleShape),
        contentScale = ContentScale.Crop)
}