package com.thejuki.kformmasterexample

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.thejuki.kformmaster.helper.*
import com.thejuki.kformmaster.model.FormHeader
import com.thejuki.kformmasterexample.databinding.ActivityFullscreenFormBinding

/**
 * Fullscreen Form Activity
 *
 * The Form takes up the whole activity screen
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class TestFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFullscreenFormBinding
    private lateinit var formBuilder: FormBuildHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFullscreenFormBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupToolBar()

        setupForm()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_form, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                R.id.action_validate -> {
                    validate()
                    true
                }
                R.id.action_show_hide -> {
                    showHideAll()
                    true
                }
                R.id.action_clear -> {
                    clear()
                    true
                }
                android.R.id.home -> {
                    onBackPressed()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

    private fun clear() {
        formBuilder.clearAll()
    }

    private fun showHideAll() {
        formBuilder.elements.forEach {
            if (it !is FormHeader) {
                it.visible = !it.visible
            }
        }
    }

    private fun validate() {
        val alert = AlertDialog.Builder(this@TestFormActivity).create()

        if (formBuilder.isValidForm) {
            alert.setTitle(this@TestFormActivity.getString(R.string.FormValid))
        } else {
            alert.setTitle(this@TestFormActivity.getString(R.string.FormInvalid))

            formBuilder.elements.forEach {
                if (!it.isValid) {
                    it.error = "This field is required!"
                }
            }
        }

        alert.setButton(AlertDialog.BUTTON_POSITIVE, this@TestFormActivity.getString(android.R.string.ok), { _, _ -> })
        alert.show()
    }

    private fun setupToolBar() {

        val actionBar = supportActionBar

        if (actionBar != null) {
            actionBar.title = getString(R.string.full_screen_form)
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeButtonEnabled(true)
        }
    }

    private fun setupForm() {
        formBuilder = form(binding.recyclerView, formLayouts = FormLayouts(
                number = R.layout.form_element_custom
        )) {
            header { title = "A" }
            number {
                title = "1"
            }
            number {
                title = "2"
            }
            header { title = "B" }
            number {
                title = "1"
            }
            number {
                title = "2"
            }
            header { title = "C" }
            number {
                title = "1"
            }
            number {
                title = "2"
            }
            header { title = "D" }
            number {
                title = "1"
            }
            number {
                title = "2"
            }
            header { title = "E" }
            number {
                title = "1"
            }
            number {
                title = "2"
            }
            header { title = "F" }
            number {
                title = "1"
            }
            number {
                title = "2"
            }

        }
    }
}