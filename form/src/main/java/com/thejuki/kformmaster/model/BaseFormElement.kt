package com.thejuki.kformmaster.model

import android.os.Parcel
import android.os.Parcelable
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import com.thejuki.kformmaster.listener.OnFormElementValueChangedListener
import java.io.Serializable
import java.util.*
import kotlin.properties.Delegates

/**
 * Base Form Element
 *
 * Holds the class variables used by most form elements
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
open class BaseFormElement<T : Serializable>(var tag: Int = -1, var title: String? = null) : ViewModel, Parcelable {

    /**
     * Form Element Unique ID
     */
    internal var id: Int = 0

    /**
     * Form Element Value Observers
     */
    val valueObservers = mutableListOf<(T?) -> Unit>()

    /**
     * Form Element Value
     */
    open var value: T? by Delegates.observable<T?>(null) { prop, old, new ->
        valueObservers.forEach { it(new) }
    }

    /**
     * Form Element Options
     */
    var options: List<T>? = null
        get() = field ?: ArrayList()

    /**
     * Form Element Options Selected
     * NOTE: When using MultiCheckBox, this is the Form Element Value
     */
    var optionsSelected: List<T>? = null
        get() = field ?: ArrayList()

    /**
     * Form Element Hint
     */
    var hint: String? = null

    /**
     * Form Element Error
     */
    var error: String? = null

    /**
     * Form Element Required
     */
    var required: Boolean = false

    /**
     * Form Element Visibility
     */
    var visible: Boolean = true

    /**
     * Form Element Value Changed Listener
     */
    @Deprecated(
            message = "As of v2.0.0, valueObservers has been added and should be used instead.",
            replaceWith = ReplaceWith("valueObservers.add({ println(it) })")
    )
    var valueChanged: OnFormElementValueChangedListener? = null

    /**
     * Form Element Value String value
     */
    val valueAsString: String
        get() = if (this.value == null) "" else this.value!!.toString()

    /**
     * Form Element Value Observers
     */
    open val isHeader: Boolean
        get() = false

    fun setTag(mTag: Int): BaseFormElement<T> {
        this.tag = mTag
        return this
    }

    fun setTitle(mTitle: String): BaseFormElement<T> {
        this.title = mTitle
        return this
    }

    open fun setValue(mValue: Any?): BaseFormElement<T> {
        this.value = mValue as T?
        valueChanged?.onValueChanged(this)
        return this
    }

    fun setHint(mHint: String?): BaseFormElement<T> {
        this.hint = mHint
        return this
    }

    fun setError(error: String?): BaseFormElement<T> {
        this.error = error
        return this
    }

    fun setRequired(required: Boolean): BaseFormElement<T> {
        this.required = required
        return this
    }

    fun setVisible(visible: Boolean): BaseFormElement<T> {
        this.visible = visible
        return this
    }

    open fun setOptions(mOptions: List<T>): BaseFormElement<T> {
        this.options = mOptions
        return this
    }

    fun setValueChanged(mValueChanged: OnFormElementValueChangedListener?): BaseFormElement<T> {
        this.valueChanged = mValueChanged
        return this
    }

    fun addValueObserver(observer: (T?) -> Unit): BaseFormElement<T> {
        this.valueObservers.add(observer)
        return this
    }

    fun addAllValueObservers(observers: List<(T?) -> Unit>): BaseFormElement<T> {
        this.valueObservers.addAll(observers)
        return this
    }

    fun setOptionsSelected(mOptionsSelected: List<Any>): BaseFormElement<T> {
        this.optionsSelected = mOptionsSelected as List<T>
        return this
    }

    fun isRequired(): Boolean {
        return this.required
    }

    fun isVisible(): Boolean {
        return this.visible
    }

    /**
     * Parcelable boilerplate
     */
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(this.tag)
        dest.writeString(this.title)
        dest.writeSerializable(this.value)

        /*
         * We need special method to store array of generic objects
         * more here: https://stackoverflow.com/a/31979348/3625638
         */

        // options
        if (options == null || options!!.size == 0) {
            dest.writeInt(0)
        } else {
            dest.writeInt(options!!.size)

            val objectsType = options!![0].javaClass
            dest.writeSerializable(objectsType)
            dest.writeList(options)
        }

        // optionsSelected
        if (optionsSelected == null || optionsSelected!!.size == 0) {
            dest.writeInt(0)
        } else {
            dest.writeInt(optionsSelected!!.size)

            val objectsType = optionsSelected!![0].javaClass
            dest.writeSerializable(objectsType)
            dest.writeList(optionsSelected)
        }

        dest.writeString(this.hint)
        dest.writeString(this.error)
        dest.writeByte(if (this.required) 1.toByte() else 0.toByte())
        dest.writeByte(if (this.visible) 1.toByte() else 0.toByte())
    }

    override fun hashCode(): Int {
        return id
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BaseFormElement<*>) return false

        if (id != other.id) return false

        return true
    }

    override fun toString(): String {
        return "FormElement(tag=$tag, title=$title, id=$id, value=$value, hint=$hint, error=$error, required=$required, visible=$visible)"
    }

    protected constructor(`in`: Parcel) : this() {
        this.tag = `in`.readInt()
        this.title = `in`.readString()
        this.value = `in`.readSerializable() as T

        /*
         * We need special method to store array of generic objects
         * more here: https://stackoverflow.com/a/31979348/3625638
         */

        // options
        val optionSize = `in`.readInt()
        if (optionSize == 0) {
            options = null
        } else {
            val type = `in`.readSerializable() as Class<*>

            options = ArrayList(optionSize)
            `in`.readList(options, type.classLoader)
        }

        // optionsSelected
        val selectedOptionSize = `in`.readInt()
        if (selectedOptionSize == 0) {
            optionsSelected = null
        } else {
            val type = `in`.readSerializable() as Class<*>

            optionsSelected = ArrayList(selectedOptionSize)
            `in`.readList(optionsSelected, type.classLoader)
        }

        this.hint = `in`.readString()
        this.error = `in`.readString()
        this.required = `in`.readByte().toInt() != 0
        this.visible = `in`.readByte().toInt() != 0
    }

    companion object {

        /**
         * static method to create instance
         *
         * @return
         */
        fun createInstance(): BaseFormElement<String> {
            return BaseFormElement()
        }

        /**
         * static method to create instance using
         * custom generic value type
         *
         * @return
         */
        fun <T : Serializable> createGenericInstance(): BaseFormElement<T> {
            return BaseFormElement()
        }

        val CREATOR: Parcelable.Creator<BaseFormElement<*>> = object : Parcelable.Creator<BaseFormElement<*>> {
            override fun createFromParcel(source: Parcel): BaseFormElement<*> {
                return BaseFormElement<Serializable>(source)
            }

            override fun newArray(size: Int): Array<BaseFormElement<*>?> {
                return arrayOfNulls(size)
            }
        }
    }
}
