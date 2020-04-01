package com.exomatik.diagnosapenyakit.ui.tambahPenyakit

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.navigation.fragment.findNavController
import com.exomatik.diagnosapenyakit.R
import com.exomatik.diagnosapenyakit.base.BaseFragmentBind
import com.exomatik.diagnosapenyakit.databinding.FragmentTambahPenyakitBinding
import com.exomatik.diagnosapenyakit.utils.Constant.tambahPenyakit
import kotlinx.android.synthetic.main.activity_main.*

class TambahPenyakitFragment : BaseFragmentBind<FragmentTambahPenyakitBinding>(){
    override fun getLayoutResource(): Int = R.layout.fragment_tambah_penyakit
    lateinit var viewModel: TambahPenyakitViewModel

    override fun myCodeHere() {
        bind.lifecycleOwner = this
        viewModel = TambahPenyakitViewModel(findNavController(), context, activity, bind.root, savedData)
        bind.viewModel = viewModel

        activity?.toolbar?.visibility = View.VISIBLE
        activity?.toolbar?.title = tambahPenyakit
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