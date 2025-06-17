package org.wil.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.wil.data.domain.CustomerRepository

class HomeGraphViewModel(
    private val customerRepository: CustomerRepository,
    //private val productRepository: ProductRepository
) : ViewModel() {
//    val customer = customerRepository.readCustomerFlow()
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5000),
//            initialValue = RequestState.Loading
//        )

//    @OptIn(ExperimentalCoroutinesApi::class)
//    private val products = customer
//        .flatMapLatest { customerState ->
//            if (customerState.isSuccess()) {
//                val productIds = customerState.getSuccessData().cart.map { it.productId }.toSet()
//                if (productIds.isNotEmpty()) {
//                    productRepository.readProductsByIdsFlow(productIds.toList())
//                } else flowOf(RequestState.Success(emptyList()))
//            } else if (customerState.isError()) {
//                flowOf(RequestState.Error(customerState.getErrorMessage()))
//            } else flowOf(RequestState.Loading)
//        }

//    @OptIn(ExperimentalCoroutinesApi::class)
//    val cartItemsWithProducts = combine(customer, products) { customerState, productsState ->
//        when {
//            customerState.isSuccess() && productsState.isSuccess() -> {
//                val cart = customerState.getSuccessData().cart
//                val products = productsState.getSuccessData()
//
//                val result = cart.mapNotNull { cartItem ->
//                    val product = products.find { it.id == cartItem.productId }
//                    product?.let { cartItem to it }
//                }
//
//                RequestState.Success(result)
//            }
//
//            customerState.isError() -> RequestState.Error(customerState.getErrorMessage())
//            productsState.isError() -> RequestState.Error(productsState.getErrorMessage())
//
//            else -> RequestState.Loading
//        }
//    }

//    @OptIn(ExperimentalCoroutinesApi::class)
//    val totalAmountFlow = cartItemsWithProducts
//        .flatMapLatest { data ->
//            if (data.isSuccess()) {
//                val items = data.getSuccessData()
//                val cartItems = items.map { it.first }
//                val products = items.map { it.second }.associateBy { it.id }
//
//                val totalPrice = cartItems.sumOf { cartItem ->
//                    val productPrice = products[cartItem.productId]?.price ?: 0.0
//                    productPrice * cartItem.quantity
//                }
//
//                flowOf(RequestState.Success(totalPrice))
//            } else if (data.isError()) flowOf(RequestState.Error(data.getErrorMessage()))
//            else flowOf(RequestState.Loading)
//        }

    fun signOut(
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                customerRepository.signOut()
            }
            if (result.isSuccess()) {
                onSuccess()
            } else if (result.isError()) {
                onError(result.getErrorMessage())
            }
        }
    }
}