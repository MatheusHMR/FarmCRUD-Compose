package com.yourpackage.crudapp.ui.screens

import android.graphics.Paint.Align
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import pdm.compose.pdm_trabalhofazendas_2.model.Farm
import pdm.compose.pdm_trabalhofazendas_2.ui.components.CustomOutlinedTextField
import pdm.compose.pdm_trabalhofazendas_2.ui.components.TextTitle
import pdm.compose.pdm_trabalhofazendas_2.ui.components.ThreadLaunchingButton
import pdm.compose.pdm_trabalhofazendas_2.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFarmScreen(navController: NavController, viewModel: MainViewModel = viewModel()) {
    var farmName by remember { mutableStateOf("") }
    var farmPrice by remember { mutableStateOf("") }
    var farmLatitude by remember { mutableStateOf("") }
    var farmLongitude by remember { mutableStateOf("") }


    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = {navController.navigate("farmList") }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back to Farm List",
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextTitle(text = "Create a new Farm", color = Color.Blue)

            Spacer(modifier = Modifier.height(64.dp))

            CustomOutlinedTextField(
                value = farmName,
                onValueChange = { farmName = it },
                label = "Farm name",
                modifier = Modifier.fillMaxWidth(),
                validation = viewModel::validateFarmName
            )

            Spacer(modifier = Modifier.height(8.dp))

            CustomOutlinedTextField(
                value = farmPrice,
                onValueChange = { farmPrice = it },
                label = "Price",
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Number,
                validation = viewModel::validateFarmPrice
            )

            Spacer(modifier = Modifier.height(8.dp))

            CustomOutlinedTextField(
                value = farmLatitude,
                onValueChange = { farmLatitude = it },
                label = "Latitude",
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Number,
                validation = viewModel::validateFarmLatitude
            )

            Spacer(modifier = Modifier.height(8.dp))

            CustomOutlinedTextField(
                value = farmLongitude,
                onValueChange = { farmLongitude = it },
                label = "Longitude",
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Number,
                validation = viewModel::validateFarmLongitude
            )

            Spacer(modifier = Modifier.height(16.dp))

            ThreadLaunchingButton(
                onClick = {
                    val newFarm = Farm(
                        record = "",
                        name = farmName,
                        price = farmPrice.toFloatOrNull() ?: 0f,
                        latitude = farmLatitude.toFloatOrNull() ?: 0f,
                        longitude = farmLongitude.toFloatOrNull() ?: 0f
                    )
                    viewModel.addFarm(newFarm)
                    Log.i("AddFarmScreen", "Added farm: ${newFarm.name}")
                },
                text = "Add Farm",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

}