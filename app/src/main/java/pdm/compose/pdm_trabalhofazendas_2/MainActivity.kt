package pdm.compose.pdm_trabalhofazendas_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.ktx.Firebase
import com.yourpackage.crudapp.ui.screens.AddFarmScreen
import pdm.compose.pdm_trabalhofazendas_2.data.DatabaseModule
import pdm.compose.pdm_trabalhofazendas_2.data.FirebaseModule
import pdm.compose.pdm_trabalhofazendas_2.ui.screens.BackupScreen
import pdm.compose.pdm_trabalhofazendas_2.ui.screens.EditFarmScreen
import pdm.compose.pdm_trabalhofazendas_2.ui.screens.FarmDetailsScreen
import pdm.compose.pdm_trabalhofazendas_2.ui.screens.FarmListScreen
import pdm.compose.pdm_trabalhofazendas_2.ui.theme.PDMTrabalhoFazendas2Theme
import pdm.compose.pdm_trabalhofazendas_2.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = DatabaseModule(FirebaseModule)
        setContent {
            PDMTrabalhoFazendas2Theme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    AppNavigation(navController = navController, db = db)
                }
            }
        }
    }
}

    @Composable
    fun AppNavigation(navController: NavHostController, db: DatabaseModule) {
        NavHost(navController = navController, startDestination = "farmList") {
            composable("farmList") {
                val viewModel: MainViewModel = viewModel( //Implementing a kind of generic DAO used on my POOV subject in my graduation
                    factory = MainViewModel.Factory(db, navController)
                )
                FarmListScreen(navController, viewModel)
            }
            composable("addFarm") {
                val viewModel: MainViewModel = viewModel(
                    factory = MainViewModel.Factory(db, navController)
                )
                AddFarmScreen(navController, viewModel)
            }
            //Passing the farmRecord so that we differ each farm that's going to be
            //detailed inside farmDetails
            composable("farmDetails/{farmRecord}"){ backStackEntry ->
                val farmRecord = backStackEntry.arguments?.getString("farmRecord") ?: ""
                val viewModel: MainViewModel = viewModel(
                    factory = MainViewModel.Factory(db, navController)
                )
                FarmDetailsScreen(farmRecord, viewModel, navController)
            }
            composable("editFarm/{farmRecord}") { backStackEntry ->
                val farmRecord = backStackEntry.arguments?.getString("farmRecord")
                val viewModel: MainViewModel = viewModel(
                    factory = MainViewModel.Factory(db, navController)
                )
                EditFarmScreen(farmRecord, viewModel, navController)
            }
            composable("backup"){
                val viewModel: MainViewModel = viewModel(
                    factory = MainViewModel.Factory(db, navController)
                )
                BackupScreen(viewModel, navController)
            }
        }
    }
