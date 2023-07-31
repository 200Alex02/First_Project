package com.example.first_project.ui.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.first_project.R
import com.example.first_project.databinding.FragmentSettingsBinding
import com.example.first_project.ui.BaseFragment
import java.util.Locale

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(
    FragmentSettingsBinding::inflate
) {

    private lateinit var sharedPreferences: SharedPreferences
    @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nightThemeSwitch.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.nightTextView.text = getString(R.string.night_theme)

            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.nightTextView.text = getString(R.string.light_theme)
            }
        }

        binding.btnLanguage.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                showPopupMenu(binding.btnLanguage)
            }
            true
        }

        binding.fullScreenSwitch.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked) {
                hideSystemUI()

            } else {
                showSystemUI()

            }

        }
    }

    private fun showPopupMenu(view: View) {
        val popup = PopupMenu(requireContext(), view)
        popup.inflate(R.menu.popup_menu)

        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.english_item -> {
                    change("en")
                    true
                }

                R.id.russian_item -> {
                    change("ru")
                    true
                }

                else -> false
            }
        }
        popup.show()
    }

    fun change(language: String) {
        val config = resources.configuration
        val locale = Locale(language)
        Locale.setDefault(locale)
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
        activity?.recreate()
    }

    private fun hideSystemUI() {
        activity?.window?.decorView?.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }

    private fun showSystemUI() {
        activity?.window?.decorView?.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION and View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
    }

}