package com.thejuki.kformmaster.model

import android.os.Build
import android.widget.ProgressBar

/**
 * Form Progress Element
 *
 * Form element for ProgressBar
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
open class FormProgressElement(tag: Int = -1) : BaseFormElement<Int>(tag) {

    /**
     * No validation needed
     */
    override val isValid: Boolean
        get() = validityCheck()

    override var validityCheck = { true }

    /**
     * Progress bar style
     */
    enum class ProgressBarStyle {
        HorizontalBar,
        SmallCircle,
        SmallInverseCircle,
        MediumCircle,
        MediumInverseCircle,
        LargeCircle,
        LargeInverseCircle
    }

    /**
     * Selected Progress bar style
     * By default this is ProgressBarStyle.HorizontalBar.
     */
    var progressBarStyle: FormProgressElement.ProgressBarStyle = FormProgressElement.ProgressBarStyle.HorizontalBar

    /**
     * Determines if the progress bar should be intermediate.
     * This is false by default.
     */
    var indeterminate: Boolean = false
        set(value) {
            field = value

            editView?.let {
                if (it is ProgressBar) {
                    it.isIndeterminate = value
                }
            }
        }

    /**
     * Progress of the progress bar
     */
    var progress: Int = 0
        get() {
            return if (field <= 0) 0 else field
        }
        set(value) {
            field = value

            editView?.let {
                if (it is ProgressBar) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        it.setProgress(value, true)
                    } else {
                        it.progress = value
                    }
                }
            }
        }

    /**
     * Secondary Progress of the progress bar
     */
    var secondaryProgress: Int = 0
        get() {
            return if (field <= 0) 0 else field
        }
        set(value) {
            field = value

            editView?.let {
                if (it is ProgressBar) {
                    it.secondaryProgress = value
                }
            }
        }

    /**
     * Maximum progress of the progress bar
     */
    var max: Int = 100
        get() {
            return if (field <= 0) 100 else field
        }
        set(value) {
            field = value

            editView?.let {
                if (it is ProgressBar) {
                    it.max = value
                }
            }
        }


    /**
     * Minimum progress of the progress bar
     */
    var min: Int = 0
        get() {
            return if (field <= 0) 0 else field
        }
        set(value) {
            field = value

            editView?.let {
                if (it is ProgressBar) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        it.min = value
                    }
                }
            }
        }


}