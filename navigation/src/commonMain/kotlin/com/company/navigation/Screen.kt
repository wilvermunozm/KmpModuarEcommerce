package com.company.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object Auth : Screen()

    @Serializable
    data object HomeGraph : Screen()
}