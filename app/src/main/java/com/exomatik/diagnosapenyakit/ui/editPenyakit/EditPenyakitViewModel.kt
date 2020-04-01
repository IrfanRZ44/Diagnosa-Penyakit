package com.exomatik.diagnosapenyakit.ui.editPenyakit

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.exomatik.diagnosapenyakit.R
import com.exomatik.diagnosapenyakit.base.BaseViewModel
import com.exomatik.diagnosapenyakit.model.ModelPenyakit
import com.exomatik.diagnosapenyakit.utils.Constant
import com.exomatik.diagnosapenyakit.utils.FirebaseUtils
import com.exomatik.diagnosapenyakit.utils.dismissKeyboard
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener

class EditPenyakitViewModel(private val navController: NavController,
                            private val activity: Activity?,
                            private var dataPenyakit: ModelPenyakit?) : BaseViewModel(){
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

    fun setDataPenyakit(){
        namaPenyakit.value = dataPenyakit?.namaPenyakit
        pengenalan.value = dataPenyakit?.pengenalan
        gejala.value = dataPenyakit?.gejala

        soal1.value = dataPenyakit?.penyakit1
        soal2.value = dataPenyakit?.penyakit2
        soal3.value = dataPenyakit?.penyakit3
        soal4.value = dataPenyakit?.penyakit4
        soal5.value = dataPenyakit?.penyakit5
        soal6.value = dataPenyakit?.penyakit6
        soal7.value = dataPenyakit?.penyakit7
        soal8.value = dataPenyakit?.penyakit8
        soal9.value = dataPenyakit?.penyakit9
        soal10.value = dataPenyakit?.penyakit10

        diagnosa1.value = dataPenyakit?.hasilDiagnosa1
        diagnosa2.value = dataPenyakit?.hasilDiagnosa2
        diagnosa3.value = dataPenyakit?.hasilDiagnosa3
    }

    fun buttonSimpanPenyakit(){
        try {
            dismissKeyboard(
                activity ?: throw Exception("")
            )
        } catch (e: Exception) {
        }

        isShowLoading.value = true
        val idPenyakit = dataPenyakit?.idPenyakit?:throw Exception("Error, terjadi kesalahan database")

        try {
            dataPenyakit = ModelPenyakit(idPenyakit,
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
                        message.value = "Berhasil menyimpan penyakit"
                        navController.navigate(R.id.mainAdminFragment)
                    } else {
                        isShowLoading.value = false
                        message.value = "Gagal menyimpan penyakit"
                    }
                }

            val onFailureListener = OnFailureListener { result ->
                isShowLoading.value = false
                message.value = result.message
            }

            FirebaseUtils.editValuePenyakit(dataPenyakit, idPenyakit, onCompleteListener, onFailureListener)
        }catch (e: Exception){
            isShowLoading.value = false
            message.value = e.message
        }
    }

    fun alertLogout() {
        val alert = AlertDialog.Builder(activity?:throw Exception("Error, mohon masuk ulang ke aplikasi"))
        alert.setTitle(Constant.attention)
        alert.setMessage(Constant.hapusPenyakit)
        alert.setPositiveButton(
            Constant.iya
        ) { _, _ ->
            deletePenyakit()
        }
        alert.setNegativeButton(
            Constant.tidak
        ) { dialog, _ -> dialog.dismiss() }

        alert.show()
    }

    private fun deletePenyakit(){
        isShowLoading.value = true

        val onCompleteListener =
            OnCompleteListener<Void> { result ->
                if (result.isSuccessful) {
                    isShowLoading.value = false
                    message.value = "Berhasil menghapus penyakit"
                    navController.navigate(R.id.mainAdminFragment)
                } else {
                    isShowLoading.value = false
                    message.value = "Gagal menghapus penyakit"
                }
            }

        val onFailureListener = OnFailureListener { result ->
            isShowLoading.value = false
            message.value = result.message
        }

        try {
            FirebaseUtils.deletePenyakit(dataPenyakit?.idPenyakit?:throw Exception("Error, gagal menghapus penyakit"),
                onCompleteListener, onFailureListener)
        }catch (e: Exception){
            message.value = e.message
            isShowLoading.value = false
        }
    }
}