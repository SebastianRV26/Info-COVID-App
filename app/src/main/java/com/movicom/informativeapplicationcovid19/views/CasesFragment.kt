package com.movicom.informativeapplicationcovid19.views

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.movicom.informativeapplicationcovid19.R
import com.movicom.informativeapplicationcovid19.models.Cases
import com.movicom.informativeapplicationcovid19.utils.UIUtils
import com.movicom.informativeapplicationcovid19.viewmodel.CasesViewModel
import kotlinx.android.synthetic.main.fragment_cases.*

/**
 * Controlador de vista: Casos de un paises.
 */
class CasesFragment : Fragment(), View.OnClickListener {

    private lateinit var btnChangeCountry: Button
    private lateinit var preferences: SharedPreferences
    private lateinit var slug: String
    private lateinit var viewModel: CasesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_cases, container, false)
        preferences = this.activity!!.getSharedPreferences("data", Context.MODE_PRIVATE)
        slug = preferences.getString("slug", "")!!

        btnChangeCountry = view.findViewById(R.id.btnChangeCountry)

        btnChangeCountry.setOnClickListener(this)

        viewModel = ViewModelProvider(this).get(CasesViewModel::class.java)
        viewModel.refresh(slug)

        observeViewModel()
        return view
    }

    private fun observeViewModel() {
        viewModel.cases.observe(viewLifecycleOwner, Observer<List<Cases>> { cases ->
            setData()
        })

        viewModel.message.observe(viewLifecycleOwner, Observer<String> {
            showAlert(viewModel.message.value)
        })
    }

    private fun showAlert(message: String?) {
        val error = getDialog(message)
        error.show()
    }

    private fun getDialog(message: String?): AlertDialog.Builder {
        return AlertDialog.Builder(requireContext())
            .setMessage(message)
    }

    /**
     * Mostrar los casos del país.
     */
    private fun setData() {
        val body = viewModel.cases.value
        if (body?.size == 0) {
            UIUtils.showMessage(context!!, getString(R.string.msj_no_country))
            changeActivity()
        } else {
            val cases = body!![body.size - 1]
            val date = (cases.Date).split("-")
            tvCountry!!.text = getString(R.string.country).plus(" ")
                .plus(cases.Country)
            tvDate!!.text = getString(R.string.date).plus(" ")
                .plus(date[2][0]).plus(date[2][1]).plus("/")
                .plus(date[1]).plus("/").plus(date[0])
            tvConfirmed!!.text = getString(R.string.confirmed)
                .plus(" ").plus(cases.Confirmed)
            tvDeaths!!.text = getString(R.string.deaths).plus(" ")
                .plus(cases.Deaths)
            tvRecovered!!.text = getString(R.string.recovered)
                .plus(" ").plus(cases.Recovered)
            tvActive!!.text = getString(R.string.active).plus(" ")
                .plus(cases.Active)

            val cases2 = body[body.size - 2]
            tvNewConfirmed!!.text = getString(R.string.confirmed).plus(" ")
                .plus(cases.Confirmed - cases2.Confirmed)
            tvNewDeaths!!.text = getString(R.string.deaths).plus(" ")
                .plus(cases.Deaths - cases2.Deaths)
            tvNewRecovered!!.text = getString(R.string.recovered)
                .plus(" ").plus(cases.Recovered - cases2.Recovered)
            tvNewActive!!.text = getString(R.string.active).plus(" ")
                .plus(cases.Active - cases2.Active)
        }
    }

    override fun onClick(p0: View?) {
        alertDialog()
    }

    /**
     * Dialogo de confirmación.
     */
    private fun alertDialog() {
        val alert = AlertDialog.Builder(context!!)
        alert.setTitle(R.string.alert_title_cases)
            .setPositiveButton(R.string.alert_ok) { _, _ -> //evento click
                changeActivity()
            }
            .setNeutralButton(R.string.alert_no) { _, _ -> } //no hará nada, solo quitar el dialog
            .show()
    }

    /**
     * Cambiar de actividad.
     */
    private fun changeActivity() {
        val editor = preferences.edit()
        editor.putString("slug", "")
        editor.apply()
        val intent = Intent(context, SelectionActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

}
