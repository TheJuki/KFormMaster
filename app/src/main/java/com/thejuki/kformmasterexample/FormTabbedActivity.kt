package com.thejuki.kformmasterexample

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmasterexample.FormTabbedActivity.Tabs.Form
import com.thejuki.kformmasterexample.FormTabbedActivity.Tabs.values
import com.thejuki.kformmasterexample.fragment.FormFragment
import kotlinx.android.synthetic.main.activity_tabbed_form.*

/**
 * Form Tabbed Activity
 *
 * The form is displayed in tab fragment
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormTabbedActivity : AppCompatActivity() {

    private lateinit var formBuilder: FormBuildHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabbed_form)

        setSupportActionBar(toolbar)
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
        tabs.addTab(tabs.newTab().setText(getString(R.string.form)))

        // Set up the ViewPager with the sections adapter.
        container.adapter = SectionsPagerAdapter(supportFragmentManager, tabs.tabCount)

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))
        tabs.tabMode = TabLayout.MODE_SCROLLABLE
    }

    private enum class Tabs {
        Form
    }

    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager, private val tabCount: Int) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return when (values()[position]) {
                Form -> FormFragment.newInstance()
            }
        }

        override fun getCount(): Int {
            return tabCount
        }
    }
}