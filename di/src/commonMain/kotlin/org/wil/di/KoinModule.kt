package org.wil.di


import com.company.auth.AuthViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.wil.admin_panel.AdminPanelViewModel
import org.wil.data.domain.CustomerRepository
import org.wil.data.domain.CustomerRepositoryImpl
import org.wil.home.HomeGraphViewModel
import org.wil.nutrisport.ProfileViewModel

val sharedModule = module {
    single<CustomerRepository> { CustomerRepositoryImpl() }
    viewModelOf(::AuthViewModel)
    viewModelOf(::HomeGraphViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::AdminPanelViewModel)
//    single<AdminRepository> { AdminRepositoryImpl() }
//    single<ProductRepository> { ProductRepositoryImpl() }
//    single<OrderRepository> { OrderRepositoryImpl(get()) }
//    single<IntentHandler> { IntentHandler() }
//    single<PaypalApi> { PaypalApi() }

//    viewModelOf(::HomeGraphViewModel)
//    viewModelOf(::ManageProductViewModel)
//    viewModelOf(::ProductsOverviewViewModel)
//    viewModelOf(::DetailsViewModel)
//    viewModelOf(::CartViewModel)
//    viewModelOf(::CategorySearchViewModel)
//    viewModelOf(::CheckoutViewModel)
//    viewModelOf(::PaymentViewModel)
}

//expect val targetModule: Module

fun initializeKoin(
    config: (KoinApplication.() -> Unit)? = null,
) {
    startKoin {
        config?.invoke(this)
        modules(sharedModule)
    }
}