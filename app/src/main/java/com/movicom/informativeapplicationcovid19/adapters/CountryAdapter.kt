package com.movicom.informativeapplicationcovid19.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.movicom.informativeapplicationcovid19.R

class CountryAdapter : RecyclerView.Adapter<CountryAdapter.ViewHolder>(){
    private var countries: ArrayList<String>  = ArrayList()

    fun recyclerAdapter(countries : ArrayList<String>){
        this.countries = countries
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = countries[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_country, parent, false))
    }

    override fun getItemCount(): Int {
        return this.countries.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val countryName = view.findViewById(R.id.CountryName) as TextView

        fun bind(country: String) {
            countryName.text = country
        }
    }
}