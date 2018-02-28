package com.thejuki.kformmaster

import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.matcher.ViewMatchers
import android.support.v7.widget.AppCompatSeekBar
import android.view.View
import org.hamcrest.Matcher

/**
 * Custom View Actions
 *
 * Custom View Actions for the Great Form Instrumented Test
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
fun setProgress(progress: Int) =
        object : ViewAction {
            override fun getDescription(): String {
                return "Set a progress on a SeekBar"
            }

            override fun getConstraints(): Matcher<View> {
                return ViewMatchers.isAssignableFrom(AppCompatSeekBar::class.java)
            }

            override fun perform(uiController: UiController?, view: View?) {
                val seekBar = view as AppCompatSeekBar
                seekBar.progress = progress
            }
        }