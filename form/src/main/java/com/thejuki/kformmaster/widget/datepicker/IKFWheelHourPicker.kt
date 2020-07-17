package br.com.sigiweb.sigiweb.util.form.datepicker

import org.threeten.bp.LocalDate

interface IKFWheelHourPicker {

    var selectedHour: Int
    var selectedDate: LocalDate
    var allHours: Boolean

}