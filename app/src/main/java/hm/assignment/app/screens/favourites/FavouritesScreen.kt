package hm.assignment.app.screens.favourites

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import hm.assignment.app.BuildConfig
import hm.assignment.app.R
import hm.assignment.app.api.models.CountryModel
import hm.assignment.app.screens.Loading
import hm.assignment.app.util.Colors
import hm.assignment.app.util.UiState
import org.koin.androidx.compose.viewModel

/**
 * Created on 2022-03-07.
 * CopyrightⒸ Kagge
 */

@ExperimentalFoundationApi
@Composable
fun FavouritesScreen() {
    val vm by viewModel<FavouriteViewModel>()

    when(val state = vm.uiState.collectAsState().value) {
        is UiState.Loading -> {
            Loading()
            vm.getFavourites()
        }
        is UiState.Success -> {
            FavouriteSuccess(countries = state.data)
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun FavouriteSuccess (
    countries: List<CountryModel>
) {
    LazyVerticalGrid(
        cells = GridCells.Adaptive(150.dp),
        content = {
            items(countries.size) { index ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(4.dp)
                ) {
                    FavouriteItem(country = countries[index])
                }
            }
        }
    )
}

@Composable
fun FavouriteItem(country: CountryModel) {
    ConstraintLayout(
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ) {
        val (countryFlag, countryName, countryCapital, countryRegion) = createRefs()
        AsyncImage(
            model = "${BuildConfig.COUNTRY_FLAGS_IMAGE_URL}${country.alpha2Code}",
            placeholder = painterResource(id = R.drawable.loading),
            error = painterResource(id = R.drawable.broken_image),
            contentDescription = "",
            modifier = Modifier
                .constrainAs(countryFlag) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top, margin = 8.dp)
                    end.linkTo(parent.end)
                }
                .size(50.dp)
        )
        Text(
            text = country.name,
            fontSize = 20.sp,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center,
            modifier = Modifier.constrainAs(countryName) {
                top.linkTo(countryFlag.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.wrapContent
            }
        )
        Text(
            text = stringResource(id = R.string.capital, country.capital),
            textAlign = TextAlign.Center,
            modifier = Modifier.constrainAs(countryCapital) {
                top.linkTo(countryName.bottom, margin = 4.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.wrapContent
            }
        )
        Text(
            text = stringResource(id = R.string.region, country.region),
            textAlign = TextAlign.Center,
            modifier = Modifier.constrainAs(countryRegion) {
                top.linkTo(countryCapital.bottom, margin = 4.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom, margin = 8.dp)
                width = Dimension.wrapContent
            }
        )
    }
}

@Preview
@Composable
fun Preview_FavouriteItem() {
    val country = CountryModel("Sweden", "Stockholm", "Europe", "SE", true)
    FavouriteItem(country = country)
}