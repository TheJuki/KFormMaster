package com.thejuki.kformmaster.widget.datepicker

import org.threeten.bp.LocalDate

/**
 * Wheel Minute Picker Interface
 *
 * @author **soareseneves** ([GitHub](https://github.com/soareseneves))
 * @version 1.0
 */
interface IKFWheelMinutePicker {
    var selectedMinute: Int
    var selectedDate: LocalDate
    var allMinutes: Boolean
}