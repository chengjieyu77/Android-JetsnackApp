package com.example.jetsnackme.data

import com.example.jetsnackme.R
import com.example.jetsnackme.model.Order
import com.example.jetsnackme.model.SearchCategory
import com.example.jetsnackme.model.Snack

object DataSource {
    fun getSnacks():List<Snack> = snacks
    fun getRecentSearches():List<String> = recentSearches
    fun getPopularSearches():List<String> = popularSearches
    fun getFilterItems():List<Int> = filterItems
    fun getCartItems():List<Snack> = cartItems
    fun getFakeOrder():List<Order> = cartFakeOrder

}
val cartFakeOrder = listOf(
    Order(
        snack = Snack(
            id = 3,
            name = "Eclair",
            price = 299,
            imageUrl = "https://source.unsplash.com/-LojFX9NfPY",
            tagline = "A tag line"
        ),
        quantity = 2
    ),
    Order(
        snack = Snack(
            id = 7,
            name = "Ice Cream Sandwich",
            price = 299,
            imageUrl = "https://source.unsplash.com/YgYJsFDd4AU",
            tagline = "A tag line"
        ),
        quantity = 3
    ),
    Order(
        snack = Snack(
            id = 9,
            name = "KitKat",
            price = 299,
            imageUrl = "https://source.unsplash.com/yb16pT5F_jE",
            tagline = "A tag line"
        ),
        quantity = 1
    ),
)
val cartItems = listOf(
    Snack(
        id = 3,
        name = "Eclair",
        price = 299,
        imageUrl = "https://source.unsplash.com/-LojFX9NfPY",
        tagline = "A tag line"
    ),
    Snack(
        id = 7,
        name = "Ice Cream Sandwich",
        price = 299,
        imageUrl = "https://source.unsplash.com/YgYJsFDd4AU",
        tagline = "A tag line"
    ),
    Snack(
        id = 9,
        name = "KitKat",
        price = 299,
        imageUrl = "https://source.unsplash.com/yb16pT5F_jE",
        tagline = "A tag line"
    )

)
val recentSearches = listOf("Cheese","Apple Sauce")
val popularSearches = listOf("Organic","Gluten Free","Paleo","Vegan","Vegitarian","Whole30")

val filterItems = listOf(
    R.string.filter1,
    R.string.filter2,
    R.string.filter3,
    R.string.filter4,
    R.string.filter5
)

val snacks = listOf(
    Snack(
        id = 1,
        name = "Cupcake",
        price = 299,
        imageUrl = "https://source.unsplash.com/pGM4sjt_BdQ",
        tagline = "A tag line"
    ),
    Snack(
        id = 2,
        name = "Donut",
        price = 299,
        imageUrl = "https://source.unsplash.com/Yc5sL-ejk6U",
        tagline = "A tag line"
    ),
    Snack(
        id = 3,
        name = "Eclair",
        price = 299,
        imageUrl = "https://source.unsplash.com/-LojFX9NfPY",
        tagline = "A tag line"
    ),
    Snack(
        id = 4,
        name = "Froyo",
        price = 299,
        imageUrl = "https://source.unsplash.com/3U2V5WqK1PQ",
        tagline = "A tag line"
    ),
    Snack(
        id = 5,
        name = "Gingerbread",
        price = 299,
        imageUrl = "https://source.unsplash.com/Y4YR9OjdIMk",
        tagline = "A tag line"
    ),
    Snack(
        id = 6,
        name = "Honeycomb",
        price = 299,
        imageUrl = "https://source.unsplash.com/bELvIg_KZGU",
        tagline = "A tag line"
    ),
    Snack(
        id = 7,
        name = "Ice Cream Sandwich",
        price = 299,
        imageUrl = "https://source.unsplash.com/YgYJsFDd4AU",
        tagline = "A tag line"
    ),
    Snack(
        id = 8,
        name = "Jellybean",
        price = 299,
        imageUrl = "https://source.unsplash.com/0u_vbeOkMpk",
        tagline = "A tag line"
    ),
    Snack(
        id = 9,
        name = "KitKat",
        price = 299,
        imageUrl = "https://source.unsplash.com/yb16pT5F_jE",
        tagline = "A tag line"
    ),
    Snack(
        id = 10,
        name = "Lollipop",
        tagline = "A tag line",
        imageUrl = "https://source.unsplash.com/AHF_ZktTL6Q",
        price = 299
    ),
    Snack(
        id = 11,
        name = "Marshmallow",
        tagline = "A tag line",
        imageUrl = "https://source.unsplash.com/rqFm0IgMVYY",
        price = 299
    ),
    Snack(
        id = 12,
        name = "Nougat",
        tagline = "A tag line",
        imageUrl = "https://source.unsplash.com/qRE_OpbVPR8",
        price = 299
    ),
    Snack(
        id = 13,
        name = "Oreo",
        tagline = "A tag line",
        imageUrl = "https://source.unsplash.com/33fWPnyN6tU",
        price = 299
    ),
    Snack(
        id = 14,
        name = "Pie",
        tagline = "A tag line",
        imageUrl = "https://source.unsplash.com/aX_ljOOyWJY",
        price = 299
    ),
    Snack(
        id = 15,
        name = "Chips",
        imageUrl = "https://source.unsplash.com/UsSdMZ78Q3E",
        price = 299
    ),
    Snack(
        id = 16,
        name = "Pretzels",
        imageUrl = "https://source.unsplash.com/7meCnGCJ5Ms",
        price = 299
    ),
    Snack(
        id = 17,
        name = "Smoothies",
        imageUrl = "https://source.unsplash.com/m741tj4Cz7M",
        price = 299
    ),
    Snack(
        id = 18,
        name = "Popcorn",
        imageUrl = "https://source.unsplash.com/iuwMdNq0-s4",
        price = 299
    ),
    Snack(
        id = 19,
        name = "Almonds",
        imageUrl = "https://source.unsplash.com/qgWWQU1SzqM",
        price = 299
    ),
    Snack(
        id = 20,
        name = "Cheese",
        imageUrl = "https://source.unsplash.com/9MzCd76xLGk",
        price = 299
    ),
    Snack(
        id = 21,
        name = "Apples",
        tagline = "A tag line",
        imageUrl = "https://source.unsplash.com/1d9xXWMtQzQ",
        price = 299
    ),
    Snack(
        id = 22,
        name = "Apple sauce",
        tagline = "A tag line",
        imageUrl = "https://source.unsplash.com/wZxpOw84QTU",
        price = 299
    ),
    Snack(
        id = 23,
        name = "Apple chips",
        tagline = "A tag line",
        imageUrl = "https://source.unsplash.com/okzeRxm_GPo",
        price = 299
    ),
    Snack(
        id = 24,
        name = "Apple juice",
        tagline = "A tag line",
        imageUrl = "https://source.unsplash.com/l7imGdupuhU",
        price = 299
    ),
    Snack(
        id = 25,
        name = "Apple pie",
        tagline = "A tag line",
        imageUrl = "https://source.unsplash.com/bkXzABDt08Q",
        price = 299
    ),
    Snack(
        id = 26,
        name = "Grapes",
        tagline = "A tag line",
        imageUrl = "https://source.unsplash.com/y2MeW00BdBo",
        price = 299
    ),
    Snack(
        id = 27,
        name = "Kiwi",
        tagline = "A tag line",
        imageUrl = "https://source.unsplash.com/1oMGgHn-M8k",
        price = 299
    ),
    Snack(
        id = 28,
        name = "Mango",
        tagline = "A tag line",
        imageUrl = "https://source.unsplash.com/TIGDsyy0TK4",
        price = 299
    )

)

val searchCategories = listOf(
    SearchCategory(label = R.string.searchCategories1,
        imageUrl = "https://source.unsplash.com/UsSdMZ78Q3E" ),
    SearchCategory(label = R.string.searchCategories2,
        imageUrl ="https://source.unsplash.com/SfP1PtM9Qa8" ),
    SearchCategory(label = R.string.searchCategories3,
        imageUrl = "https://source.unsplash.com/_jk8KIyN_uA" ),
    SearchCategory(label = R.string.searchCategories4,
        imageUrl =  "https://source.unsplash.com/UsSdMZ78Q3E" ),
)

val searchLifestyles = listOf(
    SearchCategory(label = R.string.searchLifestyles1,
        imageUrl = "https://source.unsplash.com/7meCnGCJ5Ms"),
    SearchCategory(label = R.string.searchLifestyles2,
        imageUrl = "https://source.unsplash.com/m741tj4Cz7M"),
    SearchCategory(label = R.string.searchLifestyles3,
        imageUrl = "https://source.unsplash.com/dt5-8tThZKg"),
    SearchCategory(label = R.string.searchLifestyles4,
        imageUrl = "https://source.unsplash.com/ReXxkS1m1H0"),
    SearchCategory(label = R.string.searchLifestyles5,
        imageUrl =  "https://source.unsplash.com/IGfIGP5ONV0"),
    SearchCategory(label = R.string.searchLifestyles6,
        imageUrl = "https://source.unsplash.com/9MzCd76xLGk"),
)
