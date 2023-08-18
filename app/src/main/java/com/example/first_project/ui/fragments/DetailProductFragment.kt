package com.example.first_project.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.first_project.R
import com.example.first_project.databinding.FragmentDetailProductBinding
import com.example.first_project.ui.basefragment.BaseFragment
import com.example.first_project.ui.database.ProductEntity
import com.example.first_project.ui.database.ProductMapper
import com.example.first_project.ui.viewmodels.ProductViewModel

class DetailProductFragment : BaseFragment<FragmentDetailProductBinding>(
    FragmentDetailProductBinding::inflate
) {
    private lateinit var viewModel: ProductViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ProductViewModel::class.java]

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

        binding.favouriteIcon.setOnClickListener {
            viewModel.addProduct(
                ProductEntity(
                    0, product.brand, product.description,
                    product.picture, product.cost, product.likeElement
                )
            )
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_detailProductFragment2_to_item_favourite)
        }
    }

}