package hm.assignment.app.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import hm.assignment.app.navigation.Screen
import hm.assignment.app.screens.countries.CountriesScreen
import hm.assignment.app.screens.country.CountryScreen
import hm.assignment.app.screens.welcome.WelcomeScreen
import hm.assignment.app.util.Colors

/**
 * Created on 2022-03-04.
 * CopyrightⒸ Kagge
 */
@ExperimentalCoilApi
@ExperimentalAnimationApi
class HomeActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen()
        }
    }
}

@ExperimentalCoilApi
@ExperimentalAnimationApi
@Composable
fun HomeScreen() {
    val navigationController = rememberAnimatedNavController()
    val animDuration = 1000

    MaterialTheme(colors = Colors.EbonyColors) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Colors.EbonyColors.background)
        ) {
            AnimatedNavHost(
                navigationController,
                startDestination = Screen.Welcome.route
            ) {
                composable(
                    route = Screen.Welcome.route,
                    exitTransition = {
                        slideOutHorizontally(animationSpec = tween(animDuration))
                    }
                ) { WelcomeScreen(navController = navigationController) }
                composable(
                    route = Screen.Countries.route,
                    enterTransition = {
                        slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(animDuration))
                    },
                    exitTransition = {
                        slideOutHorizontally(targetOffsetX = { - it }, animationSpec = tween(animDuration))
                    },
                    popEnterTransition = {
                        slideInHorizontally(initialOffsetX = { -(it) }, animationSpec = tween(animDuration))
                    },
                    popExitTransition = {
                        slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(animDuration))
                    }
                ) { CountriesScreen(navController = navigationController) }
                composable(
                    route = Screen.Country.route,
                    enterTransition = {
                        slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(animDuration))
                    },
                    exitTransition = {
                        slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(animDuration))
                    },
                    popExitTransition = {
                        slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(animDuration))
                    }
                ) { backStackEntry ->
                    val countryId = backStackEntry.arguments?.getString("countryId")
                    requireNotNull(countryId) { "Need countryId to navigate!" }
                    CountryScreen(countryId = countryId)
                }
            }
        }
    }
}