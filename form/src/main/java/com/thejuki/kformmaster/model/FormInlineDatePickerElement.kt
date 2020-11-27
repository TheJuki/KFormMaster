package com.thejuki.kformmaster.model

import android.widget.TextView
import com.thejuki.kformmaster.extensions.IFormInlinePicker
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

/**
 * Form Inline Date Picker Element
 *
 * @author **soareseneves** ([GitHub](https://github.com/soareseneves))
 * @version 1.0
 */
open class FormInlineDatePickerElement(tag: Int = -1) : BaseFormElement<LocalDateTime>(tag) {
    /**
     * Picker Type Enum
     */
    enum class PickerType(val type: Int) {
        Primary(1),
        Secondary(2)
    }

    /**
     * Date Time Format for displaying on the form
     */
    var dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
        set(value) {
            field = value
            displayNewValue()
        }

    /**
     * Date Time Format for displaying on the picker with the date and time (allDay = false)
     */
    var dateTimePickerFormatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE

    private var delegate: IFormInlinePicker? = null

    /**
     * All Day
     *
     * By default, this is false which shows the time picker.
     * Set to true to hide the time picker.
     */
    var allDay: Boolean = false

    /**
     * Start Date for the picker if value is null.
     */
    var startDate: LocalDate? = null
        set(value) {
            field = value
            delegate?.setStartDate(value)
        }

    /**
     * Picker Type
     *
     * By default, this is PickerType.Primary.
     * Set to PickerType.Secondary for the second Linked Picker.
     */
    var pickerType = PickerType.Primary

    /**
     * Linked Picker
     *
     * Links two FormInlineDatePickerElements together.
     */
    var linkedPicker: FormInlineDatePickerElement? = null
        set(value) {
            field = value
            if (pickerType == PickerType.Secondary)
                value?.linkedPicker = this
        }

    /**
     * Date Error
     *
     * By default, this is false.
     * The secondary linked date picker will set this to true when the date is
     * before the primary linked picker.
     */
    var dateError: Boolean = false

    /**
     * Set Delegate
     */
    fun setDelegate(delegate: IFormInlinePicker) {
        this.delegate = delegate
    }

    /**
     * Is All Day
     */
    fun isAllDay(): Boolean {
        return allDay
    }

    /**
     * Set All Day with a DateTime
     */
    fun setAllDay(allDay: Boolean, value: LocalDateTime? = null) {
        this.allDay = allDay
        if (value != null)
            delegate?.setDateTime(value)

        delegate?.setAllDayPicker()
    }

    /**
     * Set Date
     */
    fun setDateTime(dateTime: LocalDateTime) {
        delegate?.setDateTime(dateTime)
    }

    /**
     * Toggle the display of the picker
     */
    fun toggle() {
        delegate?.toggle()
    }

    /**
     * Return the expanded state of the picker
     */
    fun isExpanded(): Boolean {
        return delegate?.isExpanded() == true
    }

    /**
     * Hide the picker
     */
    fun collapse() {
        delegate?.collapse()
    }

    /**
     * Display the picker
     */
    fun expand() {
        delegate?.expand()
    }

    override fun displayNewValue() {
        editView?.let {
            if (it is TextView) {
                it.text = value?.format(dateTimeFormatter)
            }
        }
    }
}
