package com.exomatik.diagnosapenyakit.ui.register

import android.app.Activity
import android.app.DatePickerDialog
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.exomatik.diagnosapenyakit.R
import com.exomatik.diagnosapenyakit.base.BaseViewModel
import com.exomatik.diagnosapenyakit.model.ModelUser
import com.exomatik.diagnosapenyakit.utils.Constant
import com.exomatik.diagnosapenyakit.utils.Constant.referenceUser
import com.exomatik.diagnosapenyakit.utils.Constant.username
import com.exomatik.diagnosapenyakit.utils.DataSave
import com.exomatik.diagnosapenyakit.utils.FirebaseUtils
import com.exomatik.diagnosapenyakit.utils.dismissKeyboard
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*

class RegisterViewModel(
    private val activity: Activity?,
    private val dataSave: DataSave?,
    private val navController: NavController,
    private val radioJK: RadioGroup,
    private val view: View
) : BaseViewModel() {
    val userName = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val nama = MutableLiveData<String>()
    val tempatLahir = MutableLiveData<String>()
    val tanggalLahir = MutableLiveData<String>()
    val alamat = MutableLiveData<String>()

    fun buttonLogin(){
        navController.navigate(R.id.loginFragment)
    }

    fun getDate() {
        val datePickerDialog: DatePickerDialog
        val localCalendar = Calendar.getInstance()

        try {
            datePickerDialog = DatePickerDialog(
                activity ?: throw Exception(""),
                DatePickerDialog.OnDateSetListener { _, paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3 ->
                    val dateSelected = Calendar.getInstance()
                    dateSelected[paramAnonymousInt1, paramAnonymousInt2] = paramAnonymousInt3
                    val dateFormatter = SimpleDateFormat("dd-MMM-yyyy", Locale.US)
                    tanggalLahir.value = dateFormatter.format(dateSelected.time)
                },
                localCalendar[Calendar.YEAR]
                ,
                localCalendar[Calendar.MONTH]
                ,
                localCalendar[Calendar.DATE]
            )

            datePickerDialog.show()
        } catch (e: java.lang.Exception) {
            message.value = e.message
        }

    }

    fun buttonDaftar() {
        try {
            dismissKeyboard(
                activity ?: throw Exception("Error, silahkan mulai ulang aplikasi")
            )
        } catch (e: Exception) {
        }

        isShowLoading.value = true
        try {
            val user = userName.value ?: throw Exception("Error, Username tidak boleh kosong")
            val pass = password.value ?: throw Exception("Error, Password tidak boleh kosong")
            val valueEventListener = object : ValueEventListener {
                override fun onCancelled(result: DatabaseError) {
                    isShowLoading.value = false
                    message.value = "Error, ${result.message}"
                }

                override fun onDataChange(result: DataSnapshot) {
                    if (result.exists()) {
                        isShowLoading.value = false
                        message.value = "Gagal, Username sudah digunakan"
                    } else {
                        val radio = radioJK.checkedRadioButtonId

                        if (radio <= 0){
                            throw Exception("Error, Anda belum memilih jenis kelamin")
                        }
                        else{
                            val btn  = view.findViewById<RadioButton?>(radio)
                            val dataUser = ModelUser(user, pass, nama.value?:"",
                                btn?.text.toString(), tempatLahir.value?:"",
                                tanggalLahir.value?:"", alamat.value?:"",
                                Constant.user)
                            addUserToFirebase(dataUser, user)
                        }

                    }
                }
            }
            FirebaseUtils.searchDataWith1ChildObject(
                referenceUser, username
                , user
                , valueEventListener
            )
        } catch (e: Exception) {
            isShowLoading.value = false
            message.value = e.message
        }
    }

    private fun addUserToFirebase(dataUser: ModelUser, userName: String) {
        val onCompleteListener =
            OnCompleteListener<Void> { result ->
                if (result.isSuccessful) {
                    isShowLoading.value = false
                    message.value = "Berhasil mendaftar"
                    dataSave?.setDataObject(dataUser, referenceUser)

                    navController.navigate(R.id.splashFragment)
                } else {
                    isShowLoading.value = false
                    message.value = "Gagal mendaftar"
                }
            }

        val onFailureListener = OnFailureListener { result ->
            isShowLoading.value = false
            message.value = result.message
        }
        FirebaseUtils.setValueObject(
            referenceUser
            , userName
            , dataUser
            , onCompleteListener
            , onFailureListener
        )
    }
}