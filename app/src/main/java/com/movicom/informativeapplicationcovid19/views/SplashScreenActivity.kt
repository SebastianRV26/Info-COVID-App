package com.movicom.informativeapplicationcovid19.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.movicom.informativeapplicationcovid19.MainActivity

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Comprueba si existe barra de navegación y la oculta.
        if (supportActionBar != null) supportActionBar!!.hide()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
