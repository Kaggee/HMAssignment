package hm.assignment.app.screens.countries

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import hm.assignment.app.R
import hm.assignment.app.api.models.CountryModel
import hm.assignment.app.navigation.Screen
import hm.assignment.app.screens.ErrorScreen
import hm.assignment.app.screens.Loading
import hm.assignment.app.util.Colors
import hm.assignment.app.util.UiState
import hm.assignment.app.util.log
import org.koin.androidx.compose.viewModel

/**
 * Created on 2022-03-05.
 * CopyrightⒸ Kagge
 */

@Composable
fun CountriesScreen(navController: NavController) {
    val viewModel by viewModel<CountriesViewModel>()

    when(viewModel.uiState.collectAsState().value) {
        UiState.Loading -> {
            Loading()
            viewModel.getCountries()
        }
        is UiState.Success -> CountriesSuccess(
            navController = navController,
            regionSelected = viewModel.getFilterRegion(),
            onFilterRegion = {
                viewModel.filterByRegion(it)
            },
            onSearchRegion = {
                viewModel.filterByWord(it)
            }
        )
        UiState.ApiError -> ErrorScreen(
            title = stringResource(id = R.string.error_generic_title),
            desc = stringResource(id = R.string.error_generic_desc)
        ) {
            viewModel.setStateLoading()
        }
        UiState.NetworkError -> ErrorScreen(
            title = stringResource(id = R.string.error_network_title),
            desc = stringResource(id = R.string.error_network_desc)
        ) {
            viewModel.setStateLoading()
        }
    }
}

@Composable
fun CountriesSuccess (
    navController: NavController?,
    regionSelected: String,
    onSearchRegion: (String) -> Unit,
    onFilterRegion: (String) -> Unit)
{
    val viewModel by viewModel<CountriesViewModel>()
    var searchValue by remember { mutableStateOf(viewModel.getSearchWord()) }
    var isNavigating by remember { mutableStateOf(false) }
    var mListItems: List<CountryModel> by remember { mutableStateOf(viewModel.getCurrentCountries()) }

    mListItems = when(val state = viewModel.objectState.collectAsState().value) {
        is UiState.Success -> state.data
        else -> viewModel.getCurrentCountries()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, top = 4.dp, end = 4.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            ConstraintLayout(modifier = Modifier
                .fillMaxWidth()
            ) {
                val (dropdownConstraint, buttonConstraint) = createRefs()

                Box (
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .border(BorderStroke(1.dp, Color.White))
                        .constrainAs(dropdownConstraint) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                ) {
                    GetRegionDropdown(regionSelected, onFilterRegion)
                }

                TextButton(
                    onClick = {
                        if(!isNavigating) {
                            isNavigating = true
                            navController?.navigate(Screen.Favourites.route)
                        }
                    },
                    modifier = Modifier.constrainAs(buttonConstraint) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "Favourite",
                        tint = Colors.EbonyColors.secondary
                    )
                    Text(
                        stringResource(id = R.string.go_to_favourites),
                        color = Color.White,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp)
        ) {
            OutlinedTextField(
                value = searchValue,
                onValueChange = {
                    searchValue = it
                    onSearchRegion.invoke(it)
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Colors.EbonyColors.secondaryVariant,
                    focusedLabelColor = Color.White,
                    cursorColor = Color.White,
                    textColor = Color.White
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                label = {
                    Text(
                        text = stringResource(id = R.string.search),
                        color = Color.White
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
        Row (
            modifier = Modifier.fillMaxWidth()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 4.dp)
            ) {
                mListItems.forEach { country ->
                    item {
                        GetCountryItem(country) {
                            if(!isNavigating) {
                                isNavigating = true
                                navController?.navigate(Screen.Country.createRoute(country.name))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GetRegionDropdown(regionSelected: String, onSelectRegion: (String) -> Unit) {
    val viewModel by viewModel<CountriesViewModel>()
    var expanded by remember { mutableStateOf(false) }
    val items = viewModel.getAllRegions()

    IconButton(
        onClick = { expanded = true },
        modifier = Modifier.padding(start = 8.dp, end = 8.dp)
    ) {
        Row {
            Text(
                modifier = Modifier.padding(end = 2.dp),
                text = stringResource(id = R.string.region_dropdown_filter, regionSelected),
                color = Color.White
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                tint = Color.White,
                contentDescription = "Region Filter",
                modifier = Modifier.padding(start = 2.dp)
            )
        }
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        items.forEach { region ->
            DropdownMenuItem(onClick = {
                expanded = false
                onSelectRegion.invoke(region)
            }) {
                Text(region)
            }
        }
    }
}

@Composable
fun GetCountryItem(country: CountryModel, onNavigate: () -> Unit) {
    val vm by viewModel<CountriesViewModel>()
    var isFavourite by remember { mutableStateOf(country.favourite) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 1.dp, bottom = 1.dp, start = 8.dp, end = 8.dp),
        shape = RoundedCornerShape(4.dp),
        elevation = 20.dp,
        backgroundColor = Colors.EbonyColors.primaryVariant,
        onClick = {
            onNavigate.invoke()
        }
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth(),
        ) {
            val (textConstraint, iconConstraint, favouriteConstraint) = createRefs()
            Text(
                text = country.name,
                modifier = Modifier.constrainAs(textConstraint) {
                    start.linkTo(parent.start, margin = 8.dp)
                    top.linkTo(parent.top, margin = 8.dp)
                    bottom.linkTo(parent.bottom, margin = 8.dp)
                    end.linkTo(iconConstraint.start, margin = 8.dp)
                    width = Dimension.fillToConstraints
                },
                color = Color.White
            )
            IconToggleButton(
                checked = isFavourite,
                onCheckedChange = {
                    isFavourite = it
                    vm.setFavourite(country.name, isFavourite)
                },
                modifier = Modifier.constrainAs(favouriteConstraint) {
                    end.linkTo(iconConstraint.start, margin = 8.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
            ) {
                val transition = updateTransition(isFavourite, label = "Favourite")
                val tint by transition.animateColor(
                    label = "Favourite Tint"
                ) { isChecked ->
                    if(isChecked) Colors.EbonyColors.secondary else Colors.EbonyColors.secondaryVariant
                }

                Icon(
                    imageVector = if (isFavourite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Favourite",
                    tint = tint
                )
            }
            Icon(
                Icons.Rounded.ArrowForward,
                modifier = Modifier.constrainAs(iconConstraint) {
                    end.linkTo(parent.end, margin = 8.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
                tint = Color.White,
                contentDescription = "")
        }
    }
}

@Preview
@Composable
fun Preview_CountryItem() {
    /*val countryList = ArrayList<CountryModel>()
    countryList.add(CountryModel("TestCountry", "TestCapital", "TestRegion", "SE"))
    countryList.add(CountryModel("TestCountry", "TestCapital", "TestRegion", "SE"))
    countryList.add(CountryModel("TestCountry", "TestCapital", "TestRegion", "SE"))
    CountriesSuccess(navController = null, countries = countryList, "") {}*/
    GetCountryItem(country = CountryModel("TestCountry", "TestCapital", "TestRegion", "SE")) {

    }
}