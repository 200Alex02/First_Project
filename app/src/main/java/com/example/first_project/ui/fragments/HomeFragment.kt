package com.example.first_project.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.first_project.R
import com.example.first_project.databinding.FragmentHomeBinding
import com.example.first_project.ui.adapter.ProductAdapter
import com.example.first_project.ui.basefragment.BaseFragment
import com.example.first_project.ui.products.Product
import com.example.first_project.ui.room.ProductDb
import com.example.first_project.ui.room.ProductViewModel
import com.example.first_project.ui.room.ProductViewModelFactory
import java.util.Locale

class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {
    private lateinit var adapter: ProductAdapter
    private lateinit var productViewModel: ProductViewModel
    private var isFabVisible = true
    private val CHANNEL_ID = "channel_id"
    private val notificationId = 101
    private lateinit var sharedPreferences: SharedPreferences
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createNotificationChannel()

        val application = requireActivity().application
        productViewModel = ViewModelProvider(this, ProductViewModelFactory(application))[ProductViewModel::class.java]

        binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
        adapter = ProductAdapter(emptyList())

        val onItemClick = { productDb: ProductDb ->
            productViewModel.updateProduct(productDb.copy(likeElement = !productDb.likeElement))
        }

        val onFullItemClick = { productDb: ProductDb ->
            val product = Product(productDb)
            val action = HomeFragmentDirections.actionItemHomeToDetailProductFragment2(product)
            findNavController().navigate(action)
        }

        adapter.onItemClick = onItemClick
        adapter.onFullItemClick = onFullItemClick

        binding.recyclerView.adapter = adapter
        productViewModel.allProducts.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0 && isFabVisible) {
                    binding.floatingBtn.hide()
                    isFabVisible = false
                } else if (dy < 0 && !isFabVisible) {
                    binding.floatingBtn.show()
                    isFabVisible = true
                }
            }
        })

        binding.floatingBtn.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_item_home_to_addProductFragment)
        }

        checkTheme()

        checkScreen()

        checkLanguage()

    }

    private fun checkLanguage() {
        when (sharedPreferences.getString("switchLang", "")) {
            "English" -> {
                change("en")
            }

            "Russian" -> {
                change("ru")
            }

            else -> {

            }
        }
    }

    private fun checkScreen() {
        val switchScreen = sharedPreferences.getBoolean("switchScreen", false)

        if (switchScreen) {
            hideSystemUI()
        } else {
            showSystemUI()
        }
    }

    private fun checkTheme() {
        sharedPreferences =
            requireActivity().getSharedPreferences("MyPreference", Context.MODE_PRIVATE)

        val switchTheme = sharedPreferences.getBoolean("switchTheme", false)

        if (switchTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification title"
            val descriptionText = "Notification description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = requireActivity()
                .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification(productDb: ProductDb) {
        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("${productDb.brand} был добавлен в избранное")
            .setContentText("Зайдите в раздел избранное, чтобы посмотреть")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(requireContext())) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(notificationId, builder.build())
        }

    }

    private fun hideSystemUI() {
        activity?.window?.decorView?.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }

    private fun showSystemUI() {
        activity?.window?.decorView?.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION and View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
    }

    fun change(language: String) {
        val config = resources.configuration
        val locale = Locale(language)
        Locale.setDefault(locale)
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
        activity?.recreate()
    }

}