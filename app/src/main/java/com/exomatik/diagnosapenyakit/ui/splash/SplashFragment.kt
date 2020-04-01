package com.exomatik.diagnosapenyakit.ui.splash

import android.os.Handler
import android.view.View
import androidx.navigation.fragment.findNavController
import com.exomatik.diagnosapenyakit.R
import com.exomatik.diagnosapenyakit.base.BaseFragment
import com.exomatik.diagnosapenyakit.utils.Constant.admin
import kotlinx.android.synthetic.main.activity_main.*


class SplashFragment : BaseFragment() {
    override fun getLayoutResource(): Int = R.layout.fragment_splash

    override fun myCodeHere() {
        activity?.toolbar?.visibility = View.GONE
        Handler().postDelayed({
            if (savedData.getDataUser()?.username.isNullOrEmpty()) {
                findNavController().navigate(R.id.landingFragment)
            } else {
                if (savedData.getDataUser()?.jenisAkun == admin){
                    findNavController().navigate(R.id.mainAdminFragment)
                }
                else{
                    findNavController().navigate(R.id.mainUserFragment)
                }
            }
        }, 2000L)
    }

}