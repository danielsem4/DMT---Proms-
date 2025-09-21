import SwiftUI

@main
struct iOSApp: App {
    // This line links the AppDelegate to your SwiftUI app lifecycle.
    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}