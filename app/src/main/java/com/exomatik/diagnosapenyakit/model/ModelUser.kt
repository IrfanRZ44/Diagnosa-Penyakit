package com.exomatik.diagnosapenyakit.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelUser(
    var username: String = "",
    var password: String = "",
    var nama: String = "",
    var jk: String = "",
    var tempatLahir: String = "",
    var tanggalLahir: String = "",
    var alamat: String = "",
    var jenisAkun: String = ""
    ) : Parcelable