package com.movicom.informativeapplicationcovid19.views


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.movicom.informativeapplicationcovid19.R
import com.movicom.informativeapplicationcovid19.adapters.CountryAdapter
import com.movicom.informativeapplicationcovid19.models.Country
import com.movicom.informativeapplicationcovid19.network.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Controlador de vista: Casos de un paises.
 */
class CasesFragment : Fragment() {

    private lateinit var mRecyclerView: RecyclerView
    private var mAdapter: CountryAdapter = CountryAdapter()
    private lateinit var countries: ArrayList<Country>
    private lateinit var slug:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_cases, container, false)
        val preferences = this.activity!!.getSharedPreferences("data", Context.MODE_PRIVATE)
        this.slug = preferences.getString("slug","")!!
        setUpRecyclerView(view)
        return view
    }

    private fun setUpRecyclerView(view:View){
        mRecyclerView = view.findViewById(R.id.rvCountrysxd)
        //mRecyclerView.setHasFixedSize(true)
        countries = getCountries()
        //mAdapter.recyclerAdapter(countries)
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
    }

    /**
     * @return lista de paises.
     */
    private fun getCountries (): ArrayList<Country> {
        val listCountries: ArrayList<Country> = arrayListOf()
        Api.getInstance().getCountyService()
            .getCountries().enqueue(object: Callback<List<Country>> {
                override fun onResponse(
                    call: Call<List<Country>>, response: Response<List<Country>>?) {
                    if (response?.isSuccessful!!) { // si success es true
                        val body = response.body()
                        body?.forEach {
                            listCountries .add(it)
                        }
                        mAdapter.notifyDataSetChanged()
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
        return listCountries
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
