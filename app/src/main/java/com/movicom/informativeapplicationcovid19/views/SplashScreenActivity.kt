package com.movicom.informativeapplicationcovid19.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Pantalla de carga.
 */
class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preferences = getSharedPreferences("data", Context.MODE_PRIVATE)
        val slug = preferences.getString("slug","")!!
        if (slug=="") {
            val intent = Intent(this, SelectionActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
