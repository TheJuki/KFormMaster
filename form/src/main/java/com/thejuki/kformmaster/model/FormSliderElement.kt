package com.thejuki.kformmaster.model

import android.os.Parcel

/**
 * Form Slider Element
 *
 * Form element for SeekBar
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
open class FormSliderElement : BaseFormElement<Int> {

    var max: Int = 100
        get() {
            return if (field <= 0) 100 else field
        }
    var min: Int = 0
        get() {
            return if (field <= 0) 0 else field
        }
    var steps: Int = 1
        get() {
            return if (field <= 0) 1 else field
        }

    fun setMax(max: Int): FormSliderElement
    {
        this.max = max
        return this
    }

    fun setMin(min: Int): FormSliderElement
    {
        this.min = min
        return this
    }

    fun setSteps(steps: Int): FormSliderElement
    {
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

    constructor(tag: Int = -1) : super(tag)

    protected constructor(`in`: Parcel) : super(`in`) {}

    companion object {
        fun createInstance(): FormSliderElement {
            return FormSliderElement()
        }

        fun createGenericInstance(): FormSliderElement {
            return FormSliderElement()
        }
    }
}