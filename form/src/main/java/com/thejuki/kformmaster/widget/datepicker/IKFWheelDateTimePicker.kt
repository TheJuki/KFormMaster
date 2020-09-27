package com.thejuki.kformmaster.widget.datepicker

import org.threeten.bp.LocalDateTime

/**
 * Wheel Date Time Picker Interface
 *
 * @author **soareseneves** ([GitHub](https://github.com/soareseneves))
 * @version 1.0
 */
interface IKFWheelDateTimePicker {
    fun setOnDateSelectedListener(listener: KFWheelDateTimePicker.SWOnDateSelectedListener?)
    val currentDate: LocalDateTime?
    var itemAlignDate: Int
    var itemAlignHour: Int
    var itemAlignMinute: Int
    val wheelDatePicker: KFWheelDatePicker?
    val wheelExtendedDatePicker: KFWheelExtendedDatePicker?
    val wheelHourPicker: KFWheelHourPicker?
    val wheelMinutePicker: KFWheelMinutePicker?
}