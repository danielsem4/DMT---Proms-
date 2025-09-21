package org.example.hit.heal

import androidx.compose.ui.window.ComposeUIViewController
import org.example.hit.heal.di.initKoin
import platform.UIKit.UINavigationController
import platform.UIKit.UIViewController


fun MainViewController(): UIViewController {
    val composeController = ComposeUIViewController(
        configure = {
            initKoin()
        }
    ) {
        App()
    }
    val navigationController = UINavigationController(rootViewController = composeController)
    navigationController.navigationBarHidden = true
    return navigationController
}