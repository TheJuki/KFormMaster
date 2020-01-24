package com.thejuki.kformmaster.model

import androidx.appcompat.widget.AppCompatSeekBar

/**
 * Form Slider Element
 *
 * Form element for AppCompatSeekBar
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
open class FormSliderElement(tag: Int = -1) : BaseFormElement<Int>(tag) {

    override fun clear() {
        this.value = min
    }

    /**
     * Maximum progress of the slider
     */
    var max: Int = 100
        get() {
            return if (field <= 0) 100 else field
        }

    /**
     * Minimum progress of the slider
     */
    var min: Int = 0
        get() {
            return if (field <= 0) 0 else field
        }

    /**
     * Increments of the slider
     * NOTE: steps must be null. Use steps or incrementBy, not both.
     * Ex. An increment by of 5 with max of 100 would increment by 5's: 0, 5, 10, 15, 20, 25,... to 100
     */
    var incrementBy: Int? = null
        get() {
            return if (field != null && field ?: 0 <= 0) 1 else field
        }

    /**
     * Steps of the slider
     * NOTE: incrementBy must be null. Use steps or incrementBy, not both.
     * Ex. 20 steps with max of 100 would step 0, 5, 10, 15, 20, 25,... to 100
     */
    var steps: Int? = null
        get() {
            return if (field != null && field ?: 0 <= 0) 1 else field
        }

    override fun displayNewValue() {
        editView?.let {
            if (it is AppCompatSeekBar) {
                it.progress = value ?: this.min
            }
        }
    }
}