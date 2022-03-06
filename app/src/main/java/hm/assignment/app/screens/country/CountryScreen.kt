package hm.assignment.app.screens.country

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import hm.assignment.app.BuildConfig
import hm.assignment.app.R
import hm.assignment.app.api.models.CountryModel
import hm.assignment.app.screens.Loading
import hm.assignment.app.util.UiState
import hm.assignment.app.util.log
import org.koin.androidx.compose.viewModel

/**
 * Created on 2022-03-05.
 * CopyrightⒸ Kagge
 */


@ExperimentalCoilApi
@Composable
fun CountryScreen(countryId: String) {
    val countryViewModel by viewModel<CountryViewModel>()
    countryViewModel.getCountry(countryId)

    when(val state = countryViewModel.uiState.collectAsState().value) {
        UiState.Loading -> Loading()
        is UiState.Success -> {
            CountrySuccess(country = state.data)
        }
    }
}

@ExperimentalCoilApi
@Composable
fun CountrySuccess(country: CountryModel) {
    Column (
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {
        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = 10.dp,
            backgroundColor = Color.White
        ) {
            ConstraintLayout(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)) {

                val (title, capital, region, image) = createRefs()
                Text(
                    text = country.name,
                    fontSize = 20.sp,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.constrainAs(title) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(image.start, margin = 8.dp)
                        width = Dimension.fillToConstraints
                    }
                )
                Text(
                    text = stringResource(id = R.string.capital, country.capital),
                    modifier = Modifier.constrainAs(capital) {
                        top.linkTo(title.bottom, margin = 4.dp)
                        start.linkTo(parent.start)
                        end.linkTo(image.start, margin = 8.dp)
                        width = Dimension.fillToConstraints
                    }
                )
                Text(
                    text = stringResource(id = R.string.region, country.region),
                    modifier = Modifier.constrainAs(region) {
                        top.linkTo(capital.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(image.start, margin = 8.dp)
                        width = Dimension.fillToConstraints
                    }
                )
                AsyncImage(
                    model = "${BuildConfig.COUNTRY_FLAGS_IMAGE_URL}${country.alpha2Code}",
                    placeholder = painterResource(id = R.drawable.loading),
                    contentDescription = "",
                    modifier = Modifier.constrainAs(image) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                )
            }
        }
    }
}

@ExperimentalCoilApi
@Preview
@Composable
fun Preview_CountryItem() {
    CountrySuccess(CountryModel("Sweden", "Stockholm", "Europe", "SE"))
}