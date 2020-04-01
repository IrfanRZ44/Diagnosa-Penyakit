package com.exomatik.diagnosapenyakit.ui.tambahPenyakit

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.exomatik.diagnosapenyakit.R
import com.exomatik.diagnosapenyakit.base.BaseViewModel
import com.exomatik.diagnosapenyakit.model.ModelPenyakit
import com.exomatik.diagnosapenyakit.utils.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener

class TambahPenyakitViewModel(private val navController: NavController,
                              private val context: Context?,
                              private val activity: Activity?,
                              private val view: View?,
                              private val savedData: DataSave) : BaseViewModel(){
    val namaPenyakit = MutableLiveData<String>()
    val pengenalan = MutableLiveData<String>()
    val gejala = MutableLiveData<String>()
    val soal1 = MutableLiveData<String>()
    val soal2 = MutableLiveData<String>()
    val soal3 = MutableLiveData<String>()
    val soal4 = MutableLiveData<String>()
    val soal5 = MutableLiveData<String>()
    val soal6 = MutableLiveData<String>()
    val soal7 = MutableLiveData<String>()
    val soal8 = MutableLiveData<String>()
    val soal9 = MutableLiveData<String>()
    val soal10 = MutableLiveData<String>()
    val diagnosa1 = MutableLiveData<String>()
    val diagnosa2 = MutableLiveData<String>()
    val diagnosa3 = MutableLiveData<String>()

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

    fun buttonTambahPenyakit(){
        try {
            dismissKeyboard(
                activity ?: throw Exception("")
            )
        } catch (e: Exception) {
        }

        isShowLoading.value = true

        try {
            val dataPenyakit = ModelPenyakit("",
                namaPenyakit.value?:throw Exception("Error, mohon mengisi form nama penyakit"),
                pengenalan.value?:throw Exception("Error, mohon mengisi form pengenalan"),
                gejala.value?:throw Exception("Error, mohon mengisi form gejala"),
                soal1.value?:throw Exception("Error, mohon mengisi form pertanyaan 1"),
                soal2.value?:throw Exception("Error, mohon mengisi form pertanyaan 2"),
                soal3.value?:throw Exception("Error, mohon mengisi form pertanyaan 3"),
                soal4.value?:throw Exception("Error, mohon mengisi form pertanyaan 4"),
                soal5.value?:throw Exception("Error, mohon mengisi form pertanyaan 5"),
                soal6.value?:throw Exception("Error, mohon mengisi form pertanyaan 6"),
                soal7.value?:throw Exception("Error, mohon mengisi form pertanyaan 7"),
                soal8.value?:throw Exception("Error, mohon mengisi form pertanyaan 8"),
                soal9.value?:throw Exception("Error, mohon mengisi form pertanyaan 9"),
                soal10.value?:throw Exception("Error, mohon mengisi form pertanyaan 10"),
                diagnosa1.value?:throw Exception("Error, mohon mengisi form hasil diagnosa 1"),
                diagnosa2.value?:throw Exception("Error, mohon mengisi form hasil diagnosa 2"),
                diagnosa3.value?:throw Exception("Error, mohon mengisi form hasil diagnosa 3")
                )

            val onCompleteListener =
                OnCompleteListener<Void> { result ->
                    if (result.isSuccessful) {
                        isShowLoading.value = false
                        message.value = "Berhasil menambah penyakit"
                        navController.navigate(R.id.mainAdminFragment)
                    } else {
                        isShowLoading.value = false
                        message.value = "Gagal menambah penyakit"
                    }
                }

            val onFailureListener = OnFailureListener { result ->
                isShowLoading.value = false
                message.value = result.message
            }

            FirebaseUtils.setValuePenyakit(
                dataPenyakit
                , onCompleteListener
                , onFailureListener
            )
        }catch (e: Exception){
            isShowLoading.value = false
            message.value = e.message
        }
    }
}