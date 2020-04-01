package com.exomatik.diagnosapenyakit.ui.listPenyakit

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.navigation.fragment.findNavController
import com.exomatik.diagnosapenyakit.R
import com.exomatik.diagnosapenyakit.base.BaseFragmentBind
import com.exomatik.diagnosapenyakit.databinding.FragmentListPenyakitBinding
import kotlinx.android.synthetic.main.activity_main.*

class ListPenyakitFragment : BaseFragmentBind<FragmentListPenyakitBinding>(){
    override fun getLayoutResource(): Int = R.layout.fragment_list_penyakit
    lateinit var viewModel: ListPenyakitViewModel

    override fun myCodeHere() {
        bind.lifecycleOwner = this
        viewModel = ListPenyakitViewModel(bind.rcPenyakit, context, findNavController(), savedData)
        bind.viewModel = viewModel

        activity?.toolbar?.visibility = View.VISIBLE
        activity?.toolbar?.title = "List Penyakit"
        setHasOptionsMenu(true)
        viewModel.initAdapter()
        viewModel.getListPenyakit()
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