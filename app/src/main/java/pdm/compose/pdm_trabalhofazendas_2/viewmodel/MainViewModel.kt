package pdm.compose.pdm_trabalhofazendas_2.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pdm.compose.pdm_trabalhofazendas_2.data.DatabaseModule
import pdm.compose.pdm_trabalhofazendas_2.model.Farm

/*
* MutableStateFlow - Kotlin Coroutine that represents an observable state holder
* Emit updates to collector that observes it (like UI components)
* Underscore (_) is a good practice to notate a variable that should
* not be used outside the referred class context
* */


class MainViewModel (
    private val databaseModule: DatabaseModule,
    private val navController: NavController
) : ViewModel() {

    private val _farms = MutableStateFlow<List<Farm>>((emptyList()))
    val farms: StateFlow<List<Farm>> = _farms.asStateFlow()

    private val _selectedFarm = MutableStateFlow<Farm?>(null)
    val selectedFarm: StateFlow<Farm?> = _selectedFarm.asStateFlow()

    companion object {
        fun Factory(databaseModule: DatabaseModule, navController: NavController): ViewModelProvider.Factory
        = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if(modelClass.isAssignableFrom(MainViewModel::class.java)) {
                    return MainViewModel(databaseModule, navController) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

    init {
        refreshFarms()
    }

    fun addFarm(farm: Farm) {
        viewModelScope.launch {
            databaseModule.addFarm(farm,
                onSuccess = { addedFarm ->
                    // Handle success, navigate back to farmList
                    Log.d("MainViewModel", "Farm added successfully (from callback): $addedFarm")
                    navController.navigate("farmList") {
                        popUpTo("farmList") {
                            inclusive = true
                        }
                    }
                    refreshFarms()
                },
                onFailure = { exception ->
                    // Handle failure, e.g., show an error message
                    Log.e("MainViewModel", "Error adding farm (from callback): ${exception.message}")
                }
            )
        }
    }

    fun updateFarm(
        farm: Farm,
        onSuccess: () -> Unit = {},
        onFailure: (Exception) -> Unit = {}
    ) {
        viewModelScope.launch {
            databaseModule.updateFarm(farm, onSuccess, onFailure)
            refreshFarms()
        }
    }

    fun deleteFarm(
        record: String,
        onSuccess: () -> Unit = {},
        onFailure: (Exception) -> Unit = {}
    ) {
        viewModelScope.launch {
            databaseModule.deleteFarm(record, onSuccess, onFailure)
            refreshFarms()
        }
    }

    private fun refreshFarms() {
        viewModelScope.launch {
            databaseModule.getFarms(
                onSuccess = { farms ->
                    _farms.value = farms
                    Log.d("MainViewModel", "Farms refreshed (from callback): $farms")
                },
                onFailure = { exception ->
                    Log.e("MainViewModel", "Error refreshing farms (from callback): ${exception.message}")
                }
            )
        }
    }

    fun setSelectedFarm(farm: Farm) {
        _selectedFarm.value = farm
    }

    fun validateFarmName(name: String): String? {
        return if (name.isBlank()) "Farm name cannot be empty" else null
    }

    fun validateFarmPrice(price: String): String? {
        return if (price.isBlank()) "Price cannot be empty"
        else if (price.toFloatOrNull() == null) "Invalid price format"
        else null
    }

    fun validateFarmLatitude(latitude: String): String? {
        return if (latitude.isBlank()) "Latitude cannot be empty"
        else if (latitude.toFloatOrNull() == null) "Invalid latitude format"
        else null
    }

    fun validateFarmLongitude(longitude: String): String? {
        return if (longitude.isBlank()) "Longitude cannot be empty"
        else if (longitude.toFloatOrNull() == null) "Invalid longitude format"
        else null
    }

    fun getAllFarmsAsText(): String {
        val farms = farms.value // Get the current list of farms
        return farms.joinToString("\n\n") { farm ->
            "Farm ${farm.name}\n" +
                    "     Record: ${farm.record}\n" +
                    "     Price: ${farm.price}\n" +
                    "     Latitude: ${farm.latitude}\n" +
                    "     Longitude: ${farm.longitude}"
        }
    }

}