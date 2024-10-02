package ipca.example.calculater

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import ipca.example.calculater.ui.components.CalcButton

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
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = operationDisplay,
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            fontWeight = FontWeight.Light,
            maxLines = 1
        )

        Text(
            text = display,
            fontSize = 48.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            textAlign = TextAlign.End
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Primeira linha: C, (), %, /
        Row {
            CalcButton(text = "C", isOperator = true, onClick = {
                clearCalculator(
                    onUpdateDisplay = { display = it },
                    onUpdateOperador = { operador = it },
                    onUpdateValor1 = { valor1 = it },
                    onUpdateOperationDisplay = { operationDisplay = it }
                )
            })
            CalcButton(text = if (isOpenParenthesis) ")" else "(", isOperator = true, onClick = {
                if (isOpenParenthesis) {
                    onCloseParenthesis(display, onUpdateDisplay = { display = it })
                } else {
                    onOpenParenthesis(display, onUpdateDisplay = { display = it })
                }
                isOpenParenthesis = !isOpenParenthesis
            })
            CalcButton(text = "%", isOperator = true, onClick = {
                onPercentClick(display, onUpdateDisplay = { display = it })
            })
            CalcButton(text = "/", isOperator = true, onClick = {
                operador = "/"
                valor1 = display
                display = ""
                operationDisplay = "$valor1 /"
            })
        }

        // Segunda linha: 7, 8, 9, *
        Row {
            CalcButton(text = "7", onClick = { onNumberClick("7", display, onUpdateDisplay = { display = it }) })
            CalcButton(text = "8", onClick = { onNumberClick("8", display, onUpdateDisplay = { display = it }) })
            CalcButton(text = "9", onClick = { onNumberClick("9", display, onUpdateDisplay = { display = it }) })
            CalcButton(text = "*", isOperator = true, onClick = {
                operador = "*"
                valor1 = display
                display = ""
                operationDisplay = "$valor1 *"
            })
        }

        // Terceira linha: 4, 5, 6, -
        Row {
            CalcButton(text = "4", onClick = { onNumberClick("4", display, onUpdateDisplay = { display = it }) })
            CalcButton(text = "5", onClick = { onNumberClick("5", display, onUpdateDisplay = { display = it }) })
            CalcButton(text = "6", onClick = { onNumberClick("6", display, onUpdateDisplay = { display = it }) })
            CalcButton(text = "-", isOperator = true, onClick = {
                operador = "-"
                valor1 = display
                display = ""
                operationDisplay = "$valor1 -"
            })
        }

        // Quarta linha: 1, 2, 3, +
        Row {
            CalcButton(text = "1", onClick = { onNumberClick("1", display, onUpdateDisplay = { display = it }) })
            CalcButton(text = "2", onClick = { onNumberClick("2", display, onUpdateDisplay = { display = it }) })
            CalcButton(text = "3", onClick = { onNumberClick("3", display, onUpdateDisplay = { display = it }) })
            CalcButton(text = "+", isOperator = true, onClick = {
                operador = "+"
                valor1 = display
                display = ""
                operationDisplay = "$valor1 +"
            })
        }

        // Última linha: +/-, 0, ., =
        Row {
            CalcButton(text = "+/-", fontSize = 18.sp, isOperator = true, onClick = {
                onToggleSign(display, onUpdateDisplay = { display = it })
            })
            CalcButton(text = "0", onClick = { onNumberClick("0", display, onUpdateDisplay = { display = it }) })
            CalcButton(text = ".", onClick = { onDecimalClick(display, onUpdateDisplay = { display = it }) })
            CalcButton(text = "=", isOperator = true, onClick = {
                calculateResult(
                    valor1 = valor1,
                    valor2 = display,
                    operador = operador,
                    onUpdateDisplay = { display = it },
                    onUpdateOperationDisplay = { operationDisplay = it }
                )
            })
        }
    }
}



// Funções auxiliares
fun onNumberClick(number: String, currentDisplay: String, onUpdateDisplay: (String) -> Unit) {
    if (currentDisplay == "0") {
        onUpdateDisplay(number)
    } else {
        onUpdateDisplay(currentDisplay + number)
    }
}

fun onOpenParenthesis(currentDisplay: String, onUpdateDisplay: (String) -> Unit) {
    onUpdateDisplay(currentDisplay + "(")
}

fun onCloseParenthesis(currentDisplay: String, onUpdateDisplay: (String) -> Unit) {
    onUpdateDisplay(currentDisplay + ")")
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
        "/" -> if (num2 != 0.0) num1 / num2 else Double.NaN
        else -> 0.0
    }

    // Arredondar o resultado para no máximo 6 casas decimais
    val roundedResult = String.format("%.6f", result).toDouble()

    onUpdateDisplay(roundedResult.toString())
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
