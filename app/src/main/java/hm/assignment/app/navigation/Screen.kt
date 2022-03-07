package hm.assignment.app.navigation

/**
 * Created on 2022-03-05.
 * CopyrightⒸ Kagge
 */
sealed class Screen(val route: String) {
    object Welcome: Screen(Destinations.Welcome)
    object Countries: Screen(Destinations.Countries)
    object Country: Screen("${Destinations.Country}/{countryId}") {
        fun createRoute(countryId: String) = "${Destinations.Country}/$countryId"
    }
    object Favourites: Screen(Destinations.Favourites)
}