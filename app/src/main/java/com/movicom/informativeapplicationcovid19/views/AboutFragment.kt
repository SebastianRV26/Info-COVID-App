package com.movicom.informativeapplicationcovid19.views


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.movicom.informativeapplicationcovid19.R

/**
 * A simple [Fragment] subclass.
 */
class AboutFragment : Fragment(), View.OnClickListener {

    private var ivGitHub:ImageView ?= null
    private var ivLinkedIn:ImageView ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_about, container, false)
        ivGitHub = view.findViewById(R.id.ivGitHub)
        ivLinkedIn = view.findViewById(R.id.ivLinkedIn)

        ivGitHub!!.setOnClickListener(this)
        ivLinkedIn!!.setOnClickListener(this)
        return view
    }

    override fun onClick(p0: View?) {
        when(p0!!.id) {
            R.id.ivGitHub -> {
                Toast.makeText(activity, "SebastianRV26", Toast.LENGTH_LONG).show()
            }
            R.id.ivLinkedIn -> {
                Toast.makeText(activity, "sebastian-rojas-vargas", Toast.LENGTH_LONG).show()
            }
        }
    }


}
