package com.thejuki.kformmaster.model

import android.os.Parcel
import android.os.Parcelable
import android.support.v7.widget.AppCompatAutoCompleteTextView
import android.widget.ArrayAdapter
import java.io.Serializable

/**
 * Form AutoComplete Element
 *
 * Form element for AppCompatAutoCompleteTextView
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormAutoCompleteElement<T : Serializable> : BaseFormElement<T> {

    override fun clear() {
        this.value = null
        (this.editView as? AppCompatAutoCompleteTextView)?.setText("")
    }

    constructor() : super()

    constructor(tag: Int) : super(tag)

    /**
     * Because the written text in the EditText not always matched the
     * Values in options array, we keep the TypedString: what the user typed.
     */
    var typedString: String? = null

    /**
     * Override the default array adapter
     * This is useful for a custom asynchronous adapter
     */
    var arrayAdapter: ArrayAdapter<*>? = null

    /**
     * Override the default dropdown width
     */
    var dropdownWidth: Int? = null

    /**
     * arrayAdapter builder setter
     */
    fun setArrayAdapter(arrayAdapter: ArrayAdapter<*>?): FormAutoCompleteElement<T> {
        this.arrayAdapter = arrayAdapter
        return this
    }

    /**
     * dropdownWidth builder setter
     */
    fun setDropdownWidth(dropdownWidth: Int?): FormAutoCompleteElement<T> {
        this.dropdownWidth = dropdownWidth
        return this
    }

    /**
     * Sets the value and typedString
     */
    override fun setValue(mValue: Any?): BaseFormElement<T> {
        typedString = mValue?.toString()

        return super.setValue(mValue)
    }

    /**
     * Parcelable boilerplate
     */
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        super.writeToParcel(dest, flags)
        dest.writeString(this.typedString)
    }

    constructor(`in`: Parcel) : super(`in`) {
        this.typedString = `in`.readString()
    }

    companion object {

        /**
         * Creates an instance
         */
        fun createInstance(): FormAutoCompleteElement<String> {
            return FormAutoCompleteElement()
        }

        /**
         * Creates a generic instance
         */
        fun <T : Serializable> createGenericInstance(): FormAutoCompleteElement<T> {
            return FormAutoCompleteElement()
        }

        val CREATOR: Parcelable.Creator<FormAutoCompleteElement<*>> = object : Parcelable.Creator<FormAutoCompleteElement<*>> {
            override fun createFromParcel(source: Parcel): FormAutoCompleteElement<*> {
                return FormAutoCompleteElement<Serializable>(source)
            }

            override fun newArray(size: Int): Array<FormAutoCompleteElement<*>?> {
                return arrayOfNulls(size)
            }
        }
    }
}
