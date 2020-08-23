package com.movicom.informativeapplicationcovid19

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import com.movicom.informativeapplicationcovid19.views.AboutFragment
import com.movicom.informativeapplicationcovid19.views.CountriesFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var aboutFragment: AboutFragment
    lateinit var countriesFragment: CountriesFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar!!.title = "Navigation Drawer"

        val drawerToggle : ActionBarDrawerToggle = object: ActionBarDrawerToggle(
            this, drawerLayout, toolbar,(R.string.open), (R.string.close)){
        }

        drawerToggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        countriesFragment = CountriesFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, countriesFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId){
            R.id.casesByCountry -> {
                countriesFragment = CountriesFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout, countriesFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.about -> {
                aboutFragment = AboutFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout, aboutFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressed()
        }
    }

}
