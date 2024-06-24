package pdm.compose.pdm_trabalhofazendas_2.ui.screens


import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import pdm.compose.pdm_trabalhofazendas_2.ui.components.ConfirmationDialog
import pdm.compose.pdm_trabalhofazendas_2.ui.components.GenericActionMenu
import pdm.compose.pdm_trabalhofazendas_2.ui.components.TextTitle


/*
Searched on the internet:
viewModel: MainViewModel: This declares a parameter named viewModel of type MainViewModel, which is expected to provide the data and logic for the screen.
= viewModel(): This part provides a default value for the viewModel parameter. The viewModel() function (imported from androidx.lifecycle.viewmodel.compose) is used to retrieve or create an instance of the MainViewModel associated with the current composable scope. How It Works:
When the FarmListScreen is composed, the viewModel() function checks if there's an existing instance of MainViewModel in the current scope (e.g., within the same activity or navigation graph).
If an instance exists, it is reused.
If no instance exists, a new MainViewModel is created using the default constructor (or a factory if provided).
* Latest version for viewModel until 22/06/2024 was 2.8.2 (watch for build.gradle.ktx - app)
* */

@Composable
fun FarmListScreen(navController: NavController, viewModel: MainViewModel = viewModel()) {
    val farms by viewModel.farms.collectAsState()
    var selectedFarm by remember { mutableStateOf<Farm?>(null) }
    var showDeleteConfirmation by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            // Use a Row to position elements in the top bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween // Arrange items with space between
            ) {
                // Your existing back button (if any) can go here

                // Icon to navigate to BackupScreen
                IconButton(
                    onClick = { navController.navigate("backup") },
                    ) {
                    Icon(imageVector = Icons.Default.Settings, contentDescription = "Backup")
                }
            }},
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("addFarm") }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Farm")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            //Title
            TextTitle(
                text = "Farm's CRUD 2.0",
                color = Color.Blue,
                fontSize = 40.sp
            )
            //Farm list
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(farms) { farm ->
                    var expanded by remember { mutableStateOf(false) }
                    FarmListItem(
                        farm = farm,
                        onCardClick = { navController.navigate("farmDetails/${farm.record}") },
                        onMoreInfoClick = { navController.navigate("farmDetails/${farm.record}")},
                        onEditClick = {
                            Log.d("FarmListScreen", "Farm that's going to be edited: $farm")
                            navController.navigate("editFarm/${farm.record}")
                            expanded = false
                        },
                        onDeleteClick = {
                            selectedFarm = farm
                            showDeleteConfirmation = true
                            expanded = false
                        },
                        expanded = expanded,
                        onExpandChange = { expanded = it }
                    )
                }
            }
        }
    }

    // Confirmation Dialog
    ConfirmationDialog(
        showDialog = showDeleteConfirmation,
        title = "Confirm Deletion",
        message = "Are you sure you want to delete <${selectedFarm?.name ?: ""}> farm?",onConfirm = {
            selectedFarm?.let {
                viewModel.deleteFarm(it.record)
                navController.popBackStack()
            }
            selectedFarm = null
            showDeleteConfirmation = false
        },
        onDismiss = {
            selectedFarm = null
            showDeleteConfirmation = false
        }
    )
}

// Farm List Item Component
@Composable
fun FarmListItem(
    farm: Farm,
    onCardClick: () -> Unit,
    onMoreInfoClick: (() -> Unit)? = null, //Nullable callbacks
    onEditClick: (() -> Unit)? = null,
    onDeleteClick: (() -> Unit)? = null,
    expanded: Boolean,
    onExpandChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onCardClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Farm: ${farm.name}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "Price: $${farm.price}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
            Box {

                IconButton(onClick = { onExpandChange(true) }) {
                    Icon(Icons.Filled.MoreVert, contentDescription = "More Vert")
                }

                GenericActionMenu(
                    actionTargetString = "Farm",
                    expanded = expanded,
                    onDismissRequest = { onExpandChange(false) },
                    onMoreInfoClick = onMoreInfoClick,
                    onEditClick = onEditClick,
                    onDeleteClick = onDeleteClick
                )
            }
        }
    }
}