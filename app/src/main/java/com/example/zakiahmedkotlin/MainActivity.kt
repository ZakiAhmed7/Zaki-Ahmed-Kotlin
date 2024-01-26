package com.example.zakiahmedkotlin

import android.app.Dialog

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.zakiahmedkotlin.databinding.ActivityMainBinding
import com.example.zakiahmedkotlin.ui.Weather.WeatherFragment
import com.example.zakiahmedkotlin.ui.home.HomeFragment

import com.google.android.material.navigation.NavigationView

/*
AppBarConfiguration is used for NavigationUI
onCreate
    inflating layoutInflater for binding
    setSupportActionBar will change the default toolbar to and actionbar
    initializing drawerLayout
    navView for sideNavigation
    ContentMain is where i can add common components on a particular fragment.
    AppBarConfiguration adding drawer navigation
    navController => content_main where common elements are declared for MainActivity
    onCreateOptionsMenu will be for bottom navigation.
    onSupportNavigateUp => declaration and initializing of content_main.



 */
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    var pageChecker = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow),
            drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        fabFunctionality()


        binding.appBarMain.bottomNavigationViewMain.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.action_home -> {
                    pageChecker = "home"
                    replaceFragment(HomeFragment())
                    binding.appBarMain.fabMain.setImageDrawable(baseContext.getDrawable(R.drawable.icon_z))
                    true
                }

                R.id.action_weather -> {
                    pageChecker = "weather"
                    //Implement bottom drop down logic and then implement search bar
                    replaceFragment(WeatherFragment())
                    binding.appBarMain.fabMain.setImageDrawable(baseContext.getDrawable(R.drawable.search))
                    true
                }

                R.id.action_news -> {
                    Toast.makeText(baseContext, "News Button Clicked", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.action_settings -> {
                    Toast.makeText(baseContext, "Setting Button CLicked", Toast.LENGTH_SHORT).show()
                    true
                }

                else -> {
                    false
                }
            }
        }

    }

    private fun fabFunctionality() {
        binding.appBarMain.fabMain.setOnClickListener({
            if (pageChecker.isEmpty() || pageChecker === "home")
                openMyWebsite()
            else if (pageChecker === "weather")
                openAlertDialog()
        })
    }

    private fun openMyWebsite() {
        val webScreenFragment = WebScreenFragment()
        val bundle = Bundle()
        bundle.putString("URL", "https://zakiahmed7.github.io")
        webScreenFragment.arguments = bundle
        replaceFragment(webScreenFragment)
    }

    private fun openAlertDialog() {
//        val dialog : BottomSheetDialog = BottomSheetDialog(baseContext)

        val dialog: Dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        val view: View = layoutInflater.inflate(R.layout.search_bottom_alert_dialog, null, false)
        dialog.setContentView(view)

//        val searchView = view.findViewById<SearchView>(R.id.bad_searchView)

        val etSearch = view.findViewById<EditText>(R.id.etSearchBAD)
        val btnSubmit = view.findViewById<Button>(R.id.bad_search_btn)

        btnSubmit.setOnClickListener( {
            val bundle = Bundle()
            val weatherFragment = WeatherFragment()
            bundle.putString("cityName", etSearch.text.toString())
            Log.d("TAG", "-----" + etSearch.text.toString())
            weatherFragment.arguments = bundle
            replaceFragment(weatherFragment)
        })

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setGravity(Gravity.BOTTOM)

        dialog.show()

    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.main, menu)
//        return true
//    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun replaceFragment(fragment: Fragment) {
        var fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(
            R.id.nav_host_fragment_content_main,
            fragment
        )
            .addToBackStack("main")
            .commit()
    }
}