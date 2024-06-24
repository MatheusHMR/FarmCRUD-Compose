package pdm.compose.pdm_trabalhofazendas_2.data

import android.util.Log
import kotlinx.coroutines.tasks.await
import pdm.compose.pdm_trabalhofazendas_2.model.Farm

class DatabaseModule (private val db: FirebaseModule) {
    //Working with FireStore
    private val instance = db.provideFirestoreInstance()

    suspend fun addFarm(
        farm: Farm,
        onSuccess: (Farm) -> Unit = {},
        onFailure: (Exception) -> Unit = {}
    ) {
        try{
            val newFarmRef = instance.collection("farms").document()
            val newFarm = farm.copy(record = newFarmRef.id)
            instance.collection("farms").document(newFarmRef.id)
                .set(newFarm).await()
            Log.d("DatabaseModule", "Farm added successfully: $newFarm")
            onSuccess(newFarm) //Call the success callback
        } catch (e: Exception) {
            Log.e("DatabaseModule", "Error adding farm: ${e.message}", e)
            onFailure(e) //Call the failure callback with the exception
        }
    }

    suspend fun getFarms(
        onSuccess: (List<Farm>) -> Unit = {},
        onFailure: (Exception) -> Unit = {}
    ): List <Farm> {
        var farms: List<Farm>
        try {
            val snapshot = instance.collection("farms").get()
                .await()
            farms = snapshot.toObjects(Farm::class.java)
            Log.d("DatabaseModule", "Farms retrieved successfully: $farms")
            onSuccess(farms)
        } catch (e: Exception) {
            Log.e("DatabaseModule", "Error getting farms: ${e.message}", e)
            onFailure(e)
            farms = emptyList()
        }

        return farms
    }

    suspend fun updateFarm(
        farm: Farm,
        onSuccess: () -> Unit = {},
        onFailure: (Exception) -> Unit = {}
    ) {
        try {
            val farmRef = instance.collection("farms")
                .document(farm.record)

            farmRef.update(
                mapOf(
                    "name" to farm.name,
                    "price" to farm.price,
                    "latitude" to farm.latitude,
                    "longitude" to farm.longitude
                )
            ).await()
            onSuccess()
        } catch (e: Exception) {
            onFailure(e)
        }
    }

    suspend fun deleteFarm(
        record: String,
        onSuccess: () -> Unit = {},
        onFailure: (Exception) -> Unit = {}
    ) {
        try {
            instance.collection("farms").document(record)
                .delete().await()
            onSuccess()
        } catch (e: Exception) {
            onFailure(e)
        }
    }

}