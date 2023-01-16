package fi.oamk.exchangeratesapisampleanswer.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import fi.oamk.exchangeratesapisampleanswer.R
import fi.oamk.exchangeratesapisampleanswer.ui.theme.ExchangeRatesAPISampleAnswerTheme
import fi.oamk.exchangeratesapisampleanswer.viewmodel.ExchangeRatesUIState
import fi.oamk.exchangeratesapisampleanswer.viewmodel.ExchangeRatesViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExchangeRatesAPISampleAnswerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CalculatorApp()
                }
            }
        }
    }
}

@Composable
fun CalculatorApp(exchangeRatesViewModel: ExchangeRatesViewModel = viewModel()) {
    when (exchangeRatesViewModel.exchangeRatesUIState) {
        is ExchangeRatesUIState.Success ->
            CalculatorScreen(
                eurInput = exchangeRatesViewModel.eurInput,
                gbp = exchangeRatesViewModel.gbp,
                changeEur = { exchangeRatesViewModel.changeEur(it) },
                convert = { exchangeRatesViewModel.convert() }
            )
        is ExchangeRatesUIState.Loading -> LoadingScreen()
        is ExchangeRatesUIState.Error -> ErrorScreen()
    }
}


@Composable
fun CalculatorScreen(eurInput: String, gbp: Double,changeEur:(value: String)-> Unit,convert:()->Unit) {
    Column (
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = stringResource(R.string.currency_calculator),
            color = MaterialTheme.colors.primary,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            label = {Text(text = stringResource(R.string.enter_euros))},
            value= eurInput,
            onValueChange = { changeEur(it.replace(',','.')) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(R.string.result,String.format("%.2f",gbp)
                .replace(',','.')),
            modifier = Modifier.padding(start = 16.dp)
        )
        Button(
            onClick = {
                convert()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.calculate))
        }
    }
}

@Composable
fun ErrorScreen() {
    Text("Error retrieving exchange rates. Converter cannot be used.")
}

@Composable
fun LoadingScreen() {
    Text("Loading...")
}