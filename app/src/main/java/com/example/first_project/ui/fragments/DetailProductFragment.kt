package com.example.first_project.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.first_project.R
import com.example.first_project.databinding.FragmentDetailProductBinding
import com.example.first_project.ui.basefragment.BaseFragment

class DetailProductFragment : BaseFragment<FragmentDetailProductBinding>(
    FragmentDetailProductBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: DetailProductFragmentArgs by navArgs()
        val product = args.MyArg

        binding.brand.text = product.brand
        binding.description.text = product.description
        binding.cost.text = product.cost
        Glide.with(requireContext())
            .load(product.picture)
            .centerCrop()
            .error(R.drawable.error)
            .placeholder(R.drawable.error)
            .into(binding.picture)
    }

}