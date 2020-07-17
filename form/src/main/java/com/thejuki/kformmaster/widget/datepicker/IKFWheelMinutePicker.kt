package br.com.sigiweb.sigiweb.util.form.datepicker

import org.threeten.bp.LocalDate

interface IKFWheelMinutePicker {

    var selectedMinute: Int
    var selectedDate: LocalDate
    var allMinutes: Boolean

}