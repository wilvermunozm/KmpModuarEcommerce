package org.wil.di


import com.company.auth.AuthViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.wil.data.domain.CustomerRepository
import org.wil.data.domain.CustomerRepositoryImpl

val sharedModule = module {
    single<CustomerRepository> { CustomerRepositoryImpl() }
    viewModelOf(::AuthViewModel)
//    single<AdminRepository> { AdminRepositoryImpl() }
//    single<ProductRepository> { ProductRepositoryImpl() }
//    single<OrderRepository> { OrderRepositoryImpl(get()) }
//    single<IntentHandler> { IntentHandler() }
//    single<PaypalApi> { PaypalApi() }

//    viewModelOf(::HomeGraphViewModel)
//    viewModelOf(::ProfileViewModel)
//    viewModelOf(::ManageProductViewModel)
//    viewModelOf(::AdminPanelViewModel)
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