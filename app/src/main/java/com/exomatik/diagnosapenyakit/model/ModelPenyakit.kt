package com.exomatik.diagnosapenyakit.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelPenyakit(
    var idPenyakit: String = "",
    var namaPenyakit: String = "",
    var pengenalan: String = "",
    var gejala: String = "",
    var penyakit1: String = "",
    var penyakit2: String = "",
    var penyakit3: String = "",
    var penyakit4: String = "",
    var penyakit5: String = "",
    var penyakit6: String = "",
    var penyakit7: String = "",
    var penyakit8: String = "",
    var penyakit9: String = "",
    var penyakit10: String = "",
    var hasilDiagnosa1: String = "",
    var hasilDiagnosa2: String = "",
    var hasilDiagnosa3: String = ""
) : Parcelable