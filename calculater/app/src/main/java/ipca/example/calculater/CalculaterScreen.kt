package ipca.example.calculater

import androidx.compose.foundation.layout.* // Já está importado
import androidx.compose.foundation.shape.CircleShape // Importa a forma circular
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults // Para alterar as cores do botão
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
        horizontalAlignment = Alignment.CenterHorizontally // Centralizando conteúdo
    ) {
        // Espaço para o display
        Spacer(modifier = Modifier.height(32.dp))

        // Linha superior: exibe a operação atual
        Text(
            text = operationDisplay,
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            fontWeight = FontWeight.Light,
            maxLines = 1
        )

        // Linha inferior: exibe o resultado ou o número digitado
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

        // Espaço para os botões
        Spacer(modifier = Modifier.height(24.dp))

        // Primeira linha: C, (), %, /
        Row {
            CalcButton(text = "C") {
                clearCalculator(
                    onUpdateDisplay = { display = it },
                    onUpdateOperador = { operador = it },
                    onUpdateValor1 = { valor1 = it },
                    onUpdateOperationDisplay = { operationDisplay = it }
                )
            }
            CalcButton(text = if (isOpenParenthesis) ")" else "(") {
                if (isOpenParenthesis) {
                    onCloseParenthesis(display, onUpdateDisplay = { display = it })
                } else {
                    onOpenParenthesis(display, onUpdateDisplay = { display = it })
                }
                isOpenParenthesis = !isOpenParenthesis
            }
            CalcButton(text = "%") {
                onPercentClick(display, onUpdateDisplay = { display = it })
            }
            CalcButton(text = "/") {
                operador = "/"
                valor1 = display
                display = ""
                operationDisplay = "$valor1 /"
            }
        }

        // Segunda linha: 7, 8, 9, *
        Row {
            CalcButton(text = "7") { onNumberClick("7", display, onUpdateDisplay = { display = it }) }
            CalcButton(text = "8") { onNumberClick("8", display, onUpdateDisplay = { display = it }) }
            CalcButton(text = "9") { onNumberClick("9", display, onUpdateDisplay = { display = it }) }
            CalcButton(text = "*") {
                operador = "*"
                valor1 = display
                display = ""
                operationDisplay = "$valor1 *"
            }
        }

        // Terceira linha: 4, 5, 6, -
        Row {
            CalcButton(text = "4") { onNumberClick("4", display, onUpdateDisplay = { display = it }) }
            CalcButton(text = "5") { onNumberClick("5", display, onUpdateDisplay = { display = it }) }
            CalcButton(text = "6") { onNumberClick("6", display, onUpdateDisplay = { display = it }) }
            CalcButton(text = "-") {
                operador = "-"
                valor1 = display
                display = ""
                operationDisplay = "$valor1 -"
            }
        }

        // Quarta linha: 1, 2, 3, +
        Row {
            CalcButton(text = "1") { onNumberClick("1", display, onUpdateDisplay = { display = it }) }
            CalcButton(text = "2") { onNumberClick("2", display, onUpdateDisplay = { display = it }) }
            CalcButton(text = "3") { onNumberClick("3", display, onUpdateDisplay = { display = it }) }
            CalcButton(text = "+") {
                operador = "+"
                valor1 = display
                display = ""
                operationDisplay = "$valor1 +"
            }
        }

        // Última linha: +/-, 0, ., =
        Row {
            // Diminuindo a fonte do botão "+/-"
            CalcButton(text = "+/-", fontSize = 18.sp) { onToggleSign(display, onUpdateDisplay = { display = it }) }
            CalcButton(text = "0") { onNumberClick("0", display, onUpdateDisplay = { display = it }) }
            CalcButton(text = ".") { onDecimalClick(display, onUpdateDisplay = { display = it }) }
            CalcButton(text = "=") {
                calculateResult(
                    valor1 = valor1,
                    valor2 = display,
                    operador = operador,
                    onUpdateDisplay = { display = it },
                    onUpdateOperationDisplay = { operationDisplay = it }
                )
            }
        }
    }
}

@Composable
fun CalcButton(text: String, fontSize: TextUnit = 24.sp, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = CircleShape, // Botões redondos
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Blue // Botões de cor azul
        ),
        modifier = Modifier
            .padding(8.dp)
            .size(64.dp) // Tamanho do botão (largura e altura iguais)
    ) {
        Text(text = text, color = Color.White, fontSize = fontSize)
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
    // Se o número já tiver um ponto decimal, não faz nada
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

