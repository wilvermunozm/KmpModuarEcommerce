package org.wil.nutrisport

import androidx.compose.ui.window.ComposeUIViewController
import org.wil.di.initializeKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initializeKoin() }
) { App() }