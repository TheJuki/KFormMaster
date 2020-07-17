package br.com.sigiweb.sigiweb.util.form.datepicker

import com.aigestudio.wheelpicker.widgets.WheelYearPicker
import org.threeten.bp.LocalDate
import java.util.*

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