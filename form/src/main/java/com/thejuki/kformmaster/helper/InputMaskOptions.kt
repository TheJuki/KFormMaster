package com.thejuki.kformmaster.helper

import android.text.TextWatcher
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.redmadrobot.inputmask.helper.AffinityCalculationStrategy
import com.redmadrobot.inputmask.model.Notation

class InputMaskOptions(
        var primaryFormat: String,
        var affineFormats: List<String> = emptyList(),
        var customNotations: List<Notation> = emptyList(),
        var affinityCalculationStrategy: AffinityCalculationStrategy = AffinityCalculationStrategy.WHOLE_STRING,
        var autocomplete: Boolean = true,
        var listener: TextWatcher? = null,
        var valueListener: MaskedTextChangedListener.ValueListener? = null,
        var rightToLeft: Boolean = false
)
