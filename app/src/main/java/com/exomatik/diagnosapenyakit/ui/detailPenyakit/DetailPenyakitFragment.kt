package com.exomatik.diagnosapenyakit.ui.detailPenyakit

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.navigation.fragment.findNavController
import com.exomatik.diagnosapenyakit.R
import com.exomatik.diagnosapenyakit.base.BaseFragmentBind
import com.exomatik.diagnosapenyakit.databinding.FragmentDetailPenyakitBinding
import com.exomatik.diagnosapenyakit.model.ModelPenyakit
import com.exomatik.diagnosapenyakit.utils.Constant
import kotlinx.android.synthetic.main.activity_main.*

class DetailPenyakitFragment : BaseFragmentBind<FragmentDetailPenyakitBinding>(){
    override fun getLayoutResource(): Int = R.layout.fragment_detail_penyakit
    lateinit var viewModel: DetailPenyakitViewModel

    override fun myCodeHere() {
        activity?.toolbar?.visibility = View.GONE
        bind.lifecycleOwner = this
        val dataPenyakit = this.arguments?.getParcelable<ModelPenyakit>(Constant.dataPenyakit)
        viewModel = DetailPenyakitViewModel(dataPenyakit, savedData, findNavController(), context)
        bind.viewModel = viewModel

        setHasOptionsMenu(true)
        activity?.toolbar?.visibility = View.VISIBLE
        activity?.toolbar?.title = "Detail Penyakit"
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_setting, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_logout ->{
                viewModel.alertLogout()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}