package com.example.first_project.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.first_project.R
import com.example.first_project.databinding.FragmentPreviewBinding
import com.example.first_project.products
import com.example.first_project.ui.BaseFragment
import com.example.first_project.ui.products.Product
import com.google.gson.Gson

class PreviewFragment : BaseFragment<FragmentPreviewBinding>(
    FragmentPreviewBinding::inflate
) {
    private lateinit var sharedPreferencesProduct: SharedPreferences
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

            binding.addProductBtn.setOnClickListener {
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