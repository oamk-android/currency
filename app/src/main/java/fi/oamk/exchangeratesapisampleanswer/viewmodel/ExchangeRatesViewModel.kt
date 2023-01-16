package fi.oamk.exchangeratesapisampleanswer.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ExchangeRatesViewModel: ViewModel() {
    var eurInput by mutableStateOf("")
    var gbp by mutableStateOf(0.0)
        private set

    fun changeEur(newValue: String) {
        eurInput = newValue
    }

    fun convert() {
        val euros = eurInput.toDoubleOrNull() ?: 0.0
        gbp = euros * 0.9
    }
}