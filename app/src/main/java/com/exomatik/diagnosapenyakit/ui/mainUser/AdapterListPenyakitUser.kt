package com.exomatik.diagnosapenyakit.ui.mainUser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.exomatik.diagnosapenyakit.R
import com.exomatik.diagnosapenyakit.model.ModelPenyakit
import kotlinx.android.synthetic.main.item_penyakit.view.*

class AdapterListPenyakitUser(
    private val listAfiliasi: ArrayList<ModelPenyakit?>,
    private val onClik: (ModelPenyakit) -> Unit
) : RecyclerView.Adapter<AdapterListPenyakitUser.AfiliasiHolder>() {

    inner class AfiliasiHolder(private val itemAfiliasi: View) :
        RecyclerView.ViewHolder(itemAfiliasi) {
        fun bindAfiliasi(
            dataJadwal: ModelPenyakit,
            onClik: (ModelPenyakit) -> Unit) {

            itemAfiliasi.nama.text = dataJadwal.namaPenyakit
            itemAfiliasi.pengenalan.text = dataJadwal.pengenalan

            itemAfiliasi.setOnClickListener {
                onClik(dataJadwal)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AfiliasiHolder {
        return AfiliasiHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_penyakit,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = listAfiliasi.size
    override fun onBindViewHolder(holder: AfiliasiHolder, position: Int) {
        listAfiliasi[position]?.let { holder.bindAfiliasi(it, onClik) }
    }
}
