package com.exomatik.diagnosapenyakit.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel (){
    val isShowLoading = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()
}