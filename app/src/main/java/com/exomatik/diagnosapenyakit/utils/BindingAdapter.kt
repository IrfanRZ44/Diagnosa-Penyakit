package com.exomatik.diagnosapenyakit.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.exomatik.diagnosapenyakit.R
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

fun showLog(message: String?){
    Log.e("Error", "$message This log")
}

fun dismissKeyboard(activity: Activity) {
    val imm: InputMethodManager =
        activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (null != activity.currentFocus) imm.hideSoftInputFromWindow(
        activity.currentFocus!!
            .applicationWindowToken, 0
    )
}

@BindingAdapter("app:visible")
fun setVisibility(view: View?, isVisible: Boolean) {
    if (isVisible)
        view?.visibility = View.VISIBLE
    else
        view?.visibility = View.GONE
}

@BindingAdapter("app:toast")
fun showMessage(view: View?, message: String?) {
    if (message != null) {
        Toast.makeText(view?.context, message, Toast.LENGTH_LONG).show()
    }
}

@BindingAdapter("app:snackbar")
fun showSnackbar(view: View?, message: String?) {
    try {
        if (message != null) {
            val snackbar =
                Snackbar.make(view ?: throw Exception("No View"), "", Snackbar.LENGTH_LONG)

            val v = snackbar.view

            if (message.contains("Berhasil")) {
                v.background = ContextCompat.getDrawable(view.context, R.drawable.snakbar_green)
            } else if (message.contains("Error") || message.contains("Gagal") || message.contains("Afwan,")) {
                v.background = ContextCompat.getDrawable(view.context, R.drawable.snakbar_red)
            } else {
                v.background = ContextCompat.getDrawable(view.context, R.drawable.snakbar_blue)
            }
            val tv =
                v.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView

            tv.setTextColor(Color.parseColor("#FFFFFF"))

            snackbar.setText(message)
            snackbar.show()
        }
    } catch (e: Exception) {
        Log.e("Error", e.message + " Message Error")
    }
}

@BindingAdapter("app:showTextStatus")
fun showTextStatus(appCompatTextView: AppCompatTextView, message: String?) {
    try {
        if (message != null) {
            if (message.contains("Berhasil")) {
                appCompatTextView.setTextColor(Color.parseColor("#52af44"))
            } else if (message.contains("Error") || message.contains("Gagal")) {
                appCompatTextView.setTextColor(Color.RED)
            } else if (message.contains("belum")){
                appCompatTextView.setTextColor(Color.parseColor("#757575"))
            } else{
                appCompatTextView.setTextColor(Color.BLUE)
            }
        }
        appCompatTextView.text = message
    } catch (e: Exception) {
        Log.e("Error", e.message + " Message Error")
    }
}

@BindingAdapter("app:disableButton")
fun disableButton(view: View?, isEnable: Boolean) {
    view?.isEnabled = !isEnable
}

@BindingAdapter("app:errorText")
fun setErrorMessage(view: TextInputLayout, value: String?) {
    if (view.isClickable) {
        if (value == null) {
            view.error = "Data tidak boleh kosong"
        } else {
            view.error = null
        }
    }
}
