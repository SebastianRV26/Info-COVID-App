package com.movicom.informativeapplicationcovid19.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import com.movicom.informativeapplicationcovid19.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var aboutFragment: AboutFragment
    private lateinit var casesFragment: CasesFragment
    private lateinit var actionBar:ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        actionBar = supportActionBar!!
        actionBar.title = getString(R.string.country_cases)

        val drawerToggle : ActionBarDrawerToggle = object: ActionBarDrawerToggle(
            this, drawerLayout, toolbar,(R.string.open), (R.string.close)){
        }

        drawerToggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        aboutFragment = AboutFragment()
        casesFragment = CasesFragment()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, casesFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }

    /**
     * Para cambiar de fragmento.
     *
     * @param menuItem menu del Navigation Drawer
     */
    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId){
            R.id.casesByCountry -> {
                actionBar.title = getString(R.string.country_cases)
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout, casesFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.about -> {
                actionBar.title = getString(R.string.about_page)
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
