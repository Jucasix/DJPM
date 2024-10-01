package ipca.example.calculater

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun CalculatorScreen(modifier: Modifier = Modifier) {
    var display by remember { mutableStateOf("0") }
    var valor1 by remember { mutableStateOf("") }
    var operador by remember { mutableStateOf("") }
    var isOpenParenthesis by remember { mutableStateOf(false) }
    var operationDisplay by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Espaço para o display
        Spacer(modifier = Modifier.height(32.dp))

        // Linha superior: exibe a operação atual
        Text(
            text = operationDisplay,
            fontSize = 24.sp,
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Light,
            maxLines = 1,
            modifier = Modifier.padding(8.dp)
        )

        // Linha inferior: exibe o resultado ou o número digitado
        Text(
            text = display,
            fontSize = 48.sp,
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            modifier = Modifier.padding(8.dp),
            textAlign = Alignment.End
        )

        // Espaço para os botões
        Spacer(modifier = Modifier.height(24.dp))

        // Primeira linha: C, (), %, /
        Row {
            Button(onClick = {
                clearCalculator(
                    onUpdateDisplay = { display = it },
                    onUpdateOperador = { operador = it },
                    onUpdateValor1 = { valor1 = it },
                    onUpdateOperationDisplay = { operationDisplay = it }
                )
            }) {
                Text(text = "C")
            }
            Button(onClick = {
                if (isOpenParenthesis) {
                    onCloseParenthesis(display, onUpdateDisplay = { display = it })
                } else {
                    onOpenParenthesis(display, onUpdateDisplay = { display = it })
                }
                isOpenParenthesis = !isOpenParenthesis
            }) {
                Text(text = if (isOpenParenthesis) ")" else "(")
            }
            Button(onClick = { onPercentClick(display, onUpdateDisplay = { display = it }) }) {
                Text(text = "%")
            }
            Button(onClick = {
                operador = "/"
                valor1 = display
                display = ""
                operationDisplay = "$valor1 /"
            }) {
                Text(text = "/")
            }
        }

        // Segunda linha: 7, 8, 9, *
        Row {
            Button(onClick = { onNumberClick("7", display, onUpdateDisplay = { display = it }) }) {
                Text(text = "7")
            }
            Button(onClick = { onNumberClick("8", display, onUpdateDisplay = { display = it }) }) {
                Text(text = "8")
            }
            Button(onClick = { onNumberClick("9", display, onUpdateDisplay = { display = it }) }) {
                Text(text = "9")
            }
            Button(onClick = {
                operador = "*"
                valor1 = display
                display = ""
                operationDisplay = "$valor1 *"
            }) {
                Text(text = "*")
            }
        }

        // Terceira linha: 4, 5, 6, -
        Row {
            Button(onClick = { onNumberClick("4", display, onUpdateDisplay = { display = it }) }) {
                Text(text = "4")
            }
            Button(onClick = { onNumberClick("5", display, onUpdateDisplay = { display = it }) }) {
                Text(text = "5")
            }
            Button(onClick = { onNumberClick("6", display, onUpdateDisplay = { display = it }) }) {
                Text(text = "6")
            }
            Button(onClick = {
                operador = "-"
                valor1 = display
                display = ""
                operationDisplay = "$valor1 -"
            }) {
                Text(text = "-")
            }
        }

        // Quarta linha: 1, 2, 3, +
        Row {
            Button(onClick = { onNumberClick("1", display, onUpdateDisplay = { display = it }) }) {
                Text(text = "1")
            }
            Button(onClick = { onNumberClick("2", display, onUpdateDisplay = { display = it }) }) {
                Text(text = "2")
            }
            Button(onClick = { onNumberClick("3", display, onUpdateDisplay = { display = it }) }) {
                Text(text = "3")
            }
            Button(onClick = {
                operador = "+"
                valor1 = display
                display = ""
                operationDisplay = "$valor1 +"
            }) {
                Text(text = "+")
            }
        }

        // Última linha: +/-, 0, ., =
        Row {
            Button(onClick = { onToggleSign(display, onUpdateDisplay = { display = it }) }) {
                Text(text = "+/-")
            }
            Button(onClick = { onNumberClick("0", display, onUpdateDisplay = { display = it }) }) {
                Text(text = "0")
            }
            Button(onClick = { onDecimalClick(display, onUpdateDisplay = { display = it }) }) {
                Text(text = ".")
            }
            Button(onClick = {
                calculateResult(
                    valor1 = valor1,
                    valor2 = display,
                    operador = operador,
                    onUpdateDisplay = { display = it },
                    onUpdateOperationDisplay = { operationDisplay = it }
                )
            }) {
                Text(text = "=")
            }
        }
    }
}

// Funções auxiliares (sem alteração)
fun onNumberClick(number: String, currentDisplay: String, onUpdateDisplay: (String) -> Unit) {
    if (currentDisplay == "0") {
        onUpdateDisplay(number)
    } else {
        onUpdateDisplay(currentDisplay + number)
    }
}

fun calculateResult(
    valor1: String,
    valor2: String,
    operador: String,
    onUpdateDisplay: (String) -> Unit,
    onUpdateOperationDisplay: (String) -> Unit
) {
    val num1 = valor1.toDoubleOrNull() ?: 0.0
    val num2 = valor2.toDoubleOrNull() ?: 0.0
    val result = when (operador) {
        "+" -> num1 + num2
        "-" -> num1 - num2
        "*" -> num1 * num2
        "/" -> if (num2 != 0.0) num1 / num2 else "Erro"
        else -> 0.0
    }
    onUpdateDisplay(result.toString())
    onUpdateOperationDisplay("$valor1 $operador $valor2 =")
}

fun clearCalculator(
    onUpdateDisplay: (String) -> Unit,
    onUpdateOperador: (String) -> Unit,
    onUpdateValor1: (String) -> Unit,
    onUpdateOperationDisplay: (String) -> Unit
) {
    onUpdateDisplay("0")
    onUpdateOperador("")
    onUpdateValor1("")
    onUpdateOperationDisplay("")
}

fun onPercentClick(currentDisplay: String, onUpdateDisplay: (String) -> Unit) {
    val value = currentDisplay.toDoubleOrNull() ?: 0.0
    onUpdateDisplay((value / 100).toString())
}

fun onToggleSign(currentDisplay: String, onUpdateDisplay: (String) -> Unit) {
    val value = currentDisplay.toDoubleOrNull() ?: 0.0
    onUpdateDisplay((value * -1).toString())
}

fun onDecimalClick(currentDisplay: String, onUpdateDisplay: (String) -> Unit) {
    if (!currentDisplay.contains(".")) {
        onUpdateDisplay(currentDisplay + ".")
    }
}

fun onOpenParenthesis(currentDisplay: String, onUpdateDisplay: (String) -> Unit) {
    onUpdateDisplay(currentDisplay + "(")
}

fun onCloseParenthesis(currentDisplay: String, onUpdateDisplay: (String) -> Unit) {
    onUpdateDisplay(currentDisplay + ")")
}
