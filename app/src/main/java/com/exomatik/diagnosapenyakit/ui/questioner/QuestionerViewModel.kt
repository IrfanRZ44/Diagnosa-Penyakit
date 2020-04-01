package com.exomatik.diagnosapenyakit.ui.questioner

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.exomatik.diagnosapenyakit.R
import com.exomatik.diagnosapenyakit.base.BaseViewModel
import com.exomatik.diagnosapenyakit.model.ModelPenyakit
import com.exomatik.diagnosapenyakit.model.ModelQuestioner
import com.exomatik.diagnosapenyakit.services.seekBar.BubbleSeekBar
import com.exomatik.diagnosapenyakit.ui.hasilDiagnosa.HasilDiagnosaFragment
import com.exomatik.diagnosapenyakit.utils.Constant
import com.exomatik.diagnosapenyakit.utils.Constant.diagnosa
import com.exomatik.diagnosapenyakit.utils.showLog
import java.util.*

class QuestionerViewModel(
    private val navController: NavController,
    private val dataPenyakit: ModelPenyakit?,
    private val rgAnswer: RadioGroup,
    private val seekBar: BubbleSeekBar,
    private val view: View
) : BaseViewModel() {
    val soal = MutableLiveData<String>()
    val posisi = MutableLiveData<Int>()
    private val hasilDiagnosa = ArrayList<ModelQuestioner>()
    val buttonNext = MutableLiveData<String>()

    fun init() {
        for (a in 0..10) {
            hasilDiagnosa.add(ModelQuestioner("-"))
        }

        seekBar.setOnTouchListener { _, _ -> true }
    }

    fun getQuestion() {
        when (posisi.value) {
            1 -> soal.value = dataPenyakit?.penyakit1
            2 -> soal.value = dataPenyakit?.penyakit2
            3 -> soal.value = dataPenyakit?.penyakit3
            4 -> soal.value = dataPenyakit?.penyakit4
            5 -> soal.value = dataPenyakit?.penyakit5
            6 -> soal.value = dataPenyakit?.penyakit6
            7 -> soal.value = dataPenyakit?.penyakit7
            8 -> soal.value = dataPenyakit?.penyakit8
            9 -> soal.value = dataPenyakit?.penyakit9
            10 -> {
                soal.value = dataPenyakit?.penyakit10
                buttonNext.value = diagnosa
            }
        }
    }

    fun gantiSoal() {
        if (posisi.value == 10) {
            val check = rgAnswer.checkedRadioButtonId

            if (check == -1) {
                message.value = "Mohon pilih salah satu jawaban"
            } else {
                val btn = view.findViewById<RadioButton?>(check)
                hasilDiagnosa[10] = ModelQuestioner(btn?.text.toString())
                val bundle = Bundle()
                val frag = HasilDiagnosaFragment()
                bundle.putParcelableArrayList(Constant.listDiagnosa, hasilDiagnosa)
                bundle.putParcelable(Constant.dataPenyakit, dataPenyakit)
                frag.arguments = bundle
                navController.navigate(R.id.hasilDiagnosaFragment, bundle)
            }
        } else {
            try {
                val check = rgAnswer.checkedRadioButtonId

                if (check == -1) {
                    message.value = "Mohon pilih salah satu jawaban"
                } else {
                    val btn = view.findViewById<RadioButton?>(check)
                    val progress =
                        posisi.value ?: throw Exception("Error, mohon keluar dari aplikasi")
                    hasilDiagnosa[progress] = ModelQuestioner(btn?.text.toString())
                    showLog(progress.toString() + ". " + btn?.text.toString())
                    posisi.value = posisi.value?.plus(1)
                    posisi.value?.toFloat()?.let { seekBar.setProgress(it) }
                    getQuestion()
                    rgAnswer.clearCheck()
                }

            } catch (e: Exception) {
                message.value = e.message
            }
        }
    }
}