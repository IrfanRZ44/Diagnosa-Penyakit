package com.exomatik.diagnosapenyakit.ui.landing

import android.view.View
import com.exomatik.diagnosapenyakit.R
import com.exomatik.diagnosapenyakit.base.BaseFragmentBind
import com.exomatik.diagnosapenyakit.databinding.FragmentLandingBinding
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.activity_main.*

class LandingFragment : BaseFragmentBind<FragmentLandingBinding>(){
    override fun getLayoutResource(): Int = R.layout.fragment_landing
    lateinit var viewModel: LandingViewModel

    override fun myCodeHere() {
        activity?.toolbar?.visibility = View.GONE
        bind.lifecycleOwner = this
        viewModel = LandingViewModel(findNavController())
        bind.viewModel = viewModel
    }

}