package com.exomatik.diagnosapenyakit.ui.mainUser

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.navigation.fragment.findNavController
import com.exomatik.diagnosapenyakit.R
import com.exomatik.diagnosapenyakit.base.BaseFragmentBind
import com.exomatik.diagnosapenyakit.databinding.FragmentMainUserBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainUserFragment : BaseFragmentBind<FragmentMainUserBinding>(){
    override fun getLayoutResource(): Int = R.layout.fragment_main_user
    lateinit var viewModel: MainUserViewModel

    override fun myCodeHere() {
        bind.lifecycleOwner = this
        viewModel = MainUserViewModel(bind.rcPenyakit, context, findNavController(), savedData)
        bind.viewModel = viewModel

        activity?.toolbar?.visibility = View.VISIBLE
        activity?.toolbar?.title = "Jenis Penyakit"
        setHasOptionsMenu(true)
        viewModel.initAdapter()
        viewModel.getListPenyakit()

        if (bind.swipeRefresh.isRefreshing) {
            bind.swipeRefresh.isRefreshing = false
        }

        onClick()
    }

    private fun onClick() {
        bind.swipeRefresh.setOnRefreshListener {
            viewModel.getListPenyakit()
            if (bind.swipeRefresh.isRefreshing) {
                bind.swipeRefresh.isRefreshing = false
            }
        }
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