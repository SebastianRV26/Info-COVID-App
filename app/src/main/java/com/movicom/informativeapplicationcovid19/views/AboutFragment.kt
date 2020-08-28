package com.movicom.informativeapplicationcovid19.views


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.movicom.informativeapplicationcovid19.R
import com.movicom.informativeapplicationcovid19.utils.UIUtils
import com.squareup.picasso.Picasso


/**
 * Controlador de vista: Acerca de.
 */
class AboutFragment : Fragment(), View.OnClickListener {

    private var ivDeveloper:ImageView ?= null
    private var ivTEC:ImageView ?= null
    private var ivMovicom:ImageView ?= null
    private var ivGitHub:ImageView ?= null
    private var ivLinkedIn:ImageView ?= null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_about, container, false)
        ivDeveloper = view.findViewById(R.id.ivDeveloper)
        ivTEC = view.findViewById(R.id.ivTEC)
        ivMovicom = view.findViewById(R.id.ivMovicom)
        ivGitHub = view.findViewById(R.id.ivGitHub)
        ivLinkedIn = view.findViewById(R.id.ivLinkedIn)

        ivDeveloper!!.loadUrl(getString(R.string.dev_github))
        ivTEC!!.setOnClickListener(this)
        ivMovicom!!.setOnClickListener(this)
        ivGitHub!!.setOnClickListener(this)
        ivLinkedIn!!.setOnClickListener(this)
        return view
    }

    override fun onClick(p0: View?) {
        when(p0!!.id) {
            R.id.ivMovicom -> {
                loadUrl("facebook.com/${getString(R.string.movicom)}")
            }
            R.id.ivTEC -> {
                loadUrl(getString(R.string.tec))
            }
            R.id.ivGitHub -> {
                loadUrl("github.com/${getString(R.string.dev_github)}")
            }
            R.id.ivLinkedIn -> {
                loadUrl("linkedin.com/in/${getString(R.string.dev_linkedin)}")
            }
        }
    }

    private fun loadUrl(url:String){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.${url}/"))
        startActivity(intent)
    }

    private fun ImageView.loadUrl(url: String) {
        Picasso.with(context).load("https://avatars.githubusercontent.com/$url").into(this)
    }
}
