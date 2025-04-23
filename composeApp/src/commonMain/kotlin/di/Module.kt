package di

import org.example.hit.heal.cdt.di.CDT_module
import org.example.hit.heal.cdt.di.platformModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

val appModule = module {
    // Define your app-specific dependencies here
    includes(CDT_module)
}

fun initKoin(config: KoinAppDeclaration? = null) =
    // Start Koin with the app module
    startKoin {
        config?.invoke(this)
        modules(appModule, platformModule)
    }
