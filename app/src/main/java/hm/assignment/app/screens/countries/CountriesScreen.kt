package hm.assignment.app.screens.countries

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

    when(val state = viewModel.uiState.collectAsState().value) {
        UiState.Loading -> {
            Loading()
            viewModel.getCountries()
        }
        is UiState.Success -> CountriesSuccess(
            navController = navController,
            countries = state.data,
            regionSelected = viewModel.getFilterRegion(),
            onFilterRegion = {
                viewModel.filterByRegion(it)
            },
            onSearchRegion = {
                viewModel.filterByWord(it)
            }
        )
    }
}

@Composable
fun CountriesSuccess (
    navController: NavController?,
    countries: List<CountryModel>,
    regionSelected: String,
    onSearchRegion: (String) -> Unit,
    onFilterRegion: (String) -> Unit)
{
    val viewModel by viewModel<CountriesViewModel>()
    var searchValue by remember { mutableStateOf(viewModel.getSearchWord()) }
    var mListItems: List<CountryModel> by remember { mutableStateOf(listOf()) }

    mListItems = when(val state = viewModel.objectState.collectAsState().value) {
        is UiState.Success -> state.data
        else -> countries
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            GetRegionDropdown(regionSelected, onFilterRegion)
        }
        Row(
            modifier = Modifier.fillMaxWidth()
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
                modifier = Modifier.fillMaxSize()
            ) {
                mListItems.forEach { country ->
                    item {
                        GetCountryItem(navController, country)
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

    Box(
        modifier = Modifier.wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(
            onClick = { expanded = true }
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
}

@Composable
fun GetCountryItem(navController: NavController?, country: CountryModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 1.dp, bottom = 1.dp, start = 8.dp, end = 8.dp),
        shape = RoundedCornerShape(4.dp),
        elevation = 20.dp,
        backgroundColor = Colors.EbonyColors.primaryVariant,
        onClick = {
            navController?.navigate(Screen.Country.createRoute(country.name))
        }
    ) {
        val icons = Icons.Rounded
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth(),
        ) {
            val (textConstraint, iconConstraint) = createRefs()
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
            Icon(
                icons.ArrowForward,
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
    GetCountryItem(navController = null, country = CountryModel("TestCountry", "TestCapital", "TestRegion", "SE"))
}