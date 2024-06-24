package pdm.compose.pdm_trabalhofazendas_2.ui.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pdm.compose.pdm_trabalhofazendas_2.viewmodel.MainViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import pdm.compose.pdm_trabalhofazendas_2.model.Farm
import pdm.compose.pdm_trabalhofazendas_2.ui.components.CustomOutlinedTextField
import pdm.compose.pdm_trabalhofazendas_2.ui.components.TextLabel
import pdm.compose.pdm_trabalhofazendas_2.ui.components.TextTitle


@Composable
fun EditFarmScreen(farmRecord:String?, viewModel: MainViewModel = viewModel(), navController: NavController) {
    val farms by viewModel.farms.collectAsState()
    val farm = farms.find { it.record == farmRecord }

    // State variables for editable fields
    var farmName by remember(farm) { mutableStateOf(farm?.name ?: "") }
    var farmPrice by remember(farm) { mutableStateOf(farm?.price?.toString() ?: "") }
    var farmLatitude by remember(farm) { mutableStateOf(farm?.latitude?.toString() ?: "") }
    var farmLongitude by remember(farm) { mutableStateOf(farm?.longitude?.toString() ?: "") }

    farm?.let {
        Scaffold(
            topBar = {
                IconButton(onClick = { navController.popBackStack() }, modifier = Modifier.size(56.dp)) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        ) { innerPadding ->
            TextTitle(text = "Edit Farm", color = Color.Blue)

            Column(modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
            ) {
                // Use state variables in CustomOutlinedTextField
                CustomOutlinedTextField(
                    value = farmName,
                    onValueChange = { farmName = it },
                    label = "Farm Name",
                    modifier = Modifier.fillMaxWidth(),
                    validation = viewModel::validateFarmName
                )
                Spacer(modifier = Modifier.height(16.dp))

                CustomOutlinedTextField(
                    value = farmPrice,
                    onValueChange = { farmPrice = it },
                    label = "Farm Price",modifier = Modifier.fillMaxWidth(),
                    validation = viewModel::validateFarmPrice
                )
                Spacer(modifier = Modifier.height(16.dp))

                CustomOutlinedTextField(
                    value = farmLatitude,
                    onValueChange = { farmLatitude = it },
                    label = "Farm Latitude",modifier = Modifier.fillMaxWidth(),
                    validation = viewModel::validateFarmLatitude
                )
                Spacer(modifier = Modifier.height(16.dp))

                CustomOutlinedTextField(
                    value = farmLongitude,
                    onValueChange = { farmLongitude = it },
                    label = "Farm Longitude",modifier = Modifier.fillMaxWidth(),
                    validation = viewModel::validateFarmLongitude
                )
                Spacer(modifier = Modifier.height(16.dp))


                Button(
                    onClick = {
                        val updatedFarm = farm.copy(
                            name = farmName,
                            price = farmPrice.toFloatOrNull() ?: farm.price,
                            latitude = farmLatitude.toFloatOrNull() ?: farm.latitude,
                            longitude = farmLongitude.toFloatOrNull() ?: farm.longitude
                        )
                        viewModel.updateFarm(updatedFarm)
                        navController.navigate("farmDetails/${updatedFarm.record}")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save Changes")
                }
            }
        }
    } ?: run {
        Text(text = "Farm not Found")
    }
}