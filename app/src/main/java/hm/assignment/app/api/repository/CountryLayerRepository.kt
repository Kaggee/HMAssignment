package hm.assignment.app.api.repository

import hm.assignment.app.api.ApiResultWrapper
import hm.assignment.app.api.BaseApiRepository
import hm.assignment.app.api.CountryLayerApi
import hm.assignment.app.api.models.CountryModel

/**
 * Created on 2022-03-04.
 * CopyrightⒸ Kagge
 */
class CountryLayerRepository(private val countryLayerApi: CountryLayerApi): BaseApiRepository() {

    // Gets all countries!
    suspend fun getAllCountries(): ApiResultWrapper<List<CountryModel>> {
        return safeApiCall { countryLayerApi.getAllCountries() }
    }
}