package com.movicom.informativeapplicationcovid19.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.movicom.informativeapplicationcovid19.R
import com.movicom.informativeapplicationcovid19.models.Country
import com.movicom.informativeapplicationcovid19.network.Api
import kotlinx.android.synthetic.main.activity_selection.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Controlador de vista: Actividad de selección del país.
 */
class SelectionActivity : AppCompatActivity(), View.OnClickListener,
    SearchView.OnQueryTextListener, AdapterView.OnItemClickListener {

    private var svCountry: SearchView?= null
    private var btnCpuntry:Button ?= null
    private var countries:ArrayList<String> = arrayListOf() // Lista de paises.
    private var slugs:ArrayList<String> = arrayListOf() // Lista de diminutivos de paises.
    private var lvCountries:ListView ?= null
    private lateinit var actualCountries:ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)

        svCountry = findViewById(R.id.svCountry)
        lvCountries = findViewById(R.id.lvCountries)
        btnCpuntry = findViewById(R.id.btnCpuntry)

        svCountry!!.setOnQueryTextListener(this)
        btnCpuntry!!.setOnClickListener(this)

        lvCountries!!.setOnItemClickListener(this)

        getCountries()
    }

    override fun onClick(p0: View?) {
        showMessage("Tamaño: "+countries.size)
        val intent = Intent (this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * @return lista de paises.
     */
    private fun getCountries() {
        Api.getInstance().getCountyService()
            .getCountries().enqueue(object: Callback<List<Country>> {
                override fun onResponse(
                    call: Call<List<Country>>, response: Response<List<Country>>?) {
                    progress_bar.visibility = View.GONE
                    svCountry!!.onActionViewExpanded()
                    if (response?.isSuccessful!!) { // si success es true
                        val body = response.body()
                        body?.forEach {
                            countries.add(it.Country)
                            slugs.add(it.ISO2)
                        }
                    } else {
                        showMessage("Ha habido un error ${response.code()}, inténtelo más tarde")
                    }
                }
                override fun onFailure(call: Call<List<Country>>, t:Throwable?) {
                    println("\n Error "+t?.message.toString())
                    showMessage("Ha habido un error, inténtelo más tarde")
                    t?.printStackTrace()
                }
            })
    }

    /**
     * Cuando termina de escribir en el SearchView.
     */
    override fun onQueryTextSubmit(p0: String?): Boolean {
        hideKeyboard()
        return true
    }

    /**
     * Cada vez que escribe en el SearchView.
     */
    override fun onQueryTextChange(p0: String?): Boolean {
        val suggestedCountries = arrayListOf<String>()
        countries.forEach{
            if (it.contains(p0!!,true)){
                suggestedCountries.add(it)
            }
        }
        val mAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
            suggestedCountries)
        lvCountries!!.adapter = mAdapter
        actualCountries = suggestedCountries
        return true
    }

    /**
     * Cuando se preciona un país.
     */
    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val country = actualCountries[p2]
        println("\n country "+country)
        val position = countries.indexOf(country)
        println("\n position "+position)
        alertDialog(slugs[position])
    }

    private fun alertDialog(slug: String){
        val alert = AlertDialog.Builder(this)
        alert.setTitle(R.string.alert_title_country)
            .setMessage(R.string.alert_description_country)
            .setPositiveButton(R.string.alert_ok){_, _-> //evento click
                nextActivity(slug)
            }
            .setNeutralButton(R.string.alert_no){_, _-> } //no hará nada, solo quitar el dialog
            .show()
    }

    /**
     * Guardar el diminutivo en SharedPreferences y pasar al MainActivity.
     *
     * @param slug diminutivo del país.
     */
    private fun nextActivity(slug:String){
        val preferences = getSharedPreferences("data", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString("slug", slug)
        editor.apply()
        println("\n Preferencias: "+preferences.getString("slug", ""))
        showMessage(preferences.getString("slug", "")!!)
        editor.apply()
        val intent = Intent(this@SelectionActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * Esconde el teclado.
     */
    private fun hideKeyboard(){
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(viewRoot.windowToken, 0)
    }

    /**
     * Mostrar un mensaje en forma de Toast.
     *
     * @param message mensaje a mostrar.
     */
    private fun showMessage(message: String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}
