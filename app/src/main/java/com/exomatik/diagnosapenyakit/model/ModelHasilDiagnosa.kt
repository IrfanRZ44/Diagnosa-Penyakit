package com.exomatik.diagnosapenyakit.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelHasilDiagnosa(
    var idDiagnosa: String = "",
    var username: String = "",
    var hasilDiagnosa: Int = 0,
    var idPenyakit: String = ""
    ) : Parcelable