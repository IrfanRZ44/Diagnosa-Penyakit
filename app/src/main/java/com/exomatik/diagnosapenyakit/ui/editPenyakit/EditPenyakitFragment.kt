package com.exomatik.diagnosapenyakit.ui.editPenyakit

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.navigation.fragment.findNavController
import com.exomatik.diagnosapenyakit.R
import com.exomatik.diagnosapenyakit.base.BaseFragmentBind
import com.exomatik.diagnosapenyakit.databinding.FragmentEditPenyakitBinding
import com.exomatik.diagnosapenyakit.model.ModelPenyakit
import com.exomatik.diagnosapenyakit.utils.Constant
import com.exomatik.diagnosapenyakit.utils.Constant.editPenyakit
import kotlinx.android.synthetic.main.activity_main.*

class EditPenyakitFragment : BaseFragmentBind<FragmentEditPenyakitBinding>(){
    override fun getLayoutResource(): Int = R.layout.fragment_edit_penyakit
    lateinit var viewModel: EditPenyakitViewModel

    override fun myCodeHere() {
        bind.lifecycleOwner = this
        val dataPenyakit = this.arguments?.getParcelable<ModelPenyakit>(Constant.dataPenyakit)
        viewModel = EditPenyakitViewModel(findNavController(), activity, dataPenyakit)
        bind.viewModel = viewModel

        activity?.toolbar?.visibility = View.VISIBLE
        activity?.toolbar?.title = editPenyakit
        setHasOptionsMenu(true)
        viewModel.setDataPenyakit()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_delete, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_delete ->{
                viewModel.alertLogout()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}