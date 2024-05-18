package com.example.jetsnackme.screen.cart

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.unit.times
import androidx.lifecycle.ViewModel
import com.example.jetsnackme.data.DataSource
import com.example.jetsnackme.data.snacks
import com.example.jetsnackme.model.Snack
import java.math.BigDecimal
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
    private val _startSubTotal = getSubtotal()
    private var _subtotal = mutableLongStateOf(_startSubTotal)
    private val _shippingAndHandling = mutableLongStateOf(369)
    private  var _startTotal = getTotal()
    private var _total = mutableLongStateOf(_startTotal)


    val cartItems
        get() = _cartItems.toMutableStateList()

    val cartItemsCount
        get() = _cartItemsCount

    val cartOrder
        get() = _cartOrder.toMutableStateList()

    val cartOrderNew
        get() = _cartOrderNew

    val subtotal: BigDecimal
        get() = _subtotal.longValue.toBigDecimal().divide(BigDecimal(100))

    val total: BigDecimal
        get() = _total.longValue.toBigDecimal().divide(BigDecimal(100))

    val shippingAndHandling
        get() = _shippingAndHandling.longValue.toBigDecimal()

    private fun getSubtotal():Long{
        var subTotal :Long= 0
        for (itemId in _cartOrderNew.keys){
            subTotal += _cartOrderNew[itemId]?.value!!.times(snacks[itemId-1].price)
        }
        return subTotal
    }

    private fun getTotal():Long{
        return _startSubTotal + _shippingAndHandling.longValue
    }

    fun removeItemFromCartOrder(itemId: Int){
        calculateSubtotalOnRemove(itemId = itemId)
        calculateShippingAndHandlingFee()
        calculateTotal()
        _cartOrderNew.remove(itemId)

    }

    //change item quantity
    fun onAddItem(itemId:Int){
        cartOrderNew[itemId]?.value = cartOrderNew[itemId]?.value?.plus(1)!!
        calculateSubtotalOnAdd(itemId = itemId)
        calculateTotal()
    }

    fun onMinusItem(itemId: Int){
        if (cartOrderNew[itemId]?.value!! == 1 ){
            removeItemFromCartOrder(itemId)
        }else if(cartOrderNew[itemId]?.value!! > 1){
            cartOrderNew[itemId]?.value = cartOrderNew[itemId]?.value?.minus(1)!!
            calculateSubtotalOnMinus(itemId = itemId)
            calculateShippingAndHandlingFee()
            calculateTotal()
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
        _subtotal.longValue += snacks[itemId - 1].price
//        for (snackId in cartOrderNew.keys){
//            _subtotal.doubleValue = _subtotal.doubleValue + cartOrderNew[snackId]?.let { snacks[snackId-1].price.times(it.value) }!!
//        }


    }

    private fun calculateSubtotalOnMinus(itemId: Int){
        _subtotal.longValue -= snacks[itemId - 1].price
    }

    private fun calculateSubtotalOnRemove(itemId: Int) {
        _subtotal.longValue = _subtotal.longValue.minus(snacks[itemId-1].price.times(_cartOrderNew[itemId]?.value!!))

    }

    private fun calculateShippingAndHandlingFee(){
        if (_subtotal.longValue.toInt() == 0){
            _shippingAndHandling.longValue = 0
        }
    }
    private fun calculateTotal(){
        _total.longValue = _subtotal.longValue + _shippingAndHandling.longValue
    }


}


