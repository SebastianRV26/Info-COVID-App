package com.movicom.informativeapplicationcovid19.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.movicom.informativeapplicationcovid19.R
import com.movicom.informativeapplicationcovid19.models.Country

class CountryAdapter() : RecyclerView.Adapter<CountryAdapter.ViewHolder>(){
    private var countries: MutableList<Country>  = ArrayList()
    private lateinit var context: Context

    fun RecyclerAdapter(countries : MutableList<Country>, context: Context){
        this.countries = countries
        this.context = context
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = countries[position]
        holder.bind(item, context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_country, parent, false))
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val countryName = view.findViewById(R.id.CountryName) as TextView
        private val countryCases = view.findViewById(R.id.CountryCases) as TextView

        fun bind(country: Country, context: Context) {
            countryName.text = country.Country
            countryCases.text = country.ISO2
        }
    }
}