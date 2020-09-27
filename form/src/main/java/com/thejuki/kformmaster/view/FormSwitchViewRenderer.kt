package com.thejuki.kformmaster.view

import android.view.View
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.SwitchCompat
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.helper.FormViewFinder
import com.thejuki.kformmaster.model.FormSwitchElement

/**
 * Form Switch Binder
 *
 * View Binder for [FormSwitchElement]
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormSwitchViewRenderer(private val formBuilder: FormBuildHelper, @LayoutRes private val layoutID: Int?) : BaseFormViewRenderer() {
    val viewRenderer = ViewRenderer(layoutID
            ?: R.layout.form_element_switch, FormSwitchElement::class.java) { model, finder: FormViewFinder, _ ->
        val textViewTitle = finder.find(R.id.formElementTitle) as? AppCompatTextView
        val mainViewLayout = finder.find(R.id.formElementMainLayout) as? LinearLayout
        val textViewError = finder.find(R.id.formElementError) as? AppCompatTextView
        val dividerView = finder.find(R.id.formElementDivider) as? View
        val itemView = finder.getRootView() as View
        val switch = finder.find(R.id.formElementValue) as SwitchCompat
        baseSetup(model, dividerView, textViewTitle, textViewError, itemView, editView = switch)

        switch.isChecked = model.isOn()

        // Delay setting to make sure editView is set first
        model.mainLayoutView = mainViewLayout

        setSwitchFocusEnabled(model, itemView, switch)

        switch.setOnCheckedChangeListener { _, isChecked ->
            model.error = null
            if (isChecked) {
                model.setValue(model.onValue)
            } else {
                model.setValue(model.offValue)
            }
            formBuilder.onValueChanged(model)
        }

        switch.setOnClickListener {
            // Invoke onClick Unit
            model.onClick?.invoke()
        }
    }

    private fun setSwitchFocusEnabled(model: FormSwitchElement<*>, itemView: View, switch: SwitchCompat) {
        itemView.setOnClickListener {
            // Invoke onClick Unit
            model.onClick?.invoke()

            switch.isChecked = !switch.isChecked
        }
    }
}
