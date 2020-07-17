package br.com.sigiweb.sigiweb.util.form.datepicker

import org.threeten.bp.LocalDate

interface IKFWheelExtendedDatePicker {

    var selectedDate: LocalDate
    var startDate: LocalDate?

}