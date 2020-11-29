package com.thejuki.kformmasterexample

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.model.BaseFormElement
import com.thejuki.kformmaster.model.FormCheckBoxElement
import com.thejuki.kformmaster.model.FormEmailEditTextElement
import com.thejuki.kformmaster.model.FormPasswordEditTextElement
import com.thejuki.kformmasterexample.LoginFormActivity.Tag.Email
import com.thejuki.kformmasterexample.LoginFormActivity.Tag.Password
import com.thejuki.kformmasterexample.databinding.ActivityLoginFormBinding

/**
 * Login Form Activity
 *
 * Example Login screen using the Form
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class LoginFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginFormBinding
    private lateinit var formBuilder: FormBuildHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginFormBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupToolBar()

        setupForm()

        binding.buttonLogin.setOnClickListener {
            val loginElementValue = formBuilder.getFormElement<FormEmailEditTextElement>(Email.ordinal).value
            val passwordElementValue = formBuilder.getFormElement<FormPasswordEditTextElement>(Password.ordinal).value
            val checkBoxElementValue = formBuilder.getElementAtIndex(2).value as Boolean?
            Toast.makeText(this@LoginFormActivity, "Do whatever you want with this data\n" +
                    "$loginElementValue\n" +
                    "$passwordElementValue\n" +
                    checkBoxElementValue, Toast.LENGTH_SHORT).show()
        }
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
            actionBar.setDisplayShowTitleEnabled(false)
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeButtonEnabled(true)
        }

    }

    private enum class Tag {
        Email,
        Password
    }

    private fun setupForm() {
        formBuilder = FormBuildHelper()
        formBuilder.attachRecyclerView(binding.recyclerView)

        val elements: MutableList<BaseFormElement<*>> = mutableListOf()

        val emailElement = FormEmailEditTextElement(Email.ordinal).apply {
            title = getString(R.string.email)
        }

        elements.add(emailElement)

        val passwordElement = FormPasswordEditTextElement(Password.ordinal).apply {
            title = getString(R.string.password)
        }

        elements.add(passwordElement)

        val checkBoxElement = FormCheckBoxElement<Boolean>().apply {
            title = getString(R.string.remember)
            value = false
            checkedValue = true
            unCheckedValue = false
        }

        elements.add(checkBoxElement)

        formBuilder.addFormElements(elements)

    }
}