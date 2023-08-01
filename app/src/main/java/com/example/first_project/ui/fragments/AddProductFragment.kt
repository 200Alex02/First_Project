package com.example.first_project.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

            if (!binding.etBrand.text.isNullOrEmpty() && !binding.etLink.text.isNullOrEmpty()
                && !binding.etDescription.text.isNullOrEmpty()
            ) {
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_addProductFragment_to_previewFragment)

                saveData()
            } else {
                setErrorForEditText()
            }
        }
    }

    private fun saveData() {
        val addProduct = Product(
            binding.etBrand.text.toString(),
            binding.etDescription.text.toString(),
            binding.etLink.text.toString()
        )

        val gson = Gson()
        val productDataGson = gson.toJson(addProduct)

        sharedPreferencesProduct.edit().putString(KEY_PRODUCT, productDataGson).apply()
    }

    private fun setErrorForEditText() {
        if (binding.etLink.text.isNullOrEmpty()) {
            binding.etLink.error = getString(R.string.please_fill_in_this_field)

        } else if (binding.etBrand.text.isNullOrEmpty()) {
            binding.etBrand.error = getString(R.string.please_fill_in_this_field)

        } else if (binding.etDescription.text.isNullOrEmpty()) {
            binding.etDescription.error = getString(R.string.please_fill_in_this_field)
        }
    }

    companion object {
        const val KEY_PRODUCT = "product"
    }

}