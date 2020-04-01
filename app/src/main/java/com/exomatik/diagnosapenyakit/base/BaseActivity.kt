package com.exomatik.diagnosapenyakit.base

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.exomatik.diagnosapenyakit.utils.DataSave


abstract class BaseActivity : AppCompatActivity(){
    protected abstract fun myCodeHere()
    protected abstract fun getLayoutResource(): Int
    protected lateinit var savedData : DataSave

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(getLayoutResource())
        savedData = DataSave(this)

        myCodeHere()
    }
}