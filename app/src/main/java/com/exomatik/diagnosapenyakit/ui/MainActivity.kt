package com.exomatik.diagnosapenyakit.ui

import android.os.Build
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import com.exomatik.diagnosapenyakit.R
import com.exomatik.diagnosapenyakit.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.view.MenuItem


class MainActivity : BaseActivity() {
    override fun getLayoutResource(): Int = R.layout.activity_main

    override fun myCodeHere() {
        setSupportActionBar(toolbar)

        NavHostFragment.create(R.navigation.main_nav)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            viewParent.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
        else{
            viewParent.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0){
            supportFragmentManager.popBackStack()
        }
        else{
            super.onBackPressed()
        }
    }
}
