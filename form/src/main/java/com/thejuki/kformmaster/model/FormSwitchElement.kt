package com.thejuki.kformmaster.model

import android.os.Parcel
import java.io.Serializable

/**
 * Form EditText Element
 *
 * Form element for AppCompatEditText
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
open class FormSwitchElement<T : Serializable> : BaseFormElement<T> {

    override fun getType(): Int {
        return BaseFormElement.TYPE_SWITCH
    }

    var mOnValue: T? = null
    var mOffValue: T? = null

    fun isOn(): Boolean {
        if (mOnValue == null || mValue == null)
            return false
        return mOnValue!! == mValue!!
    }

    fun setOnValue(mOnValue: T?): FormSwitchElement<T>
    {
        this.mOnValue = mOnValue
        return this
    }

    fun setOffValue(mOffValue: T?): FormSwitchElement<T>
    {
        this.mOffValue = mOffValue
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

    constructor(tag: Int = 0) : super(tag)

    protected constructor(`in`: Parcel) : super(`in`) {}

    companion object {
        fun createInstance(): FormSwitchElement<String> {
            return FormSwitchElement()
        }

        fun <T : Serializable> createGenericInstance(): FormSwitchElement<T> {
            return FormSwitchElement()
        }
    }
}