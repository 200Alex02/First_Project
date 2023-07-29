package com.example.first_project.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.first_project.R
import com.example.first_project.ui.adapter.ProductAdapter
import com.example.first_project.databinding.FragmentHomeBinding
import com.example.first_project.ui.favourite.favouriteItemsList
import com.example.first_project.products
import com.example.first_project.ui.BaseFragment
import com.example.first_project.ui.products.Product

class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {
    private lateinit var adapter: ProductAdapter
    @SuppressLint("ShowToast")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
        adapter = ProductAdapter()

        val onItemClick = { product: Product ->
            if (!favouriteItemsList.contains(product)) {
                favouriteItemsList.add(product)
                product.likeElement = true
                Unit
            }
            else {
                val toast = Toast.makeText(requireContext(), getString(R.string.toast_add), Toast.LENGTH_SHORT).show()
            }
        }
        adapter.onItemClick = onItemClick

        binding.recyclerView.adapter = adapter
        adapter.submitList(products)

        binding.floatingBtn.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_item_home_to_addProductFragment)
        }
    }

}