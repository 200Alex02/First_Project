package com.example.first_project.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.first_project.R
import com.example.first_project.databinding.FragmentHomeBinding
import com.example.first_project.ui.adapter.ProductAdapter
import com.example.first_project.ui.favourite.favouriteItemsList
import com.example.first_project.products
import com.example.first_project.ui.BaseFragment
import com.example.first_project.ui.products.Product

class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {
    private lateinit var adapter: ProductAdapter
    private var isFabVisible = true
    private val CHANNEL_ID = "channel_id"
    private val notificationId = 101

    @SuppressLint("ShowToast")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createNotificationChannel()

        binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
        adapter = ProductAdapter()

        val onItemClick = { product: Product ->
            if (!favouriteItemsList.contains(product)) {
                favouriteItemsList.add(product)
                product.likeElement = true
                sendNotification(product)
            } else {
                Toast.makeText(requireContext(), getString(R.string.toast_add), Toast.LENGTH_SHORT)
                    .show()
            }
        }

        /*val onFullItemClick = { product: Product ->
            val action = HomeFragmentDirections.actionItemHomeToDetailProductFragment2(product)
            findNavController().navigate(action)
        }*/

        adapter.onItemClick = onItemClick
        /*adapter.onFullItemClick = onFullItemClick*/

        binding.recyclerView.adapter = adapter
        adapter.submitList(products)

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

    private fun sendNotification(product: Product) {
        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("${product.brand} был добавлен в избранное")
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

}