package com.thejuki.kformmaster.view

import android.view.View
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatTextView
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.helper.FormViewFinder
import com.thejuki.kformmaster.model.FormSegmentedInlineTitleElement

/**
 * Form Segmented ViewRenderer
 *
 * View Binder for [FormSegmentedInlineTitleElement]
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormSegmentedInlineTitleViewRenderer(private val formBuilder: FormBuildHelper, @LayoutRes private val layoutID: Int?) : BaseFormViewRenderer() {
    val viewRenderer = ViewRenderer(layoutID
            ?: R.layout.form_element_segmented_inline_title, FormSegmentedInlineTitleElement::class.java) { model, finder: FormViewFinder, _ ->
        val textViewTitle = finder.find(R.id.formElementTitle) as? AppCompatTextView
        val mainViewLayout = finder.find(R.id.formElementMainLayout) as? LinearLayout
        val textViewError = finder.find(R.id.formElementError) as? AppCompatTextView
        val dividerView = finder.find(R.id.formElementDivider) as? View
        val itemView = finder.getRootView() as View
        val segmented = finder.find(R.id.formElementValue) as com.thejuki.kformmaster.widget.SegmentedGroup
        baseSetup(model, dividerView, textViewTitle, textViewError, itemView, mainViewLayout, segmented)

        segmented.setProperties(model.marginDp, model.cornerRadius, model.tintColor,
                model.checkedTextColor, model.unCheckedTintColor, model.radioButtonPadding, model.textSize)

        if (model.value == null || model.options?.contains(model.value) == false) {
            segmented.holdup = false
        }

        segmented.setOnCheckedChangeListener { group, checkedId ->
            if (!segmented.holdup) {
                segmented.holdup = true
                val index = group.indexOfChild(group.findViewById(checkedId))
                model.error = null
                if (index < 0) {
                    model.setValue(null)
                } else {
                    model.setValue(model.options?.get(index))
                }
                formBuilder.onValueChanged(model)
            } else {
                segmented.holdup = false
            }
        }

        model.reInitGroup()

        itemView.setOnClickListener {
            // Invoke onClick Unit
            model.onClick?.invoke()
        }

    }
}
