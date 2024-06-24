package pdm.compose.pdm_trabalhofazendas_2.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pdm.compose.pdm_trabalhofazendas_2.viewmodel.MainViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import pdm.compose.pdm_trabalhofazendas_2.model.Farm
import pdm.compose.pdm_trabalhofazendas_2.ui.components.TextLabel
import pdm.compose.pdm_trabalhofazendas_2.ui.components.TextTitle

@Composable
fun FarmDetailsScreen(farmRecord: String, viewModel: MainViewModel, navController: NavController) {
    val farms by viewModel.farms.collectAsState()
    val farm = farms.find { it.record == farmRecord }
    Log.d("FarmDetailsScreen", "Farm record: $farmRecord")

    Scaffold(
        floatingActionButton = {
            if(farm != null){
                FloatingActionButton(
                    onClick = {
//                        navController.currentBackStackEntry?.savedStateHandle?.set("farm", farm)
//                        viewModel.setSelectedFarm(farm)
                        navController.navigate("editFarm/$farmRecord")
                        Log.d("FarmDetailsScreen", "Farm that's going to be edited: ${farm.name}")
                    }
                ) {
                    Icon(imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Farm")
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(
                    onClick = {navController.navigate("farmList") },
                    modifier = Modifier.size(56.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back to Farm List"
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            if(farm != null){
                val farmAttributes = farm.toAttributeMap() //Convert to a Map

                TextTitle(text = "Farm Details", color = Color.Blue)

                Spacer(modifier = Modifier.height(8.dp))

                //Iterate over properties using mapOf and forEach (I tried using reflection):
                //allows getting all properties declared in Farm data class
                Column(
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    farmAttributes.forEach { (name, value) ->
                        Column(
                            modifier = Modifier.padding(8.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            TextLabel(text = name)
                            FarmAttributeCard(value = value)
                        }
                    }
                }
            } else {
                Text("Farm not found")
            }
        }
    }
}

@Composable
fun FarmAttributeCard(value:String){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 16.sp
            )
        }
    }
}