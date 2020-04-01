package com.exomatik.diagnosapenyakit.ui.landing

import androidx.navigation.NavController
import com.exomatik.diagnosapenyakit.R
import com.exomatik.diagnosapenyakit.base.BaseViewModel


class LandingViewModel(private val navController: NavController) : BaseViewModel() {
    fun buttonLogin(){
        navController.navigate(R.id.loginFragment)
    }

    fun buttonDaftar(){
        navController.navigate(R.id.registerFragment)
    }
}