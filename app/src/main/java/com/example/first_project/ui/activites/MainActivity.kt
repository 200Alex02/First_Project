package com.example.first_project.ui.activites


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.first_project.R
import com.example.first_project.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.nav_host_fragment)
        setupActionBarWithNavController(navController)
        binding.bottomNavView.setupWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.up_menu, menu)
        return true
    }*/
/*
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.add_your_elements -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, AddProductFragment())
                    .commit()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }*/

    /*override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val menuItem = menu?.findItem(R.id.add_your_elements)
        menuItem?.isVisible =
            when (supportFragmentManager.findFragmentById(R.id.nav_host_fragment)) {
                is MainFragment -> true
                else -> false
            }
        return true
    }*/
}

