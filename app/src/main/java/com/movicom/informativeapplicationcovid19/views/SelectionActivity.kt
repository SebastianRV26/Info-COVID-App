package com.movicom.informativeapplicationcovid19.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.movicom.informativeapplicationcovid19.R
import com.movicom.informativeapplicationcovid19.models.Country
import com.movicom.informativeapplicationcovid19.viewmodel.CountryViewModel
import kotlinx.android.synthetic.main.activity_selection.*

/**
 * Controlador de vista: Actividad de selección del país.
 */
class SelectionActivity : AppCompatActivity(), View.OnClickListener,
    SearchView.OnQueryTextListener, AdapterView.OnItemClickListener {

    private lateinit var actualCountries: ArrayList<String>
    private lateinit var viewModel: CountryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)

        viewModel = ViewModelProvider(this).get(CountryViewModel::class.java)
        viewModel.refresh()

        svCountry!!.setOnQueryTextListener(this)
        svCountry!!.onActionViewExpanded()
        lvCountries.setOnItemClickListener(this)

        observeViewModel()
    }

    private fun observeViewModel() {
        /*viewModel.countries.observe(this, Observer<List<Country>> { countries ->
            // mAdapter.updateData(countries)
        })*/

        viewModel.message.observe(this, Observer<String> {
            showAlert(viewModel.message.value)
        })
    }

    private fun showAlert(message: String?) {
        val error = getDialog(message)
        error.show()
    }

    private fun getDialog(message: String?): AlertDialog.Builder {
        return AlertDialog.Builder(this)
            .setMessage(message)
    }

    override fun onClick(p0: View?) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
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
        println("countries ${viewModel.countries.value}")
        viewModel.countries.value?.forEach {
            if (it.Country.contains(p0!!, true)) {
                suggestedCountries.add(it.Country)
                println("suggestedCountries encontrado")
            }
        }
        val mAdapter = ArrayAdapter<String>(
            this, android.R.layout.simple_list_item_1,
            suggestedCountries
        )
        lvCountries.adapter = mAdapter
        actualCountries = suggestedCountries
        return true
    }

    /**
     * Cuando se preciona un país.
     */
    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val countryName = actualCountries[p2]
        val country = viewModel.searchCountry(countryName)
        //val position = viewModel.countries.value?.indexOf(country)
        alertDialog(country!!.Slug)
    }

    private fun alertDialog(slug: String) {
        val alert = AlertDialog.Builder(this)
        alert.setTitle(R.string.alert_title_country)
            .setMessage(R.string.alert_description_country)
            .setPositiveButton(R.string.alert_ok) { _, _ -> //evento click
                nextActivity(slug)
            }
            .setNeutralButton(R.string.alert_no) { _, _ -> } //no hará nada, solo quitar el dialog
            .show()
    }

    /**
     * Guardar el diminutivo en SharedPreferences y pasar al MainActivity.
     *
     * @param slug diminutivo del país.
     */
    private fun nextActivity(slug: String) {
        val preferences = getSharedPreferences("data", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString("slug", slug)
        editor.apply()
        val intent = Intent(this@SelectionActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * Esconde el teclado.
     */
    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(viewRoot.windowToken, 0)
    }

}
