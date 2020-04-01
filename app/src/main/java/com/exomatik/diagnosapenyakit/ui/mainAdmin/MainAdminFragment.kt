package com.exomatik.diagnosapenyakit.ui.mainAdmin

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.navigation.fragment.findNavController
import com.exomatik.diagnosapenyakit.R
import com.exomatik.diagnosapenyakit.base.BaseFragmentBind
import com.exomatik.diagnosapenyakit.databinding.FragmentMainAdminBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainAdminFragment : BaseFragmentBind<FragmentMainAdminBinding>(){
    override fun getLayoutResource(): Int = R.layout.fragment_main_admin
    lateinit var viewModel: MainAdminViewModel

    override fun myCodeHere() {
        bind.lifecycleOwner = this
        viewModel = MainAdminViewModel(findNavController(), context, savedData, bind.rcDiagnosa)
        bind.viewModel = viewModel
        viewModel.initAdapter()
        viewModel.getListDiagnosa()

        activity?.toolbar?.visibility = View.VISIBLE
        activity?.toolbar?.title = "Admin"
        setHasOptionsMenu(true)
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