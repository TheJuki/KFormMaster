package com.thejuki.kformmaster.model

import com.thejuki.kformmaster.extensions.IFormInlinePicker
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime

enum class PickerType(val type: Int) {
    Primary(1),
    Secondary(2)
}

open class FormInlineDatePickerElement: BaseFormElement<LocalDateTime> {
    private var delegate : IFormInlinePicker? = null
    var allDay : Boolean = false
    private var startDate : LocalDate? = null
        set(value){
            field = value
            delegate?.setStartDate(value)
        }

    var pickerType = PickerType.Primary
    var linkedPicker : FormInlineDatePickerElement? = null
        set(value) {
            field = value
            if (pickerType == PickerType.Secondary)
                value?.linkedPicker = this
        }
    constructor() : super()
    constructor(tag: Int) {
        this.tag = tag
    }

    fun setDelegate(delegate: IFormInlinePicker){
        this.delegate = delegate
    }

    fun isAllDay() : Boolean{
        return allDay
    }

    fun setAllDay(allDay : Boolean, value : LocalDateTime? = null) {
        this.allDay = allDay
        if (value != null)
            delegate?.setDateTime(value)

        delegate?.setAllDayPicker()
    }

    fun setDateTime(dateTime: LocalDateTime){
        delegate?.setDateTime(dateTime)
    }

    fun toggle(){
        delegate?.toggle()
    }

    fun collapse(){
        delegate?.collapse()
    }

    fun expand(){
        delegate?.expand()
    }

}
