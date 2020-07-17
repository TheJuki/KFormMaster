package br.com.sigiweb.sigiweb.util.form.datepicker

import org.threeten.bp.LocalDateTime

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