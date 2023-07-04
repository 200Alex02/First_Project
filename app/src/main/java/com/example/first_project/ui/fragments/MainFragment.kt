package com.example.first_project.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.first_project.R
import com.example.first_project.ui.adapter.ProductAdapter
import com.example.first_project.databinding.FragmentMainBinding
import com.example.first_project.ui.favourite.favouriteItemsList
import com.example.first_project.products
import com.example.first_project.ui.products.Product

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)

        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter = ProductAdapter()

        val onItemClick = { product: Product ->
            favouriteItemsList.add(product)
            product.likeElement = true
            Unit
        }
        adapter.onItemClick = onItemClick

        binding.recyclerView.adapter = adapter
        adapter.submitList(products)

        return binding.root
    }

}