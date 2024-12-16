package ipca.example.myshoppinglist.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ipca.example.myshoppinglist.R
import ipca.example.myshoppinglist.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileView(
    navController: NavController = rememberNavController()
) {
    val viewModel: ProfileViewModel = viewModel()
    val state = viewModel.state.collectAsState().value

    // Initialize name field with the current value from state
    var newName by remember { mutableStateOf(state.name ?: "") }

    // Update newName when state.name changes
    LaunchedEffect(state.name) {
        newName = state.name ?: ""
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile", fontSize = 20.sp) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Email: ${state.email ?: "Not available"}", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(16.dp))

            // Edit Name Field with preloaded name
            TextField(
                value = newName,
                onValueChange = { newName = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Save Name Button
            Button(
                onClick = { viewModel.updateName(newName) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Name")
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Reset Password Button
            Button(
                onClick = { viewModel.resetPassword() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Reset Password")
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Logout Button
            Button(
                onClick = {
                    viewModel.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Logout")
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Home Button to return to the lists
            Button(
                onClick = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_home_24),
                    contentDescription = "Home"
                )
            }

            // Display Success or Error Messages
            if (state.successMessage != null) {
                Text(text = state.successMessage, color = MaterialTheme.colorScheme.primary)
            }
            if (state.error != null) {
                Text(text = state.error, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
