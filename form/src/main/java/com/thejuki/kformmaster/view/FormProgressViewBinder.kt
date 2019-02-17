package com.thejuki.kformmaster.view

import android.content.Context
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatTextView
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder
import com.github.vivchar.rendererrecyclerviewadapter.ViewState
import com.github.vivchar.rendererrecyclerviewadapter.ViewStateProvider
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.model.FormProgressElement
import com.thejuki.kformmaster.model.FormProgressElement.ProgressBarStyle.*
import com.thejuki.kformmaster.state.FormProgressViewState

/**
 * Form Progress Binder
 *
 * View Binder for [FormProgressElement]
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormProgressViewBinder(private val context: Context, private val formBuilder: FormBuildHelper, @LayoutRes private val layoutID: Int?) : BaseFormViewBinder() {
    val viewBinder = ViewBinder(layoutID
            ?: R.layout.form_element_progress, FormProgressElement::class.java, { model, finder, _ ->
        val textViewTitle = finder.find(R.id.formElementTitle) as? AppCompatTextView
        val mainViewLayout = finder.find(R.id.formElementMainLayout) as? LinearLayout
        val textViewError = finder.find(R.id.formElementError) as? AppCompatTextView
        val dividerView = finder.find(R.id.formElementDivider) as? View
        val itemView = finder.getRootView() as View
        baseSetup(model, dividerView, textViewTitle, textViewError, itemView, mainViewLayout)

        var progressBar = finder.find(R.id.formElementValue) as ProgressBar

        if (model.progressBarStyle != HorizontalBar) {
            val parent = progressBar.parent as ViewGroup

            val progressBarIndex = parent.indexOfChild(progressBar)
            parent.removeView(progressBar)

            var styleAttrId = 0

            when (model.progressBarStyle) {
                SmallCircle -> styleAttrId = android.R.attr.progressBarStyleSmall
                SmallInverseCircle -> styleAttrId = android.R.attr.progressBarStyleSmallInverse
                MediumCircle -> styleAttrId = android.R.attr.progressBarStyle
                MediumInverseCircle -> styleAttrId = android.R.attr.progressBarStyleInverse
                LargeCircle -> styleAttrId = android.R.attr.progressBarStyleLarge
                LargeInverseCircle -> styleAttrId = android.R.attr.progressBarStyleLargeInverse
                else -> {
                }
            }

            progressBar = ProgressBar(context, null, styleAttrId)
            progressBar.id = R.id.formElementValue

            if (model.centerText) {
                val params = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT)
                params.addRule(RelativeLayout.CENTER_IN_PARENT)
                progressBar.layoutParams = params
            }

            parent.addView(progressBar, progressBarIndex)
        }

        progressBar.isIndeterminate = model.indeterminate

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            progressBar.min = model.min
        }
        progressBar.max = model.max

        progressBar.progress = model.progress
        progressBar.secondaryProgress = model.secondaryProgress

        model.editView = progressBar

    }, object : ViewStateProvider<FormProgressElement, ViewHolder> {
        override fun createViewStateID(model: FormProgressElement): Int {
            return model.id
        }

        override fun createViewState(holder: ViewHolder): ViewState<ViewHolder> {
            return FormProgressViewState(holder)
        }
    })
}
