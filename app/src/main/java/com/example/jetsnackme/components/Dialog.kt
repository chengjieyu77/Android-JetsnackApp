package com.example.jetsnackme.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.jetsnackme.R
import com.example.jetsnackme.screen.home.FilterRowItem
import com.example.jetsnackme.ui.theme.JetsnackMeTheme
import com.example.jetsnackme.ui.theme.Typography


@Composable
fun FilterDialog(
    modifier: Modifier = Modifier,
    selectedSortItem: SortItem,
    onFilterSortItemSelect :(SortItem)->Unit,
    maxCalories:MutableState<Float>,
    onClose:()->Unit,
){
    val listOfSortItems = listOf(
        SortItem(R.drawable.ic_android,R.string.sort1),
        SortItem(R.drawable.ic_startrating,R.string.sort2),
        SortItem(R.drawable.ic_sort_by_alpha,R.string.sort3)
    )
    val priceList = listOf(
        R.string.price1,
        R.string.price2,
        R.string.price3,
        R.string.price4
    )
    val categoryList = listOf(
        R.string.category1,
        R.string.category2,
        R.string.category3,
        R.string.category4
    )

    val lifeStyleList = listOf(
        R.string.lifestyle1,
        R.string.lifestyle2,
        R.string.lifestyle3,
        R.string.lifestyle4,
        R.string.lifestyle5
    )
    Dialog(onDismissRequest = onClose,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Scaffold(
            modifier = modifier.padding(vertical = 15.dp),
            topBar = { FilterTopBar(
                onNavClick = onClose
            )},
            contentWindowInsets = WindowInsets(top = 0.dp)
        ) { PaddingValues ->
            Column(modifier = modifier
                .padding(top = PaddingValues.calculateTopPadding(), start = 16.dp, end = 16.dp)
                .fillMaxWidth()) {
                //Sort
                FilterBanner(label = R.string.filter_banner1)
                SortGroup(
                    selectedSortItem = selectedSortItem,
                    listOfSortItems = listOfSortItems,
                    onFilterSortItemSelect =onFilterSortItemSelect)

                //Price
                FilterBanner(label = R.string.filter_banner2)
                MultipleChoiceFlowRow(choices = priceList)

                //Dialog
                FilterBanner(label = R.string.filter_banner3)
                MultipleChoiceFlowRow(choices = categoryList)

                //Max calories
                FilterBanner(label = R.string.filter_banner4,
                    subtitle = R.string.filter_banner4_subtitle)
                Slider(value = maxCalories.value,
                    onValueChange = {maxCalories.value = it},

                    valueRange = 0f..1000f,
                    steps=5,
                    colors = SliderDefaults.colors(
                        thumbColor = JetsnackMeTheme.colors.brand,
                        activeTrackColor = JetsnackMeTheme.colors.brand

                    )
                )

                //LifeStyle
                FilterBanner(label = R.string.filter_banner5)
                MultipleChoiceFlowRow(choices = lifeStyleList)


            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MultipleChoiceFlowRow(
    modifier: Modifier = Modifier,
    choices:List<Int>
){
    FlowRow(
        modifier = modifier.padding(vertical = 8.dp)
    ) {
        choices.forEach {choice->
            FilterRowItem(item = choice,modifier = modifier.padding(bottom = 12.dp))
            Spacer(modifier = modifier.width(8.dp))
        }

    }
}

@Composable
fun SortGroup(
    modifier: Modifier = Modifier,
    selectedSortItem:SortItem,
    listOfSortItems:List<SortItem>,
    onFilterSortItemSelect: (SortItem) -> Unit
){
//    var selected by remember {
//        mutableStateOf(false)
//    }
//    val (selectedOption,onOptionSelected) = remember{
//        mutableStateOf(SortItem(R.drawable.ic_android,R.string.sort1))
//    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .selectableGroup()
    ) {
        listOfSortItems.forEach { item->
            val selected = item == selectedSortItem
            SortItemDisplay(checked = selected,
                icon = item.icon,
                label =item.label,
                onFilterSortItemSelect = { onFilterSortItemSelect(item) })
        }
    }
}

@Composable
fun SortItemDisplay(modifier:Modifier = Modifier,
             checked:Boolean,
             icon:Int,
             label:Int,
             onFilterSortItemSelect:()->Unit){


    Row(
        modifier = modifier
            .selectable(
                selected = checked,
                onClick = onFilterSortItemSelect
            )
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(id = icon),
            contentDescription = "sort item icon")
        Text(text = stringResource(id = label),
            style = Typography.subtitle1,
            modifier = modifier.padding(start = 8.dp))
        Spacer(modifier = modifier.weight(1f))
        if (checked){
            Icon(imageVector = Icons.Outlined.Check,
                contentDescription = "check icon",
                tint = JetsnackMeTheme.colors.brand)
        }


    }
}

@Composable
fun FilterBanner(modifier:Modifier = Modifier,
                 label: Int,
                 subtitle:Int? = null){
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(id =label ),
            style = Typography.h5,
            color = JetsnackMeTheme.colors.textPrimary,
            modifier = modifier.padding(vertical = 8.dp))
        if (subtitle != null){
            Text(text = stringResource(id = subtitle),
                style = Typography.subtitle1,
                color = JetsnackMeTheme.colors.textPrimary,
                modifier = modifier.padding(start = 16.dp))
        }
    }

}

data class SortItem(
    val icon:Int,
    val label:Int)

enum class FilterSortItems{
    AndroidFavorite,
    Rating,
    Alphabetical
}