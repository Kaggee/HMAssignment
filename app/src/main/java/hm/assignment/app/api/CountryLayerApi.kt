package hm.assignment.app.api

import hm.assignment.app.api.models.CountryModel
import retrofit2.http.GET

/**
 * Created on 2022-03-04.
 * CopyrightⒸ Kagge
 */
interface CountryLayerApi {
    @GET("all")
    suspend fun getAllCountries(): List<CountryModel>
}