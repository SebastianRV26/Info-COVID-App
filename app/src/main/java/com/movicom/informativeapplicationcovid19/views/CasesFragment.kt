package com.movicom.informativeapplicationcovid19.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.movicom.informativeapplicationcovid19.R
import com.movicom.informativeapplicationcovid19.models.Cases
import com.movicom.informativeapplicationcovid19.models.Country
import com.movicom.informativeapplicationcovid19.network.Api
import kotlinx.android.synthetic.main.activity_selection.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Controlador de vista: Casos de un paises.
 */
class CasesFragment : Fragment(), View.OnClickListener {

    private var tvCountry:TextView ?= null
    private var tvDate:TextView ?= null
    private var tvConfirmed:TextView ?= null
    private var tvDeaths:TextView ?= null
    private var tvRecovered:TextView ?= null
    private var tvActive:TextView ?= null
    private var btnChangeCountry:Button ?= null

    private lateinit var slug:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_cases, container, false)
        val preferences = this.activity!!.getSharedPreferences("data", Context.MODE_PRIVATE)
        this.slug = preferences.getString("slug","")!!

        tvCountry = view.findViewById(R.id.tvCountry)
        tvDate = view.findViewById(R.id.tvDate)
        tvConfirmed = view.findViewById(R.id.tvConfirmed)
        tvDeaths = view.findViewById(R.id.tvDeaths)
        tvRecovered = view.findViewById(R.id.tvRecovered)
        tvActive = view.findViewById(R.id.tvActive)
        btnChangeCountry = view.findViewById(R.id.btnChangeCountry)

        btnChangeCountry!!.setOnClickListener(this)

        setData()
        return view
    }

    /**
     * Mostrar los casos del país.
     */
    private fun setData(){
        Api.getInstance().getCountyService()
            .getCountry(slug).enqueue(object: Callback<List<Cases>> {
                override fun onResponse(
                    call: Call<List<Cases>>, response: Response<List<Cases>>?) {
                    progress_bar.visibility = View.GONE
                    if (response?.isSuccessful!!) { // si success es true
                        val body = response.body()
                        if (body?.size == 0){
                            showMessage("Lo sentimos, no se han encontrado casos para este país")
                            changeActivity()
                        } else {
                            val case = body!![body.size - 1]
                            val date = (case.Date).split("-")
                            tvCountry!!.text = getString(R.string.country) + " " + case.Country
                            tvDate!!.text = getString(R.string.date) + " " +
                                    date[2][0] + date[2][1] + "/" + date[1] + "/" + date[0]
                            tvConfirmed!!.text =
                                getString(R.string.confirmed) + " " + case.Confirmed
                            tvDeaths!!.text = getString(R.string.deaths) + " " + case.Deaths
                            tvRecovered!!.text =
                                getString(R.string.recovered) + " " + case.Recovered
                            tvActive!!.text = getString(R.string.active) + " " + case.Active
                        }
                    } else {
                        showMessage("Ha habido un error ${response.code()}, inténtelo más tarde")
                    }
                }
                override fun onFailure(call: Call<List<Cases>>, t:Throwable?) {
                    println("\n Error "+t?.message.toString())
                    showMessage("Ha habido un error, inténtelo más tarde")
                    t?.printStackTrace()
                }
            })
    }

    override fun onClick(p0: View?) {
        alertDialog()
    }

    /**
     * Dialogo de confirmación.
     */
    private fun alertDialog(){
        val alert = AlertDialog.Builder(context!!)
        alert.setTitle(R.string.alert_title_cases)
            .setPositiveButton(R.string.alert_ok){_, _-> //evento click
                changeActivity()
            }
            .setNeutralButton(R.string.alert_no){_, _-> } //no hará nada, solo quitar el dialog
            .show()
    }

    /**
     * Cambiar de actividad.
     */
    private fun changeActivity(){
        val intent = Intent(context, SelectionActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    /**
     * Mostrar un mensaje en forma de Toast.
     *
     * @param message mensaje a mostrar.
     */
    private fun showMessage(message: String){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}
