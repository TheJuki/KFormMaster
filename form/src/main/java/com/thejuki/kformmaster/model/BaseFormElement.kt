package com.thejuki.kformmaster.model

import android.os.Parcel
import android.os.Parcelable
import android.support.v7.widget.*
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
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
open class BaseFormElement<T : Serializable>(var tag: Int = -1) : ViewModel, Parcelable {

    /**
     * Form Element Title
     */
    var title: String? = null
        set(value) {
            field = value
            titleView?.let {
                it.text = value
            }
        }

    /**
     * Form Element Unique ID
     */
    var id: Int = 0

    /**
     * Form Element Value Observers
     */
    val valueObservers = mutableListOf<(value: T?, element: BaseFormElement<T>) -> Unit>()

    /**
     * Form Element Value
     */
    var value: T? by Delegates.observable<T?>(null) { _, _, newValue ->
        valueObservers.forEach { it(newValue, this) }
        editView?.let {
            if (it is AppCompatEditText && it.text.toString() != value as? String) {
                it.setText(value as? String)

            } else if (it is TextView && value is String &&
                    it.text.toString() != value as? String &&
                    it !is SwitchCompat && it !is AppCompatCheckBox) {
                it.text = value as? String
            }
        }
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
        set(value) {
            field = value
            editView?.let {
                if (it is TextView) {
                    it.hint = hint
                }
            }
        }

    /**
     * Form Element Max Lines
     */
    var maxLines: Int = 1
        set(value) {
            field = value
            editView?.let {
                if (it is TextView && it !is AppCompatCheckBox && it !is AppCompatButton && it !is SwitchCompat) {
                    it.setSingleLine(maxLines == 1)
                    it.maxLines = maxLines
                }
            }
        }

    /**
     * Form Element RTL
     */
    var rightToLeft: Boolean = true
        set(value) {
            field = value
            editView?.let {
                if (it is TextView && it !is AppCompatCheckBox && it !is AppCompatButton && it !is SwitchCompat) {
                    it.gravity = if (rightToLeft) Gravity.END else Gravity.START
                }
            }
        }

    /**
     * Form Element Error
     */
    var error: String? = null
        set(value) {
            field = value
            if (value != null) {
                errorView?.let {
                    it.visibility = View.VISIBLE
                    it.text = value
                }

            } else {
                errorView?.let {
                    it.visibility = View.GONE
                    it.text = null
                }
            }
        }

    /**
     * Form Element Required
     */
    var required: Boolean = false

    /**
     * Form Element Item View
     */
    var itemView: View? = null
        set(value) {
            field = value
            itemView?.let {
                it.isEnabled = enabled
            }
        }

    /**
     * Form Element Edit View
     */
    var editView: View? = null
        set(value) {
            field = value
            editView?.let {
                it.isEnabled = enabled
                if (it is TextView && it !is AppCompatCheckBox && it !is AppCompatButton && it !is SwitchCompat) {
                    it.gravity = if (rightToLeft) Gravity.END else Gravity.START
                    it.setSingleLine(maxLines == 1)
                    it.maxLines = maxLines
                }
            }
        }

    /**
     * Form Element Title View
     */
    var titleView: AppCompatTextView? = null
        set(value) {
            field = value
            titleView?.let {
                it.isEnabled = enabled
            }
        }

    /**
     * Form Element Error View
     */
    var errorView: AppCompatTextView? = null

    /**
     * Form Element Visibility
     */
    var visible: Boolean = true
        set(value) {
            field = value
            if (value) {
                itemView?.let {
                    it.visibility = View.VISIBLE
                    val params = it.layoutParams
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT
                    it.layoutParams = params
                }

            } else {
                itemView?.let {
                    it.visibility = View.GONE
                    val params = it.layoutParams
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT
                    params.height = 0
                    it.layoutParams = params
                }
            }
        }

    /**
     * Form Element Enabled
     */
    var enabled: Boolean = true
        set(value) {
            field = value
            itemView?.let {
                it.isEnabled = value
            }
            titleView?.let {
                it.isEnabled = value
            }
            editView?.let {
                it.isEnabled = value
            }
        }

    /**
     * Form Element Value String value
     */
    val valueAsString: String
        get() = this.value?.toString() ?: ""

    /**
     * Base validation
     */
    open val isValid: Boolean
        get() = !required || (required && value != null &&
                (value !is String || !(value as? String).isNullOrEmpty()))

    /**
     * Clear edit view
     */
    open fun clear() {
        this.value = null
    }

    /**
     * Tag builder setter
     */
    fun setTag(mTag: Int): BaseFormElement<T> {
        this.tag = mTag
        return this
    }

    /**
     * Title builder setter
     */
    fun setTitle(mTitle: String): BaseFormElement<T> {
        this.title = mTitle
        return this
    }

    /**
     * Value builder setter
     */
    @Suppress("UNCHECKED_CAST")
    open fun setValue(value: Any?): BaseFormElement<T> {
        this.value = value as T?
        return this
    }

    /**
     * Hint builder setter
     */
    fun setHint(hint: String?): BaseFormElement<T> {
        this.hint = hint
        return this
    }

    /**
     * RTL builder setter
     */
    fun setRightToLeft(rightToLeft: Boolean): BaseFormElement<T> {
        this.rightToLeft = rightToLeft
        return this
    }

    /**
     * Max Lines builder setter
     */
    fun setMaxLines(maxLines: Int): BaseFormElement<T> {
        this.maxLines = maxLines
        return this
    }

    /**
     * Error builder setter
     */
    fun setError(error: String?): BaseFormElement<T> {
        this.error = error
        return this
    }

    /**
     * Required builder setter
     */
    fun setRequired(required: Boolean): BaseFormElement<T> {
        this.required = required
        return this
    }

    /**
     * Visible builder setter
     */
    fun setVisible(visible: Boolean): BaseFormElement<T> {
        this.visible = visible
        return this
    }

    /**
     * Enabled builder setter
     */
    fun setEnabled(enabled: Boolean): BaseFormElement<T> {
        this.enabled = enabled
        return this
    }

    /**
     * Options builder setter
     */
    open fun setOptions(mOptions: List<T>): BaseFormElement<T> {
        this.options = mOptions
        return this
    }

    /**
     * Adds a value observer
     */
    fun addValueObserver(observer: (T?, BaseFormElement<T>) -> Unit): BaseFormElement<T> {
        this.valueObservers.add(observer)
        return this
    }

    /**
     * Adds a list of value observers
     */
    fun addAllValueObservers(observers: List<(T?, BaseFormElement<T>) -> Unit>): BaseFormElement<T> {
        this.valueObservers.addAll(observers)
        return this
    }

    /**
     * Options Selected builder setter
     */
    @Suppress("UNCHECKED_CAST")
    fun setOptionsSelected(optionsSelected: List<Any>): BaseFormElement<T> {
        this.optionsSelected = optionsSelected as List<T>
        return this
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
        if (options == null || options?.isEmpty() == true) {
            dest.writeInt(0)
        } else {
            options?.let {
                dest.writeInt(it.size)

                val objectsType = it[0].javaClass
                dest.writeSerializable(objectsType)
                dest.writeList(it)
            }
        }

        // optionsSelected
        if (optionsSelected == null || optionsSelected?.isEmpty() == true) {
            dest.writeInt(0)
        } else {
            optionsSelected?.let {
                dest.writeInt(it.size)

                val objectsType = it[0].javaClass
                dest.writeSerializable(objectsType)
                dest.writeList(it)
            }
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
        return "FormElement(tag=$tag, title=$title, id=$id, value=$value, hint=$hint, error=$error, required=$required, isValid=$isValid, visible=$visible)"
    }

    constructor(`in`: Parcel) : this() {
        this.tag = `in`.readInt()
        this.title = `in`.readString()
        @Suppress("UNCHECKED_CAST")
        this.value = `in`.readSerializable() as T?

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
         * Creates an instance
         */
        fun createInstance(): BaseFormElement<String> {
            return BaseFormElement()
        }

        /**
         * Creates a generic instance
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
