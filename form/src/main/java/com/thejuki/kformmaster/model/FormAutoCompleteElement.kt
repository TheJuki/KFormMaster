package com.thejuki.kformmaster.model

import android.os.Parcel
import android.os.Parcelable
import android.widget.ArrayAdapter
import java.io.Serializable
import java.util.*

/**
 * Form AutoComplete Element
 *
 * Form element for AppCompatAutoCompleteTextView
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormAutoCompleteElement<T : Serializable> : BaseFormElement<T> {

    constructor(tag: Int = -1) : super(tag)

    /**
     * Because the written text in the EditText not always matched the
     * Values in options array, we keep the TypedString: what the user typed.
     */
    var typedString: String? = null
    /**
     * To determine what strings can be typed, we keep a set of the options
     * converted to strings
     */
    private var mStringOptions: HashSet<String>? = null

    /**
     * Override the default array adapter
     * This is useful for a custom asynchronous adapter
     */
    var arrayAdapter: ArrayAdapter<*>? = null

    /**
     * Override the default dropdown width
     */
    var dropdownWidth: Int? = null

    val stringOptions: Set<String>
        get() = mStringOptions ?: HashSet<String>()

    fun setArrayAdapter(arrayAdapter: ArrayAdapter<*>?): FormAutoCompleteElement<T> {
        this.arrayAdapter = arrayAdapter
        return this
    }

    fun setDropdownWidth(dropdownWidth: Int?): FormAutoCompleteElement<T> {
        this.dropdownWidth = dropdownWidth
        return this
    }

    override fun setValue(mValue: Any?): BaseFormElement<T> {
        typedString = mValue?.toString()

        return super.setValue(mValue)
    }

    override fun setOptions(mOptions: List<T>): FormAutoCompleteElement<T> {
        super.setOptions(mOptions)
        mStringOptions = HashSet()
        for (i in mOptions) {
            mStringOptions!!.add(i.toString())
        }
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
        dest.writeString(this.typedString)
        dest.writeSerializable(this.mStringOptions)
    }

    protected constructor(`in`: Parcel) : super(`in`) {
        this.typedString = `in`.readString()
        this.mStringOptions = `in`.readSerializable() as HashSet<String>
    }

    companion object {

        fun createInstance(): FormAutoCompleteElement<String> {
            return FormAutoCompleteElement()
        }

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
