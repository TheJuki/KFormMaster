package com.thejuki.kformmaster.widget.datepicker

import org.threeten.bp.LocalDate

/**
 * Wheel Hour Picker Interface
 *
 * @author **soareseneves** ([GitHub](https://github.com/soareseneves))
 * @version 1.0
 */
interface IKFWheelHourPicker {
    var selectedHour: Int
    var selectedDate: LocalDate
    var allHours: Boolean
}