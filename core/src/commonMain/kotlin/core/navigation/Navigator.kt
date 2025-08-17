package core.navigation

interface Navigator{
    fun navigate(to: AppDestination)
    fun back()
}