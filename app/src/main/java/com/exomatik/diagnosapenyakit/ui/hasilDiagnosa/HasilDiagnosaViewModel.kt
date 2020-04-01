package com.exomatik.diagnosapenyakit.ui.hasilDiagnosa

import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.exomatik.diagnosapenyakit.R
import com.exomatik.diagnosapenyakit.base.BaseViewModel
import com.exomatik.diagnosapenyakit.model.ModelHasilDiagnosa
import com.exomatik.diagnosapenyakit.model.ModelPenyakit
import com.exomatik.diagnosapenyakit.model.ModelQuestioner
import com.exomatik.diagnosapenyakit.utils.DataSave
import com.exomatik.diagnosapenyakit.utils.FirebaseUtils

class HasilDiagnosaViewModel(private val savedData: DataSave,
                             private val dataPenyakit: ModelPenyakit?,
                             private val hasilQuestioner: ArrayList<ModelQuestioner>?,
                             private val navController: NavController) : BaseViewModel() {
    val nama = MutableLiveData<String>()
    val jenisKelamin = MutableLiveData<String>()
    val tempatLahir = MutableLiveData<String>()
    val tanggalLahir = MutableLiveData<String>()
    val alamat = MutableLiveData<String>()
    val status = MutableLiveData<String>()
    val hasilDiagnosa = MutableLiveData<String>()

    fun selesai(){
        navController.navigate(R.id.mainUserFragment)
    }
    fun setData(){
        nama.value = "Nama : ${savedData.getDataUser()?.nama}"
        jenisKelamin.value = "Nama : ${savedData.getDataUser()?.jk}"
        tempatLahir.value = "Nama : ${savedData.getDataUser()?.tempatLahir}"
        tanggalLahir.value = "Nama : ${savedData.getDataUser()?.tanggalLahir}"
        alamat.value = "Nama : ${savedData.getDataUser()?.alamat}"
    }

    private fun setInfo(nilaiDiagnosa: Int) {
        when (nilaiDiagnosa) {
            in 75..100 -> {
                status.value = "Hasil diagnosa kami, Anda 75 % terkena penyakit ${dataPenyakit?.namaPenyakit}"
                hasilDiagnosa.value = dataPenyakit?.hasilDiagnosa1
            }
            in 50..69 -> {
                status.value = "Hasil diagnosa kami, Anda 50% terkena penyakit ${dataPenyakit?.namaPenyakit}"
                hasilDiagnosa.value = dataPenyakit?.hasilDiagnosa2
            }
            in 0..49 -> {
                status.value = "Hasil diagnosa kami, Anda kurang dari 50 % terkena penyakit ${dataPenyakit?.namaPenyakit}"
                hasilDiagnosa.value = dataPenyakit?.hasilDiagnosa3
            }
        }
    }

    fun point() {
        var nilaiDiagnosa = 0
        for (a in 1..10) {
            if (hasilQuestioner?.get(a)?.jawaban == "Iya") {
                nilaiDiagnosa += 10
            } else if (hasilQuestioner?.get(a)?.jawaban == "Kadang-kadang") {
                nilaiDiagnosa += 5
            }
        }

        setInfo(nilaiDiagnosa)
        simpanHasil(nilaiDiagnosa)
    }

    private fun simpanHasil(point: Int) {
        val dataDiagnosa = ModelHasilDiagnosa(
            "", savedData.getDataUser()?.username?:"",
            point, dataPenyakit?.idPenyakit?:""
        )

        FirebaseUtils.setValueHasilDiagnosa(
            dataDiagnosa
        )
    }
}