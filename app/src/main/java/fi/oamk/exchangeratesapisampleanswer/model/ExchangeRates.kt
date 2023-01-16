package fi.oamk.exchangeratesapisampleanswer.model

import com.google.gson.internal.LinkedTreeMap
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.Date

data class ExchangeRates (
    var success: Boolean,
    var data: Date,
    var rates: LinkedTreeMap<String,Float>
)

const val BASE_URL = "https://api.exchangerate.host"

interface ExchangeRatesApi {
    @GET("latest")
    suspend fun getRates(): ExchangeRates

    companion object {
        var exchangeRatesService: ExchangeRatesApi? = null

        fun getInstance(): ExchangeRatesApi {
            if (exchangeRatesService === null) {
                exchangeRatesService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(ExchangeRatesApi::class.java)
            }
            return exchangeRatesService!!
        }
    }
}