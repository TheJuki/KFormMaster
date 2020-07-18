package com.thejuki.kformmaster.widget.datepicker

import org.threeten.bp.LocalDate

interface IKFWheelMinutePicker {

    var selectedMinute: Int
    var selectedDate: LocalDate
    var allMinutes: Boolean

}