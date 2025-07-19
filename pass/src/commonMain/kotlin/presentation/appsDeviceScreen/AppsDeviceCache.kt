package presentation.appsDeviceScreen

object AppDeviceProgressCache {
    var didNothing = -1
    var wrongApp = 0
    var isSecondInstructions = false

    fun resetAppDevice() {
        didNothing = -1
        wrongApp = 0
        isSecondInstructions = false
    }
}

object AppProgressCache {
    var didNothing = 0
    var didNothingSecondTime = 0
    var isSecondTimeWrongApp = false

    fun resetWrongApp() {
        didNothing = 0
        didNothingSecondTime = 0
        isSecondTimeWrongApp = false
    }
}


