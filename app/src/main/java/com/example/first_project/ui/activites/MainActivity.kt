package com.example.first_project.ui.activites



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.first_project.R
import com.example.first_project.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private var previousFragmentId: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.nav_host_fragment)
        setupActionBarWithNavController(navController)
        binding.bottomNavView.setupWithNavController(navController)
    }
    override fun onSupportNavigateUp(): Boolean {

        val currentDestination = navController.currentDestination?.id
        val secondFragment = R.id.item_favourite
        val firstFragment = R.id.item_home

        when (currentDestination) {
            R.id.item_settings -> {
                navController.navigate(secondFragment)
                return true
            }

            secondFragment -> {
                navController.navigate(firstFragment)
                return true
            }
        }
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}

