package com.thejuki.kformmaster

import android.view.View
import android.widget.RadioButton
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