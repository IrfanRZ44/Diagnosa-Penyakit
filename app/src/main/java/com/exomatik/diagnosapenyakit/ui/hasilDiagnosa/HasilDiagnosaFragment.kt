package com.exomatik.diagnosapenyakit.ui.hasilDiagnosa

import android.view.View
import androidx.navigation.fragment.findNavController
import com.exomatik.diagnosapenyakit.R
import com.exomatik.diagnosapenyakit.base.BaseFragmentBind
import com.exomatik.diagnosapenyakit.databinding.FragmentHasilDiagnosaBinding
import com.exomatik.diagnosapenyakit.model.ModelPenyakit
import com.exomatik.diagnosapenyakit.model.ModelQuestioner
import com.exomatik.diagnosapenyakit.utils.Constant
import kotlinx.android.synthetic.main.activity_main.*

class HasilDiagnosaFragment : BaseFragmentBind<FragmentHasilDiagnosaBinding>(){
    override fun getLayoutResource(): Int = R.layout.fragment_hasil_diagnosa
    lateinit var viewModel: HasilDiagnosaViewModel

    override fun myCodeHere() {
        activity?.toolbar?.visibility = View.VISIBLE
        activity?.toolbar?.title = "Hasil Diagnosa"
        bind.lifecycleOwner = this

        val dataPenyakit = this.arguments?.getParcelable<ModelPenyakit>(Constant.dataPenyakit)
        val listQuestioner = this.arguments?.getParcelableArrayList<ModelQuestioner>(Constant.listDiagnosa)
        viewModel = HasilDiagnosaViewModel(savedData, dataPenyakit, listQuestioner, findNavController())
        bind.viewModel = viewModel
        viewModel.setData()
        viewModel.point()

    }

}