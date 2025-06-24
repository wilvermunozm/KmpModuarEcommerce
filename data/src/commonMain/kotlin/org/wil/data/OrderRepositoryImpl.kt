package org.wil.data

import com.nutrisport.shared.component.Order
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import org.wil.data.domain.CustomerRepository
import org.wil.data.domain.OrderRepository

class OrderRepositoryImpl(
    private val customerRepository: CustomerRepository,
) : OrderRepository {
    override fun getCurrentUserId() = Firebase.auth.currentUser?.uid

    override suspend fun createTheOrder(
        order: Order,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ) {
        try {
            val currentUserId = getCurrentUserId()
            if (currentUserId != null) {
                val database = Firebase.firestore
                val orderCollection = database.collection(collectionPath = "order")
                orderCollection.document(order.id).set(order)
                customerRepository.deleteAllCartItems(
                    onSuccess = {},
                    onError = {}
                )
                onSuccess()
            } else {
                onError("User is not available.")
            }
        } catch (e: Exception) {
            onError("Error while adding a product to cart: ${e.message}")
        }
    }
}