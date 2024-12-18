package ipca.example.myshoppinglist.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ipca.example.myshoppinglist.ui.theme.MyShoppingListTheme

@Composable
fun LoginView(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    onLoginSuccess: () -> Unit = {}
) {
    val viewModel: LoginViewModel = viewModel()
    val state = viewModel.state.value

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "My Shopping Lists",
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            TextField(
                value = state.email,
                onValueChange = { viewModel.onEmailChange(it) },
                placeholder = { Text("Email") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = state.password,
                onValueChange = { viewModel.onPasswordChange(it) },
                placeholder = { Text("Password") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    viewModel.onLoginClick {
                        onLoginSuccess()
                    }
                },
                content = { Text("Login") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { navController.navigate("register") },
                content = { Text("Register") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { viewModel.onResetPasswordClick() },
                content = { Text("Reset Password") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (state.error != null) {
                Text(state.error, color = androidx.compose.ui.graphics.Color.Red)
            }
            if (state.successMessage != null) {
                Text(state.successMessage, color = androidx.compose.ui.graphics.Color.Green)
            }
            if (state.isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginViewPreview() {
    MyShoppingListTheme {
        LoginView()
    }
}
