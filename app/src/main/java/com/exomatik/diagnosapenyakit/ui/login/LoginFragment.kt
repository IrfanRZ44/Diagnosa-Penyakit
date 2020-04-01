package com.exomatik.diagnosapenyakit.ui.login

import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.exomatik.diagnosapenyakit.R
import com.exomatik.diagnosapenyakit.base.BaseFragmentBind
import com.exomatik.diagnosapenyakit.databinding.FragmentLoginBinding
import com.exomatik.diagnosapenyakit.utils.Constant.masuk
import kotlinx.android.synthetic.main.activity_main.*

class LoginFragment : BaseFragmentBind<FragmentLoginBinding>(){
    override fun getLayoutResource(): Int = R.layout.fragment_login
    lateinit var viewModel: LoginViewModel

    override fun myCodeHere() {
        activity?.toolbar?.visibility = View.VISIBLE
        activity?.toolbar?.title = masuk
        bind.lifecycleOwner = this
        viewModel = LoginViewModel(findNavController(), savedData, activity)
        bind.viewModel = viewModel

        onClick()
    }

    private fun onClick() {
        bind.etPassword.editText?.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.buttonLogin()
                return@OnEditorActionListener false
            }
            false
        })
    }

}