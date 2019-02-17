package com.thejuki.kformmaster

import android.view.View
import android.widget.ProgressBar
import android.widget.RadioButton
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import com.thejuki.kformmaster.widget.SegmentedGroup
import org.hamcrest.Matcher

/**
 * Custom View Actions
 *
 * Custom View Actions for the Great Form Instrumented Test
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
fun setSeekBarProgress(progress: Int) =
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

fun setProgressBarProgress(progress: Int) =
        object : ViewAction {
            override fun getDescription(): String {
                return "Set a progress on a ProgressBar"
            }

            override fun getConstraints(): Matcher<View> {
                return ViewMatchers.isAssignableFrom(ProgressBar::class.java)
            }

            override fun perform(uiController: UiController?, view: View?) {
                val progressBar = view as ProgressBar
                progressBar.progress = progress
            }
        }

fun setRadioButtonCheck(index: Int) =
        object : ViewAction {
            override fun getDescription(): String {
                return "Set a radio button check"
            }

            override fun getConstraints(): Matcher<View> {
                return ViewMatchers.isAssignableFrom(SegmentedGroup::class.java)
            }

            override fun perform(uiController: UiController?, view: View?) {
                val radioGroup = view as SegmentedGroup
                val radioButton = radioGroup.getChildAt(index) as RadioButton
                radioGroup.clearCheck()
                radioButton.isChecked = true
            }
        }