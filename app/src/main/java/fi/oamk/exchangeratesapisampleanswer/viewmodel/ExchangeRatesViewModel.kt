package fi.oamk.exchangeratesapisampleanswer.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fi.oamk.exchangeratesapisampleanswer.model.ExchangeRatesApi
import kotlinx.coroutines.launch

sealed interface ExchangeRatesUIState {
    object Success: ExchangeRatesUIState
    object Error: ExchangeRatesUIState
    object Loading: ExchangeRatesUIState
}


class ExchangeRatesViewModel: ViewModel() {
    var eurInput by mutableStateOf("")
    var gbp by mutableStateOf(0.0)
        private set
    var gbpRate by mutableStateOf(0.0f)
        private set
    var exchangeRatesUIState by mutableStateOf<ExchangeRatesUIState>(ExchangeRatesUIState.Loading)
        private set

    init {
        getExchangeRateForGbp()
    }

    fun changeEur(newValue: String) {
        eurInput = newValue
    }

    fun convert() {
        val euros = eurInput.toDoubleOrNull() ?: 0.0
        gbp = euros * gbpRate
    }

    private fun getExchangeRateForGbp() {
        viewModelScope.launch {
            var exchangeRatesApi: ExchangeRatesApi? = null

            try {
                exchangeRatesApi = ExchangeRatesApi.getInstance()
                val rates =  exchangeRatesApi!!.getRates()
                if (rates.success) {
                    gbpRate = rates.rates["GBP"]!!
                    exchangeRatesUIState = ExchangeRatesUIState.Success
                } else {
                    exchangeRatesUIState = ExchangeRatesUIState.Error
                }
            } catch (e: Exception) {
                Log.d("VIEWMODEL",e.message.toString())
                gbpRate = 0.0f
                exchangeRatesUIState = ExchangeRatesUIState.Error
            }
        }
    }
}