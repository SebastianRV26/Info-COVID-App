package com.movicom.informativeapplicationcovid19.views


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.movicom.informativeapplicationcovid19.R
import com.movicom.informativeapplicationcovid19.adapters.CountryAdapter
import com.movicom.informativeapplicationcovid19.models.Country
import com.movicom.informativeapplicationcovid19.network.Api
import com.movicom.informativeapplicationcovid19.network.CountyService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A simple [Fragment] subclass.
 */
class CountriesFragment : Fragment() {

    private lateinit var mRecyclerView: RecyclerView
    private val mAdapter: CountryAdapter = CountryAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_countries, container, false)
        setUpRecyclerView(view)
        return view
    }

    private fun setUpRecyclerView(view:View){
        mRecyclerView = view.findViewById(R.id.rvCountrys)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mAdapter.RecyclerAdapter(getCountries(), context!!)
        mRecyclerView.adapter = mAdapter
    }

    /**
     * @return lista de paises.
     */
    private fun getCountries (): MutableList<Country> {
        val listCountries: MutableList<Country> = mutableListOf()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.covid19api.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        val countyService = retrofit.create(CountyService::class.java)

        //Api.getInstance().getCountyService()
        countyService
            .getCountries().enqueue(object: Callback<List<Country>> {
                override fun onResponse(
                    call: Call<List<Country>>, response: Response<List<Country>>?) {
                    /*println("\n${response}")
                    if (response.isSuccessful) { // si success es true
                        println("\n${response.body()}")
                        // ballots = response.body()


                    } else {
                        //showMessage("Ha habido un error ${response.code()}, inténtelo más tarde")
                    }*/
                val countries = response?.body()
                println("\n"+ Gson().toJson(countries))
                }
                override fun onFailure(call: Call<List<Country>>, t:Throwable?) {
                    /*println("\n Error "+t!!.message.toString())
                    showMessage("Ha habido un error, inténtelo más tarde")*/
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
