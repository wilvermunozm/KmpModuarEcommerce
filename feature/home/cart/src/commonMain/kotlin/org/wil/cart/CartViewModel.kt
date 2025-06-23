package org.wil.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nutrisport.shared.util.RequestState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import org.wil.data.domain.CustomerRepository
import org.wil.data.domain.ProductRepository

class CartViewModel(
    private val customerRepository: CustomerRepository,
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val customer = customerRepository.readCustomerFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val products = customer
        .flatMapLatest { customerState ->
            if (customerState.isSuccess()) {
                val productIds = customerState.getSuccessData().cart.map { it.productId }.toSet()
                if (productIds.isNotEmpty()) {
                    productRepository.readProductsByIdsFlow(productIds.toList())
                } else flowOf(RequestState.Success(emptyList()))
            } else if (customerState.isError()) {
                flowOf(RequestState.Error(customerState.getErrorMessage()))
            } else flowOf(RequestState.Loading)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    val cartItemsWithProducts = combine(customer, products) { customerState, productsState ->
        when {
            customerState.isSuccess() && productsState.isSuccess() -> {
                val cart = customerState.getSuccessData().cart
                val products = productsState.getSuccessData()

                val result = cart.mapNotNull { cartItem ->
                    val product = products.find { it.id == cartItem.productId }
                    product?.let { cartItem to it }
                }

                RequestState.Success(result)
            }

            customerState.isError() -> RequestState.Error(customerState.getErrorMessage())
            productsState.isError() -> RequestState.Error(productsState.getErrorMessage())

            else -> RequestState.Loading
        }
    }

    fun updateCartItemQuantity(
        id: String,
        quantity: Int,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ) {
        viewModelScope.launch {
            customerRepository.updateCartItemQuantity(
                id = id,
                quantity = quantity,
                onSuccess = onSuccess,
                onError = onError
            )
        }
    }

    fun deleteCartItem(
        id: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ) {
        viewModelScope.launch {
            customerRepository.deleteCartItem(
                id = id,
                onSuccess = onSuccess,
                onError = onError
            )
        }
    }
}