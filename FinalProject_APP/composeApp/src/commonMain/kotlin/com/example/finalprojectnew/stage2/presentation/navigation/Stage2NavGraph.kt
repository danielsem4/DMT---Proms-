package com.example.finalprojectnew.stage2.presentation.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.finalprojectnew.stage2.presentation.welcome.Stage2WelcomeScreen
import com.example.finalprojectnew.stage2.presentation.welcome.Stage2InstructionsScreen
import com.example.finalprojectnew.stage2.presentation.menu.Stage2MenuScreen
import com.example.finalprojectnew.stage2.presentation.categories.Stage2CategoriesScreen
import com.example.finalprojectnew.stage2.presentation.categories.Category
import com.example.finalprojectnew.stage2.presentation.search.SearchScreen
import com.example.finalprojectnew.stage2.presentation.shoppinglist.ShoppingListScreen
import com.example.finalprojectnew.stage2.presentation.shoppinglist.DonationListScreen
import com.example.finalprojectnew.stage2.presentation.category.generic.GenericCategoryScreen
import com.example.finalprojectnew.stage2.presentation.category.generic.CategoryViewModel
import com.example.finalprojectnew.stage2.presentation.category.generic.StyledNoticeDialog
import com.example.finalprojectnew.stage2.domain.model.CategoryKey
import com.example.finalprojectnew.stage2.presentation.cart.CartScreen
import com.example.finalprojectnew.stage2.presentation.cart.CartViewModel
import com.example.finalprojectnew.stage2.presentation.finish.Stage2ThankYouScreen
import com.example.finalprojectnew.di.Stage2Locator
import finalprojectnew.composeapp.generated.resources.Res
import finalprojectnew.composeapp.generated.resources.stage2_notebook
import kotlinx.coroutines.flow.collectLatest

sealed class Stage2Route {
    data object Welcome : Stage2Route()
    data object Instructions : Stage2Route()
    data object Menu : Stage2Route()
    data object Categories : Stage2Route()
    data object Search : Stage2Route()
    data class CategoryProducts(val categoryId: String) : Stage2Route()
    data object Cart : Stage2Route()
    data object ShoppingList : Stage2Route()
    data object DonationList : Stage2Route()
    data object ThankYou : Stage2Route()
}

@Composable
fun Stage2NavGraph(
    startDestination: Stage2Route = Stage2Route.Welcome
) {
    var current by remember { mutableStateOf(startDestination) } // current = Current status of the screens

    // Before entering the Shopping/Donation list screen-
    // save the current screen so that we can return to it when we close the list.
    var returnAfterList by remember { mutableStateOf<Stage2Route?>(null) }
    fun openShoppingList() {
        returnAfterList = current; current = Stage2Route.ShoppingList
    }

    fun openDonationList() {
        returnAfterList = current; current = Stage2Route.DonationList
    }

    // make view model
    val categoryVm = remember { CategoryViewModel() }
    val cartVm = remember { CartViewModel(Stage2Locator.cart) }

// POP UP -> aunt Yaffa
// boolean that decides whether to display the alert window.
    var showAunt by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        Stage2Locator.resetStage2Alerts() // clearing previous state
        Stage2Locator.startStage2Monitors() // Enables internal monitors that send events to flow
        // For each value received → showAunt = true (display the dialog)
        Stage2Locator.globalAuntAlert.collectLatest { showAunt = true }
    }
    DisposableEffect(Unit) { onDispose { Stage2Locator.stopStage2Monitors() } }

    Box(Modifier.fillMaxSize()) {
        when (val route = current) {

            Stage2Route.Welcome ->
                Stage2WelcomeScreen(onViewListClick = { current = Stage2Route.Instructions })

            Stage2Route.Instructions ->
                Stage2InstructionsScreen(onStartShopping = { current = Stage2Route.Menu })

            Stage2Route.Menu ->
                Stage2MenuScreen(
                    onCategories = { current = Stage2Route.Categories },
                    onCart = { current = Stage2Route.Cart },
                    onSearch = { current = Stage2Route.Search },
                    onViewList = { openShoppingList() },
                    onViewDonationList = { openDonationList() }
                )

            Stage2Route.Categories ->
                Stage2CategoriesScreen(
                    onCategorySelected = { cat: Category ->
                        current = Stage2Route.CategoryProducts(cat.id)
                    },
                    onOpenCart = { current = Stage2Route.Cart },
                    onOpenSearch = { current = Stage2Route.Search },
                    onOpenDonation = { openDonationList() },
                    onOpenShoppingList = { openShoppingList() }
                )

            is Stage2Route.CategoryProducts -> {
                val key = CategoryKey.fromId(route.categoryId) // converts categoryId to CategoryKey
                if (key == null) { // if the ID is invalid → return to the categories screen (for backup)
                    current = Stage2Route.Categories
                } else {
                    GenericCategoryScreen(
                        currentKey = key,
                        vm = categoryVm,
                        onSelectCategory = { nextKey ->
                            current = Stage2Route.CategoryProducts(nextKey.id)
                        },
                        onOpenCart = { current = Stage2Route.Cart },
                        onOpenSearch = { current = Stage2Route.Search },
                        onOpenShoppingList = { openShoppingList() },
                        onOpenDonation = { openDonationList() }
                    )
                }
            }

            Stage2Route.Cart ->
                CartScreen(
                    vm = cartVm,
                    onViewList = { openShoppingList() },
                    onViewDonationList = { openDonationList() },
                    onCheckout = { current = Stage2Route.ThankYou },
                    topButtonIcon = Res.drawable.stage2_notebook,
                    onBack = { current = Stage2Route.Menu }
                )

            Stage2Route.ShoppingList ->
                ShoppingListScreen(
                    onCloseList = {
                        // When closing the list: Return to the screen we saved (if any), otherwise to the menu screen
                        current = returnAfterList ?: Stage2Route.Menu
                        returnAfterList = null
                    }
                )

            Stage2Route.DonationList ->
                DonationListScreen(
                    onCloseList = {
                        // When closing the list: Return to the screen we saved (if any), otherwise to the menu screen
                        current = returnAfterList ?: Stage2Route.Menu
                        returnAfterList = null
                    }
                )

            Stage2Route.Search ->
                SearchScreen(
                    onBack = { current = Stage2Route.Menu },
                    onOpenCart = { current = Stage2Route.Cart },
                    onOpenDonation = { openDonationList() },
                    onOpenShoppingList = { openShoppingList() }
                )

            Stage2Route.ThankYou ->
                Stage2ThankYouScreen(
                    onBackToMenu = { current = Stage2Route.Menu }
                )
        }

        if (showAunt) {
            StyledNoticeDialog(
                title = ":דודה יפה מבקשת לקנות",
                message = "יש להוסיף לרשימת הקניות \n\n" +
                        " אקונומיקה ומנקה חלונות",
                image = null,
                primaryText = "OK",
                onPrimary = { showAunt = false }
            )
        }
    }
}
