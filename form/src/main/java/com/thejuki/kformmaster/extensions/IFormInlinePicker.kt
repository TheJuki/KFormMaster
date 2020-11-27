package com.thejuki.kformmaster.extensions

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime

/**
 * Form Inline Picker Interface
 *
 * @author **soareseneves** ([GitHub](https://github.com/soareseneves))
 * @version 1.0
 */
interface IFormInlinePicker {
    fun setAllDayPicker()
    fun toggle()
    fun collapse()
    fun isExpanded(): Boolean
    fun expand()
    fun setDateTime(dateTime: LocalDateTime)
    fun setStartDate(date: LocalDate?)
}