package core.utils

import androidx.compose.runtime.*
import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSOperationQueue
import platform.UIKit.UIApplicationDidEnterBackgroundNotification
import platform.UIKit.UIApplicationWillEnterForegroundNotification
import platform.darwin.NSObjectProtocol

@Composable
actual fun ObserveLifecycle(
    onStop: () -> Unit,
    onStart: () -> Unit
) {
    DisposableEffect(Unit) {
        val notificationCenter = NSNotificationCenter.defaultCenter

        val backgroundObserver: NSObjectProtocol = notificationCenter.addObserverForName(
            name = UIApplicationDidEnterBackgroundNotification,
            `object` = null,
            queue = NSOperationQueue.mainQueue
        ) {
            onStop()
        }

        val foregroundObserver: NSObjectProtocol = notificationCenter.addObserverForName(
            name = UIApplicationWillEnterForegroundNotification,
            `object` = null,
            queue = NSOperationQueue.mainQueue
        ) {
            onStart()
        }

        onDispose {
            notificationCenter.removeObserver(backgroundObserver)
            notificationCenter.removeObserver(foregroundObserver)
        }
    }
}
