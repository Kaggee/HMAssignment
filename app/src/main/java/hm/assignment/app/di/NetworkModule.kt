package hm.assignment.app.di

import hm.assignment.app.BuildConfig
import hm.assignment.app.api.CountryLayerApi
import hm.assignment.app.util.HMInterceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created on 2022-03-04.
 * CopyrightⒸ Kagge
 */


val networkModule = module {
    single { provideOkHttpClient() }
    single { provideCountryLayerApi(provideRetrofit(get())) }
}

fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient().newBuilder().addInterceptor(HMInterceptor()).build()
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(BuildConfig.COUNTRY_LAYER_BASE_URL).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideCountryLayerApi(retrofit: Retrofit): CountryLayerApi = retrofit.create(CountryLayerApi::class.java)