package com.example.first_project.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.first_project.R
import com.example.first_project.databinding.FragmentPreviewBinding
import com.example.first_project.products
import com.example.first_project.ui.products.Product
import com.google.gson.Gson


class PreviewFragment : Fragment() {

    private lateinit var binding: FragmentPreviewBinding
    private lateinit var sharedPreferencesProduct: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPreviewBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferencesProduct =
            requireActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE)

        val productDataGson = sharedPreferencesProduct.getString(KEY_PRODUCT, "")
        if (productDataGson != "") {
            val productData = Gson().fromJson(productDataGson, Product::class.java)
            binding.brand.text = productData.brand
            binding.description.text = productData.description
            Glide.with(requireContext())
                .load(productData.picture)
                .centerCrop()
                .error(R.drawable.error)
                .placeholder(R.drawable.error)
                .into(binding.picture)

            binding.icDelete.setOnClickListener {
                if (!products.contains(productData)) {
                    products.add(productData)
                    Navigation.findNavController(binding.root)
                        .navigate(R.id.action_previewFragment_to_item_home)
                }
            }
        }

    }

    companion object {
        const val KEY_PRODUCT = "product"
    }
}