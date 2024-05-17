package com.example.jetsnackme.screen.cart

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.unit.times
import androidx.lifecycle.ViewModel
import com.example.jetsnackme.data.DataSource
import com.example.jetsnackme.data.snacks
import com.example.jetsnackme.model.Snack
import kotlin.time.times

class CartScreenViewModel :ViewModel(){
    val picksSnacks = snacks.subList(0,13)
    private val _cartItems = DataSource.getCartItems()
    private val _cartItemsCount = _cartItems.size
    private val _cartOrder = DataSource.getFakeOrder()
    //item id map to item quantity
    private val _cartOrderNew = mutableMapOf<Int,MutableState<Int>>(
        3 to mutableIntStateOf(2),
        7 to mutableIntStateOf(3),
        9 to mutableIntStateOf(1),
    )
    private var _subtotal = mutableDoubleStateOf(0.00)
    private val _shippingAndHandling = 3.69
    private var _total = mutableDoubleStateOf(0.00)
    var sub = 0.0

    val cartItems
        get() = _cartItems.toMutableStateList()

    val cartItemsCount
        get() = _cartItemsCount

    val cartOrder
        get() = _cartOrder.toMutableStateList()

    val cartOrderNew
        get() = _cartOrderNew

    val subtotal
        get() = _subtotal.doubleValue

    val total
        get() = _total.doubleValue

    val shippingAndHandling
        get() = _shippingAndHandling

    private fun removeItemFromCartOrder(itemId: Int){
        _cartOrderNew.remove(itemId)
    }

    //change item quantity
    fun onAddItem(itemId:Int){
        cartOrderNew[itemId]?.value = cartOrderNew[itemId]?.value?.plus(1)!!
        calculateSubtotalOnAdd(itemId = itemId)
        calculateTotal()
    }

    fun onMinusItem(itemId: Int){
        if (cartOrderNew[itemId]?.value!! > 0 ){
            cartOrderNew[itemId]?.value = cartOrderNew[itemId]?.value?.minus(1)!!
            calculateSubtotalOnMinus(itemId = itemId)
            calculateTotal()
        }
        else if(cartOrderNew[itemId]?.value!! == 0 ){
            removeItemFromCartOrder(itemId)
        }
    }

//    fun getSubTotal():Double{
//
//        for (snackId in cartOrderNew.keys){
//            //sub = sub + cartOrderNew[snackId]?.let { snacks[snackId-1].price.times(it.value) }!!
//
//            sub = sub + cartOrderNew[snackId]?.value?.times(snacks[snackId-1].price)!!
//        }
//        return sub
//    }

//    fun getTotal():Double{
//        return sub+_shippingAndHandling
//    }

    private fun calculateSubtotalOnAdd(itemId: Int){
        _subtotal.doubleValue += snacks[itemId - 1].price
//        for (snackId in cartOrderNew.keys){
//            _subtotal.doubleValue = _subtotal.doubleValue + cartOrderNew[snackId]?.let { snacks[snackId-1].price.times(it.value) }!!
//        }


    }

    private fun calculateSubtotalOnMinus(itemId: Int){
        _subtotal.doubleValue -= snacks[itemId - 1].price
    }

    private fun calculateTotal(){
        _total.doubleValue = _subtotal.doubleValue + _shippingAndHandling
    }


}


