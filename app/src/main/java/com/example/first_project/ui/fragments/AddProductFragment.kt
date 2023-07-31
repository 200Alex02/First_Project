package com.example.first_project.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.first_project.R
import com.example.first_project.databinding.FragmentAddProductBinding
import com.example.first_project.ui.BaseFragment
import com.example.first_project.ui.products.Product
import com.google.gson.Gson

class AddProductFragment : BaseFragment<FragmentAddProductBinding>(
    FragmentAddProductBinding::inflate
) {
    private lateinit var sharedPreferencesProduct: SharedPreferences
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferencesProduct =
            requireActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE)

        binding.previewButton.setOnClickListener {
            if (binding.brandProduct.text.isNotEmpty() && binding.descriptionProduct.text.isNotEmpty() &&
                binding.linkPhoto.text.isNotEmpty()
            ) {
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_addProductFragment_to_previewFragment)

                saveData()

            } else {
                Toast.makeText(requireActivity(), getString(R.string.fill_in_all_the_fiels), Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun saveData() {
        val addProduct = Product(
            binding.brandProduct.text.toString(),
            binding.descriptionProduct.text.toString(),
            binding.linkPhoto.text.toString()
        )

        val gson = Gson()
        val productDataGson = gson.toJson(addProduct)

        sharedPreferencesProduct.edit().putString(KEY_PRODUCT, productDataGson).apply()
    }

    companion object {
        const val KEY_PRODUCT = "product"
    }

}