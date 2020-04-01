package com.exomatik.diagnosapenyakit.ui.register

import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.exomatik.diagnosapenyakit.R
import com.exomatik.diagnosapenyakit.base.BaseFragmentBind
import com.exomatik.diagnosapenyakit.databinding.FragmentRegisterBinding
import com.exomatik.diagnosapenyakit.utils.Constant.daftar
import kotlinx.android.synthetic.main.activity_main.*

class RegisterFragment : BaseFragmentBind<FragmentRegisterBinding>(){
    override fun getLayoutResource(): Int = R.layout.fragment_register
    private lateinit var viewModel: RegisterViewModel

    override fun myCodeHere() {
        activity?.toolbar?.visibility = View.VISIBLE
        activity?.toolbar?.title = daftar
        bind.lifecycleOwner = this
        viewModel = RegisterViewModel(activity, savedData, findNavController(), bind.btnRadio, bind.root)
        bind.viewModel = viewModel
        onClick()
    }

    private fun onClick() {
        bind.etAlamat.editText?.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.buttonDaftar()
                return@OnEditorActionListener false
            }
            false
        })
    }
}