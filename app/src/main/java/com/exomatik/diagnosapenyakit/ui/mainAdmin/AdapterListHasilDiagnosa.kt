package com.exomatik.diagnosapenyakit.ui.mainAdmin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.exomatik.diagnosapenyakit.R
import com.exomatik.diagnosapenyakit.model.ModelHasilDiagnosa

class AdapterListHasilDiagnosa(
    private val listAfiliasi: ArrayList<ModelHasilDiagnosa?>,
    private val getDetail: (ModelHasilDiagnosa, View) -> Unit,
    private val deleteHasil: (ModelHasilDiagnosa, Int) -> Unit
) : RecyclerView.Adapter<AdapterListHasilDiagnosa.AfiliasiHolder>() {

    inner class AfiliasiHolder(private val itemAfiliasi: View) :
        RecyclerView.ViewHolder(itemAfiliasi) {

        fun bindAfiliasi(dataDiagnosa: ModelHasilDiagnosa,
                         deleteHasil: (ModelHasilDiagnosa, Int) -> Unit,
                         position: Int) {
            getDetail(dataDiagnosa, itemAfiliasi)

            itemAfiliasi.setOnLongClickListener {
                deleteHasil(dataDiagnosa, position)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AfiliasiHolder {
        return AfiliasiHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_hasil_diagnosa,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = listAfiliasi.size
    override fun onBindViewHolder(holder: AfiliasiHolder, position: Int) {
        listAfiliasi[position]?.let { holder.bindAfiliasi(it, deleteHasil, position) }
    }
}
