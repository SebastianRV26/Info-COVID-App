package com.movicom.informativeapplicationcovid19.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.movicom.informativeapplicationcovid19.R

class CountryAdapter(val listCountries: List<String>) : RecyclerView.Adapter<CountryAdapter.ViewHolder>(){
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listCountries[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_country, parent, false))
    }

    override fun getItemCount(): Int {
        return listCountries.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(image: String) {
        }
    }
}