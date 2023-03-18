package integracion

import com.google.firebase.firestore.FirebaseFirestore

class SingletonDBImp: SingletonDataBase {
    private var db: FirebaseFirestore? = null

    constructor(){
        if(db == null)
            db = FirebaseFirestore.getInstance()
    }


    override fun getDB(): FirebaseFirestore{
        return db as FirebaseFirestore
    }
}