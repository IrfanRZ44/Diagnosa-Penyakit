package com.exomatik.diagnosapenyakit.ui.mainAdmin

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.exomatik.diagnosapenyakit.R
import com.exomatik.diagnosapenyakit.base.BaseViewModel
import com.exomatik.diagnosapenyakit.model.ModelHasilDiagnosa
import com.exomatik.diagnosapenyakit.model.ModelPenyakit
import com.exomatik.diagnosapenyakit.model.ModelUser
import com.exomatik.diagnosapenyakit.utils.Constant
import com.exomatik.diagnosapenyakit.utils.Constant.referenceHasilDiagnosa
import com.exomatik.diagnosapenyakit.utils.DataSave
import com.exomatik.diagnosapenyakit.utils.FirebaseUtils
import com.exomatik.diagnosapenyakit.utils.showLog
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.item_hasil_diagnosa.view.*


class MainAdminViewModel(private val navController: NavController,
                         private val context: Context?,
                         private val savedData: DataSave,
                         private val rcDiagnosa: RecyclerView) : BaseViewModel(){
    private val listDiagnosa = ArrayList<ModelHasilDiagnosa?>()
    private lateinit var adapterDiagnosa: AdapterListHasilDiagnosa
    val status = MutableLiveData<String>()

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

    fun butonTambahPenyakit(){
        navController.navigate(R.id.tambahPenyakitFragment)
    }

    fun buttonListPenyakit(){
        navController.navigate(R.id.listPenyakitFragment)
    }

    fun initAdapter() {
        adapterDiagnosa = AdapterListHasilDiagnosa(
            listDiagnosa,
            { dataHasil: ModelHasilDiagnosa, item: View -> getDetailDiagnosa(dataHasil, item) },
            { dataHasil: ModelHasilDiagnosa, position: Int-> alertHapus(dataHasil, position) }
        )

        rcDiagnosa.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rcDiagnosa.adapter = adapterDiagnosa
        rcDiagnosa.isNestedScrollingEnabled = false
    }

    @SuppressLint("SetTextI18n")
    private fun getDetailDiagnosa(dataHasil: ModelHasilDiagnosa, item: View){
        item.hasilDiagnosa.text = "Persentase hasil diagnosa ${dataHasil.hasilDiagnosa}"
        getDataUser(dataHasil, item)
    }

    private fun getDataUser(dataHasil: ModelHasilDiagnosa, item: View){
        isShowLoading.value = true

        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(result: DatabaseError) {
                isShowLoading.value = false
                message.value = "Error, ${result.message}"
            }

            @SuppressLint("SetTextI18n")
            override fun onDataChange(result: DataSnapshot) {
                isShowLoading.value = false
                if (result.exists()) {
                    val data = result.getValue(ModelUser::class.java)

                    item.namaUser.text = data?.nama
                    item.tanggalLahir.text = "${data?.tempatLahir}, ${data?.tanggalLahir}"
                    item.alamat.text = "Alamat : ${data?.alamat}"
                } else {
                    isShowLoading.value = false
                    message.value = "Gagal, Username tidak ditemukan"
                }
                getDataPenyakit(dataHasil, item)
            }
        }

        FirebaseUtils.getData1Child(
            Constant.referenceUser
            , dataHasil.username
            , valueEventListener
        )
    }

    private fun getDataPenyakit(dataHasil: ModelHasilDiagnosa, item: View){
        isShowLoading.value = true

        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(result: DatabaseError) {
                isShowLoading.value = false
                message.value = "Error, ${result.message}"
            }

            @SuppressLint("SetTextI18n")
            override fun onDataChange(result: DataSnapshot) {
                isShowLoading.value = false
                if (result.exists()) {
                    val data = result.getValue(ModelPenyakit::class.java)

                    item.namaPenyakit.text = data?.namaPenyakit
                } else {
                    isShowLoading.value = false
                    showLog(dataHasil.idPenyakit)
                    message.value = "Gagal, Penyakit tidak ditemukan atau data penyakit telah dihapus"
                }
            }
        }

        FirebaseUtils.getData1Child(
            Constant.referencePenyakit
            , dataHasil.idPenyakit
            , valueEventListener
        )
    }

    fun getListDiagnosa(){
        listDiagnosa.clear()
        adapterDiagnosa.notifyDataSetChanged()
        isShowLoading.value = true
        status.value = ""

        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(result: DatabaseError) {
                isShowLoading.value = false
                message.value = "Error, ${result.message}"
                status.value = "Error, ${result.message}"
            }

            override fun onDataChange(result: DataSnapshot) {
                if (result.exists()) {
                    isShowLoading.value = false
                    for (snapshot in result.children) {
                        val data = snapshot.getValue(ModelHasilDiagnosa::class.java)
                        listDiagnosa.add(data)
                        adapterDiagnosa.notifyDataSetChanged()
                    }
                } else {
                    isShowLoading.value = false
                    message.value = "Belum ada data hasil diagnosa"
                    status.value = "Belum ada data hasil diagnosa"
                }
                showLog("size ${listDiagnosa.size}")
            }
        }

        FirebaseUtils.getDataObject(
            referenceHasilDiagnosa
            , valueEventListener
        )
    }

    private fun alertHapus(dataHasil: ModelHasilDiagnosa, position: Int){
        val alert = AlertDialog.Builder(context?:throw Exception("Error, mohon masuk ulang ke aplikasi"))
        alert.setTitle(Constant.attention)
        alert.setMessage(Constant.hapusDiagnosa)
        alert.setPositiveButton(
            Constant.iya
        ) { _, _ ->
            deleteHasil(dataHasil, position)
        }
        alert.setNegativeButton(
            Constant.tidak
        ) { dialog, _ -> dialog.dismiss() }

        alert.show()
    }

    private fun deleteHasil(dataHasil: ModelHasilDiagnosa, position: Int){
        isShowLoading.value = true

        val onCompleteListener =
            OnCompleteListener<Void> { result ->
                if (result.isSuccessful) {
                    isShowLoading.value = false
                    message.value = "Berhasil menghapus hasil diagnosa"
                    listDiagnosa.removeAt(position)
                    adapterDiagnosa.notifyDataSetChanged()
                } else {
                    isShowLoading.value = false
                    message.value = "Gagal menghapus hasil diagnosa"
                }
            }

        val onFailureListener = OnFailureListener { result ->
            isShowLoading.value = false
            message.value = result.message
        }

        try {
            FirebaseUtils.deleteChild(
                referenceHasilDiagnosa,
                dataHasil.idDiagnosa,
                onCompleteListener, onFailureListener)
        }catch (e: Exception){
            message.value = e.message
            isShowLoading.value = false
        }
    }

}