package com.exomatik.diagnosapenyakit.ui.login

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.exomatik.diagnosapenyakit.R
import com.exomatik.diagnosapenyakit.base.BaseViewModel
import com.exomatik.diagnosapenyakit.model.ModelUser
import com.exomatik.diagnosapenyakit.utils.Constant
import com.exomatik.diagnosapenyakit.utils.Constant.referenceUser
import com.exomatik.diagnosapenyakit.utils.DataSave
import com.exomatik.diagnosapenyakit.utils.FirebaseUtils
import com.exomatik.diagnosapenyakit.utils.dismissKeyboard
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class LoginViewModel(
    private val navController: NavController,
    private val savedData: DataSave?,
    private val activity: Activity?) : BaseViewModel() {
    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    fun buttonDaftar(){
        navController.navigate(R.id.registerFragment)
    }

    fun buttonLogin(){
        try {
            dismissKeyboard(
                activity ?: throw Exception("")
            )
        } catch (e: Exception) {
        }

        isShowLoading.value = true

        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(result: DatabaseError) {
                isShowLoading.value = false
                message.value = "Error, ${result.message}"
            }

            override fun onDataChange(result: DataSnapshot) {
                isShowLoading.value = false
                if (result.exists()) {
                    for (snapshot in result.children) {
                        val data = snapshot.getValue(ModelUser::class.java)

                        if (data?.password == password.value){
                            message.value = "Berhasil masuk ke akun Anda"
                            savedData?.setDataObject(data, referenceUser)
                            navController.navigate(R.id.splashFragment)
                        }
                        else{
                            message.value = "Password Anda salah"
                        }
                    }
                } else {
                    isShowLoading.value = false
                    message.value = "Gagal, Username belum terdaftar"
                }
            }
        }

        FirebaseUtils.searchDataWith1ChildObject(
            referenceUser, Constant.username
            , username.value
            , valueEventListener
        )
    }
}