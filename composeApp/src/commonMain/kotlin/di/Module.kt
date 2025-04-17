package di

import com.clock.test.di.CDT_module
import org.koin.dsl.module

val appModule = module {
    // Define your app-specific dependencies here
    // For example, you can define a ViewModel or a repository
    // viewModel { MyViewModel(get()) }
    // single { MyRepository() }
    includes(
        CDT_module
    )
}