package com.thejuki.kformmaster.view

import android.view.View
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatTextView
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.helper.FormViewFinder
import com.thejuki.kformmaster.model.FormCheckBoxElement

/**
 * Form CheckBox Binder
 *
 * View Binder for [FormCheckBoxElement]
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormCheckBoxViewRenderer(private val formBuilder: FormBuildHelper, @LayoutRes private val layoutID: Int?) : BaseFormViewRenderer() {
    val viewRenderer = ViewRenderer(layoutID
            ?: R.layout.form_element_checkbox, FormCheckBoxElement::class.java) { model, finder: FormViewFinder, _ ->
        val textViewTitle = finder.find(R.id.formElementTitle) as? AppCompatTextView
        val mainViewLayout = finder.find(R.id.formElementMainLayout) as? LinearLayout
        val textViewError = finder.find(R.id.formElementError) as? AppCompatTextView
        val dividerView = finder.find(R.id.formElementDivider) as? View
        val itemView = finder.getRootView() as View
        val checkBox = finder.find(R.id.formElementValue) as AppCompatCheckBox
        baseSetup(model, dividerView, textViewTitle, textViewError, itemView, editView = checkBox)

        checkBox.isChecked = model.isChecked()

        // Delay setting to make sure editView is set first
        model.mainLayoutView = mainViewLayout

        setCheckBoxFocusEnabled(model, itemView, checkBox)

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            model.error = null
            if (isChecked) {
                model.setValue(model.checkedValue)
            } else {
                model.setValue(model.unCheckedValue)
            }
            formBuilder.onValueChanged(model)
        }

        checkBox.setOnClickListener {
            // Invoke onClick Unit
            model.onClick?.invoke()
        }
    }

    private fun setCheckBoxFocusEnabled(model: FormCheckBoxElement<*>, itemView: View, checkBox: AppCompatCheckBox) {
        itemView.setOnClickListener {
            // Invoke onClick Unit
            model.onClick?.invoke()

            checkBox.isChecked = !checkBox.isChecked
        }
    }
}
