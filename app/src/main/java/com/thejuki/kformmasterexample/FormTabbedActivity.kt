package com.thejuki.kformmasterexample

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.thejuki.kformmasterexample.FormTabbedActivity.Tabs.Form
import com.thejuki.kformmasterexample.FormTabbedActivity.Tabs.values
import com.thejuki.kformmasterexample.databinding.ActivityTabbedFormBinding
import com.thejuki.kformmasterexample.fragment.FormFragment

/**
 * Form Tabbed Activity
 *
 * The form is displayed in tab fragment
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormTabbedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTabbedFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTabbedFormBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar)
        setupToolBar()

        setupTabs()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupToolBar() {

        val actionBar = supportActionBar

        if (actionBar != null) {
            actionBar.title = getString(R.string.tabbed_form)
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeButtonEnabled(true)
        }

    }

    private fun setupTabs() {
        binding.tabs.addTab(binding.tabs.newTab())

        // Set up the ViewPager with the sections adapter.
        binding.container.adapter = SectionsPagerAdapter(this, binding.tabs.tabCount)

        binding.tabs.tabMode = TabLayout.MODE_SCROLLABLE

        TabLayoutMediator(binding.tabs, binding.container) { tab, position ->
            when (values()[position]) {
                Form -> tab.text = getString(R.string.form)
            }
        }.attach()
    }

    private enum class Tabs {
        Form
    }

    /**
     * A [FragmentStateAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fa: FragmentActivity, private val tabCount: Int) : FragmentStateAdapter(fa) {

        override fun createFragment(position: Int): Fragment {
            return when (values()[position]) {
                Form -> FormFragment.newInstance()
            }
        }

        override fun getItemCount(): Int {
            return tabCount
        }
    }
}