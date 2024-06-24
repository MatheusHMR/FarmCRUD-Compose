package pdm.compose.pdm_trabalhofazendas_2.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

/*
* Created this object with the following structure because
* I learned about Dependency Injection and memory leakage.
* To avoid leakage, I should provide a instance of firestore instead
* of declaring it in a static way using the object. Example of what I
* mean and what I started to avoid:
* object DatabaseModule{
    private val db: FirebaseFirestore = Firebase.firestore
}
* So the solution is what is written both in this file and DatabaseModule one
* */


object FirebaseModule {
    fun provideFirestoreInstance(): FirebaseFirestore {
        return Firebase.firestore
    }
}