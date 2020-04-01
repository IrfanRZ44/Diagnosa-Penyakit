package com.exomatik.diagnosapenyakit.ui.questioner

import android.view.View
import com.exomatik.diagnosapenyakit.R
import com.exomatik.diagnosapenyakit.base.BaseFragmentBind
import com.exomatik.diagnosapenyakit.databinding.FragmentQuestionBinding
import androidx.navigation.fragment.findNavController
import com.exomatik.diagnosapenyakit.model.ModelPenyakit
import com.exomatik.diagnosapenyakit.utils.Constant
import com.exomatik.diagnosapenyakit.utils.Constant.next
import kotlinx.android.synthetic.main.activity_main.*

class QuestionerFragment : BaseFragmentBind<FragmentQuestionBinding>(){
    override fun getLayoutResource(): Int = R.layout.fragment_question
    lateinit var viewModel: QuestionerViewModel

    override fun myCodeHere() {
        activity?.toolbar?.visibility = View.VISIBLE
        activity?.toolbar?.title = "Diagnosa"
        bind.lifecycleOwner = this
        val dataPenyakit = this.arguments?.getParcelable<ModelPenyakit>(Constant.dataPenyakit)
        viewModel = QuestionerViewModel(findNavController(), dataPenyakit, bind.rgAnswer, bind.seekBar, bind.root)
        bind.viewModel = viewModel
        viewModel.init()
        viewModel.posisi.value = 1
        viewModel.buttonNext.value = next
        viewModel.getQuestion()

    }

}