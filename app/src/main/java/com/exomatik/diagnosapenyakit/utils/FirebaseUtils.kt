package com.exomatik.diagnosapenyakit.utils

import com.exomatik.diagnosapenyakit.model.ModelHasilDiagnosa
import com.exomatik.diagnosapenyakit.model.ModelPenyakit
import com.exomatik.diagnosapenyakit.utils.Constant.referenceHasilDiagnosa
import com.exomatik.diagnosapenyakit.utils.Constant.referencePenyakit
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

object FirebaseUtils {
    private lateinit var database: FirebaseDatabase
    private lateinit var query: Query
    private lateinit var refreshData: ValueEventListener

    fun searchDataWith1ChildObject(
        reference: String,
        search: String,
        value: String?,
        eventListener: ValueEventListener
    ) {
        FirebaseDatabase.getInstance()
            .getReference(reference)
            .orderByChild(search)
            .equalTo(value)
            .addListenerForSingleValueEvent(eventListener)
    }

    fun setValuePenyakit(
        data: ModelPenyakit
        , onCompleteListener: OnCompleteListener<Void>
        , onFailureListener: OnFailureListener
    ) {
        val ref = FirebaseDatabase.getInstance().getReference(referencePenyakit)
        val id = ref.push()
        data.idPenyakit = id.key.toString()

        id.setValue(data)
            .addOnCompleteListener(onCompleteListener)
            .addOnFailureListener(onFailureListener)
    }

    fun setValueHasilDiagnosa(data: ModelHasilDiagnosa) {
        val ref = FirebaseDatabase.getInstance().getReference(referenceHasilDiagnosa)
        val id = ref.push()
        data.idDiagnosa = id.key.toString()

        id.setValue(data)
    }

    fun editValuePenyakit(
        data: ModelPenyakit?,
        idPenyakit: String
        , onCompleteListener: OnCompleteListener<Void>
        , onFailureListener: OnFailureListener
    ) {
        FirebaseDatabase.getInstance().getReference(referencePenyakit)
            .child(idPenyakit)
            .setValue(data)
            .addOnCompleteListener(onCompleteListener)
            .addOnFailureListener(onFailureListener)
    }

    fun deleteChild(reference: String, idPenyakit: String
                       , onCompleteListener: OnCompleteListener<Void>
                       , onFailureListener: OnFailureListener
    ) {
        database = FirebaseDatabase.getInstance()
        database.getReference(reference)
            .child(idPenyakit)
            .removeValue()
            .addOnCompleteListener(onCompleteListener)
            .addOnFailureListener(onFailureListener)
    }

    fun searchDataWith2ChildObject(
        reference: String,
        child: String,
        search: String,
        value: String?,
        eventListener: ValueEventListener
    ) {
        FirebaseDatabase.getInstance()
            .getReference(reference)
            .child(child)
            .orderByChild(search)
            .equalTo(value)
            .addListenerForSingleValueEvent(eventListener)
    }

    fun getDataObject(reference: String, eventListener: ValueEventListener) {
        FirebaseDatabase.getInstance()
            .getReference(reference)
            .addListenerForSingleValueEvent(eventListener)
    }

    fun getData1Child(reference: String, value: String, eventListener: ValueEventListener) {
        FirebaseDatabase.getInstance()
            .getReference(reference)
            .child(value)
            .addListenerForSingleValueEvent(eventListener)
    }

    fun getData2Child(
        reference: String,
        value: String,
        value2: String,
        eventListener: ValueEventListener
    ) {
        FirebaseDatabase.getInstance()
            .getReference(reference)
            .child(value)
            .child(value2)
            .addListenerForSingleValueEvent(eventListener)
    }

    fun searchWordWith2ChildObject(
        reference: String,
        child: String,
        search: String,
        value: String?,
        eventListener: ValueEventListener
    ) {
        FirebaseDatabase.getInstance()
            .getReference(reference)
            .child(child)
            .orderByChild(search)
            .startAt(value)
            .endAt(value + "\uf8ff")
            .addListenerForSingleValueEvent(eventListener)
    }

    fun searchWordWith1ChildObject(
        reference: String,
        search: String,
        value: String?,
        eventListener: ValueEventListener
    ) {
        FirebaseDatabase.getInstance()
            .getReference(reference)
            .orderByChild(search)
            .startAt(value)
            .endAt(value + "\uf8ff")
            .addListenerForSingleValueEvent(eventListener)
    }

    fun refreshDataWith1ChildObject1(
        reference: String,
        id: String,
        eventListener: ValueEventListener
    ) {
        this.refreshData = eventListener
        query = FirebaseDatabase.getInstance()
            .getReference(reference)
            .child(id)
        query.addValueEventListener(refreshData)
    }

    fun setValueObject(
        reference: String, child: String, data: Any
        , onCompleteListener: OnCompleteListener<Void>
        , onFailureListener: OnFailureListener
    ) {
        database = FirebaseDatabase.getInstance()
        database.getReference(reference)
            .child(child)
            .setValue(data)
            .addOnCompleteListener(onCompleteListener)
            .addOnFailureListener(onFailureListener)
    }

    fun setValueWith2ChildObject(
        reference: String, child: String, child2: String, data: Any
        , onCompleteListener: OnCompleteListener<Void>
        , onFailureListener: OnFailureListener
    ) {
        database = FirebaseDatabase.getInstance()
        database.getReference(reference)
            .child(child)
            .child(child2)
            .setValue(data)
            .addOnCompleteListener(onCompleteListener)
            .addOnFailureListener(onFailureListener)
    }

    fun setValueWith2ChildString(
        reference: String, child: String, child2: String, value: String
        , onCompleteListener: OnCompleteListener<Void>
        , onFailureListener: OnFailureListener
    ) {
        database = FirebaseDatabase.getInstance()
        database.getReference(reference)
            .child(child)
            .child(child2)
            .setValue(value)
            .addOnCompleteListener(onCompleteListener)
            .addOnFailureListener(onFailureListener)
    }

    fun stopRefresh() {
        try {
            query.removeEventListener(refreshData)
        } catch (e: Exception) {
            showLog("error, method not running ${e.message} query 1")
        }
    }
}