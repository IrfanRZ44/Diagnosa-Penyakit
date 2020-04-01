package com.exomatik.diagnosapenyakit.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelQuestioner(
    var jawaban: String = ""
) : Parcelable