package integracion

import com.google.firebase.firestore.FirebaseFirestore

abstract class SingletonDataBase {

    companion object {
        private var instance: SingletonDataBase? = null

        fun getInstance(): SingletonDataBase {
            if (instance == null)
                instance = SingletonDBImp()
            return instance as SingletonDBImp
        }

    }
    abstract fun getDB(): FirebaseFirestore

}