package com.thejuki.kformmaster.model

import android.os.Parcel
import android.support.v7.widget.AppCompatSeekBar

/**
 * Form Slider Element
 *
 * Form element for AppCompatSeekBar
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
open class FormSliderElement : BaseFormElement<Int> {

    override fun clear() {
        this.value = min
        (this.editView as? AppCompatSeekBar)?.progress = min
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
     * Steps of the slider
     * Ex. 20 steps with max of 100 would step 0, 5, 10, 15, 20, 25,... to 100
     */
    var steps: Int = 1
        get() {
            return if (field <= 0) 1 else field
        }

    /**
     * Max builder setter
     */
    fun setMax(max: Int): FormSliderElement {
        this.max = max
        return this
    }

    /**
     * Min builder setter
     */
    fun setMin(min: Int): FormSliderElement {
        this.min = min
        return this
    }

    /**
     * Steps builder setter
     */
    fun setSteps(steps: Int): FormSliderElement {
        this.steps = steps
        return this
    }

    /**
     * Parcelable boilerplate
     */
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        super.writeToParcel(dest, flags)
    }

    constructor() : super()

    constructor(tag: Int) : super(tag)

    constructor(`in`: Parcel) : super(`in`) {}

    companion object {
        /**
         * Creates an instance
         */
        fun createInstance(): FormSliderElement {
            return FormSliderElement()
        }

        /**
         * Creates a generic instance
         */
        fun createGenericInstance(): FormSliderElement {
            return FormSliderElement()
        }
    }
}