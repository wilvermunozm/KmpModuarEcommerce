package org.wil.di


import com.company.auth.AuthViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.wil.admin_panel.AdminPanelViewModel
import org.wil.cart.CartViewModel
import org.wil.category_search.CategorySearchViewModel
import org.wil.checkout.CheckoutViewModel
import org.wil.checkout.domain.PaypalApi
import org.wil.data.AdminRepositoryImpl
import org.wil.data.CustomerRepositoryImpl
import org.wil.data.OrderRepositoryImpl
import org.wil.data.ProductRepositoryImpl
import org.wil.data.domain.AdminRepository
import org.wil.data.domain.CustomerRepository
import org.wil.data.domain.OrderRepository
import org.wil.data.domain.ProductRepository
import org.wil.details.DetailsViewModel
import org.wil.home.HomeGraphViewModel
import org.wil.manage_product.ManageProductViewModel
import org.wil.nutrisport.ProfileViewModel
import org.wil.products_overview.ProductsOverviewViewModel

val sharedModule = module {
    single<CustomerRepository> { CustomerRepositoryImpl() }
    single<AdminRepository> { AdminRepositoryImpl() }
    single<ProductRepository> { ProductRepositoryImpl() }
    single<OrderRepository> { OrderRepositoryImpl(get()) }

    viewModelOf(::AuthViewModel)
    viewModelOf(::HomeGraphViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::AdminPanelViewModel)
    viewModelOf(::ManageProductViewModel)
    viewModelOf(::ProductsOverviewViewModel)
    viewModelOf(::DetailsViewModel)
    viewModelOf(::CartViewModel)
    viewModelOf(::CategorySearchViewModel)
    viewModelOf(::CheckoutViewModel)
    single<PaypalApi> { PaypalApi() }

//    single<IntentHandler> { IntentHandler() }


//    viewModelOf(::PaymentViewModel)
}

expect val targetModule: Module

fun initializeKoin(
    config: (KoinApplication.() -> Unit)? = null,
) {
    startKoin {
        config?.invoke(this)
        modules(sharedModule, targetModule)
    }
}