package com.exomatik.diagnosapenyakit.ui.mainUser

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.exomatik.diagnosapenyakit.R
import com.exomatik.diagnosapenyakit.base.BaseViewModel
import com.exomatik.diagnosapenyakit.model.ModelPenyakit
import com.exomatik.diagnosapenyakit.ui.detailPenyakit.DetailPenyakitFragment
import com.exomatik.diagnosapenyakit.ui.listPenyakit.AdapterListPenyakit
import com.exomatik.diagnosapenyakit.utils.Constant
import com.exomatik.diagnosapenyakit.utils.DataSave
import com.exomatik.diagnosapenyakit.utils.FirebaseUtils
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class MainUserViewModel(private val rcPenyakit: RecyclerView,
                        private val context: Context?,
                        private val navController: NavController,
                        private val savedData: DataSave
) : BaseViewModel(){
    private val listPenyakit = ArrayList<ModelPenyakit?>()
    private lateinit var adapterPenyakit: AdapterListPenyakit

    fun alertLogout() {
        val alert = AlertDialog.Builder(context?:throw Exception("Error, mohon masuk ulang ke aplikasi"))
        alert.setTitle(Constant.attention)
        alert.setMessage(Constant.logout)
        alert.setPositiveButton(
            Constant.iya
        ) { _, _ ->
            savedData.setDataObject(null, Constant.referenceUser)
            navController.navigate(R.id.splashFragment)
        }
        alert.setNegativeButton(
            Constant.tidak
        ) { dialog, _ -> dialog.dismiss() }

        alert.show()
    }

    fun initAdapter() {
        adapterPenyakit = AdapterListPenyakit(
            listPenyakit
        ) { dataPenyakit: ModelPenyakit -> onClickItem(dataPenyakit) }

        rcPenyakit.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rcPenyakit.adapter = adapterPenyakit
        rcPenyakit.isNestedScrollingEnabled = false
    }

    private fun onClickItem(dataPenyakit: ModelPenyakit){
        val bundle = Bundle()
        val frag = DetailPenyakitFragment()
        bundle.putParcelable(Constant.dataPenyakit, dataPenyakit)
        frag.arguments = bundle
        navController.navigate(R.id.detailPenyakitFragment, bundle)
    }

    fun getListPenyakit(){
        listPenyakit.clear()
        adapterPenyakit.notifyDataSetChanged()

        isShowLoading.value = true
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(result: DatabaseError) {
                isShowLoading.value = false
                message.value = "Error, ${result.message}"
            }

            override fun onDataChange(result: DataSnapshot) {
                if (result.exists()) {
                    isShowLoading.value = false
                    for (snapshot in result.children) {
                        val data = snapshot.getValue(ModelPenyakit::class.java)
                        listPenyakit.add(data)
                        adapterPenyakit.notifyDataSetChanged()
                    }
                } else {
                    isShowLoading.value = false
                    message.value = "Belum ada data penyakit"
                }
            }
        }

        FirebaseUtils.getDataObject(
            Constant.referencePenyakit
            , valueEventListener
        )
    }
}