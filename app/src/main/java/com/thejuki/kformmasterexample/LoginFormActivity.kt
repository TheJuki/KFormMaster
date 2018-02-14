package com.thejuki.kformmasterexample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.listener.OnFormElementValueChangedListener
import com.thejuki.kformmaster.model.BaseFormElement
import com.thejuki.kformmaster.model.FormEditTextElement
import com.thejuki.kformmasterexample.LoginFormActivity.Tag.Email
import com.thejuki.kformmasterexample.LoginFormActivity.Tag.Password
import kotlinx.android.synthetic.main.activity_login_form.*

/**
 * Login Form Activity
 *
 * Example Login screen using the Form
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class LoginFormActivity : AppCompatActivity() {

    private var mFormBuilder: FormBuildHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_form)

        setupToolBar()

        setupForm()

        buttonLogin.setOnClickListener {
            val loginElement = mFormBuilder!!.getFormElement(Email.ordinal)!!.mValue as String
            val passwordElement = mFormBuilder!!.getFormElement(Password.ordinal)!!.mValue as String
            Toast.makeText(this@LoginFormActivity, "Do whatever you want with this data\n" + loginElement + "\n" + passwordElement, Toast.LENGTH_SHORT).show()
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
        mFormBuilder = FormBuildHelper(this, object : OnFormElementValueChangedListener {
            override fun onValueChanged(formElement: BaseFormElement<*>) {

            }
        })
        mFormBuilder!!.attachRecyclerView(this, recyclerView)

        val elements: MutableList<BaseFormElement<*>> = mutableListOf()

        val emailElement = FormEditTextElement<String>(Email.ordinal)
        emailElement.mType = BaseFormElement.TYPE_EDITTEXT_EMAIL
        emailElement.mTitle = getString(R.string.email)
        emailElement.mValue = ""
        elements.add(emailElement)

        val passwordElement = FormEditTextElement<String>(Password.ordinal)
        passwordElement.mType = BaseFormElement.TYPE_EDITTEXT_PASSWORD
        passwordElement.mTitle = getString(R.string.password)
        elements.add(passwordElement)

        mFormBuilder!!.addFormElements(elements)
        mFormBuilder!!.refreshView()

    }
}
