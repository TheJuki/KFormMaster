package com.thejuki.kformmasterexample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.threetenabp.AndroidThreeTen
import com.thejuki.kformmasterexample.databinding.ActivityMainBinding

/**
 * Main Activity
 *
 * Examples screen
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Init AndroidThreeTen
        AndroidThreeTen.init(applicationContext)

        binding.buttonFullScreenActivity.setOnClickListener { startActivity(Intent(this@MainActivity, FullscreenFormActivity::class.java)) }

        binding.buttonPartialScreenActivity.setOnClickListener { startActivity(Intent(this@MainActivity, PartialScreenFormActivity::class.java)) }

        binding.buttonFormListenerActivity.setOnClickListener { startActivity(Intent(this@MainActivity, FormListenerActivity::class.java)) }

        binding.buttonFormListenerJavaActivity.setOnClickListener { startActivity(Intent(this@MainActivity, FormListenerJavaActivity::class.java)) }

        binding.buttonTabbedFormActivity.setOnClickListener { startActivity(Intent(this@MainActivity, FormTabbedActivity::class.java)) }

        binding.buttonLoginActivity.setOnClickListener { startActivity(Intent(this@MainActivity, LoginFormActivity::class.java)) }

        binding.buttonCustomFormActivity.setOnClickListener { startActivity(Intent(this@MainActivity, CustomFormActivity::class.java)) }
    }
}
