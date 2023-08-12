package com.example.first_project.ui.dialogfragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.first_project.ui.database.ProductViewModel
import java.lang.IllegalStateException

class ClearFavoritesDialogFragment : DialogFragment() {

    private lateinit var productViewModel: ProductViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setTitle("Clear Favourites")
                setMessage("Are you sure you want to clear all favourites?")
                setPositiveButton("Clear") { _, _ ->
                    productViewModel.deleteAllProducts()
                }
                setNegativeButton("Cancel") { _, _ ->
                    dismiss()
                }
            }
            builder.create()
        } ?: throw IllegalStateException("Activity can not be null")
    }
}