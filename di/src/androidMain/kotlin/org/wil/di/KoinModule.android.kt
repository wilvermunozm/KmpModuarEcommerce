package org.wil.di

import org.koin.dsl.module
import org.wil.manage_product.PhotoPicker

actual val targetModule = module {
    single<PhotoPicker> { PhotoPicker() }
}