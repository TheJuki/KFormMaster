package com.thejuki.kformmaster.widget.datepicker

import org.threeten.bp.LocalDate

/**
 * Wheel Extender Date Picker Interface
 *
 * @author **soareseneves** ([GitHub](https://github.com/soareseneves))
 * @version 1.0
 */
interface IKFWheelExtendedDatePicker {
    var selectedDate: LocalDate
    var startDate: LocalDate?
}