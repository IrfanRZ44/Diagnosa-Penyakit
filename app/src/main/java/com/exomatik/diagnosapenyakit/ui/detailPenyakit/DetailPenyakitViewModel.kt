package com.exomatik.diagnosapenyakit.ui.detailPenyakit

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import com.exomatik.diagnosapenyakit.R
import com.exomatik.diagnosapenyakit.base.BaseViewModel
import com.exomatik.diagnosapenyakit.model.ModelPenyakit
import com.exomatik.diagnosapenyakit.ui.questioner.QuestionerFragment
import com.exomatik.diagnosapenyakit.utils.Constant
import com.exomatik.diagnosapenyakit.utils.DataSave


class DetailPenyakitViewModel(val dataPenyakit: ModelPenyakit?,
                              private val savedData: DataSave,
                              private val navController: NavController,
                              private val context: Context?) : BaseViewModel(){

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

    fun diagnosa(){
        val bundle = Bundle()
        val frag = QuestionerFragment()
        bundle.putParcelable(Constant.dataPenyakit, dataPenyakit)
        frag.arguments = bundle
        navController.navigate(R.id.questionerFragment, bundle)
    }
}