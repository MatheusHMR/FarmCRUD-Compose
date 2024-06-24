package pdm.compose.pdm_trabalhofazendas_2.ui.screens

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import pdm.compose.pdm_trabalhofazendas_2.ui.components.TextTitle
import pdm.compose.pdm_trabalhofazendas_2.viewmodel.MainViewModel

@Composable
fun BackupScreen(viewModel: MainViewModel = viewModel(), navController: NavController) {
    var backupText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            IconButton(onClick = {
                navController.popBackStack()
                Log.d("BackupScreen", "Going back to Main Page")
            },
                modifier = Modifier.size(56.dp)
            ) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    backupText = viewModel.getAllFarmsAsText()
                },
                modifier = Modifier.padding(bottom =16.dp)
            ) {
                Text("Make Backup")
            }

            TextTitle("Backup List")

            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .border(1.dp, Color.Black),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = backupText,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(8.dp)
                        .verticalScroll(rememberScrollState()),
                )
            }
        }
    }
}