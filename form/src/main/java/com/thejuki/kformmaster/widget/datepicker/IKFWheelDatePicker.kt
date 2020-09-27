package com.thejuki.kformmaster.widget.datepicker

import com.aigestudio.wheelpicker.widgets.WheelYearPicker
import org.threeten.bp.LocalDate
import java.util.*

/**
 * Wheel Date Picker Interface
 *
 * @author **soareseneves** ([GitHub](https://github.com/soareseneves))
 * @version 1.0
 */
interface IKFWheelDatePicker {
    fun setOnDateSelectedListener(listener: KFWheelDatePicker.SWOnDateSelectedListener?)
    val currentDate: Date?
    var itemAlignYear: Int
    var itemAlignMonth: Int
    var itemAlignDay: Int
    val wheelYearPicker: WheelYearPicker?
    val wheelMonthPicker: KFWheelMonthPicker?
    val wheelDayPicker: KFWheelDayPicker?
    var startDate: LocalDate?
}