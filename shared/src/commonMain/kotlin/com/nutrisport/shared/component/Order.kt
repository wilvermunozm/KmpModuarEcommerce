package com.nutrisport.shared.component

import com.nutrisport.shared.domain.CartItem
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Serializable
data class Order(
    val id: String = Uuid.random().toHexString(),
    val customerId: String,
    val items: List<CartItem>,
    val totalAmount: Double,
    val token: String? = null
)