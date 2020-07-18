package com.thejuki.kformmaster.extensions

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime

interface IFormInlinePicker {
    fun setAllDayPicker()
    fun toggle()
    fun collapse()
    fun expand()
    fun setDateTime(dateTime : LocalDateTime)
    fun setStartDate(date : LocalDate?)
}