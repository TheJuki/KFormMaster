package com.thejuki.kformmaster.model

import android.widget.TextView
import com.thejuki.kformmaster.extensions.IFormInlinePicker
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

open class FormInlineDatePickerElement(tag: Int = -1) : BaseFormElement<LocalDateTime>(tag) {
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

    private var delegate: IFormInlinePicker? = null
    var allDay: Boolean = false
    private var startDate: LocalDate? = null
        set(value) {
            field = value
            delegate?.setStartDate(value)
        }

    var pickerType = PickerType.Primary
    var linkedPicker: FormInlineDatePickerElement? = null
        set(value) {
            field = value
            if (pickerType == PickerType.Secondary)
                value?.linkedPicker = this
        }

    fun setDelegate(delegate: IFormInlinePicker) {
        this.delegate = delegate
    }

    fun isAllDay(): Boolean {
        return allDay
    }

    fun setAllDay(allDay: Boolean, value: LocalDateTime? = null) {
        this.allDay = allDay
        if (value != null)
            delegate?.setDateTime(value)

        delegate?.setAllDayPicker()
    }

    fun setDateTime(dateTime: LocalDateTime) {
        delegate?.setDateTime(dateTime)
    }

    fun toggle() {
        delegate?.toggle()
    }

    fun collapse() {
        delegate?.collapse()
    }

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
