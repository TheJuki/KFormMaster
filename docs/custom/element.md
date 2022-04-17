# Custom Element

It's possible to create your own form element. Here is what is needed.

### Form Element Layout

Create a new XML layout file. We'll name it form_element_custom.xml.

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="@color/colorFormMasterElementBackground"
    android:orientation="vertical"
    android:paddingBottom="16dp">

    <View
        android:id="@+id/formElementDivider"
        android:layout_width="match_parent"
        android:layout_height="0.5dp"
        android:layout_marginLeft="16dp"
        android:layout_marginRight="16dp"
        android:background="@color/colorFormMasterDivider" />

    <LinearLayout
        android:id="@+id/formElementMainLayout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginLeft="16dp"
        android:layout_marginRight="16dp"
        android:layout_marginTop="16dp"
        android:orientation="horizontal">

        <com.thejuki.kformmaster.widget.IconTextView
            android:id="@+id/formElementTitle"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="2"
            android:textColor="@color/colorFormMasterElementTextTitle"
            android:textSize="@dimen/elementTextTitleSize"
            tools:text="Custom Title" />

        <com.thejuki.kformmaster.widget.ClearableEditText
            android:id="@+id/formElementValue"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="3"
            android:background="@null"
            android:gravity="end"
            android:imeOptions="actionNext"
            android:inputType="textNoSuggestions"
            android:maxLines="1"
            android:textColor="@drawable/edit_text_selector"
            android:textColorHint="@color/colorFormMasterElementHint"
            android:textSize="@dimen/elementTextValueSize"
            tools:text="Custom Value" />

    </LinearLayout>

    <androidx.appcompat.widget.AppCompatTextView
        android:id="@+id/formElementError"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_marginLeft="16dp"
        android:layout_marginRight="16dp"
        android:layout_marginTop="10dp"
        android:layout_weight="1"
        android:textColor="@color/colorFormMasterElementErrorTitle"
        android:textSize="@dimen/elementErrorTitleSize"
        android:visibility="gone"
        tools:text="Personal Info" />

</LinearLayout>
```

### Form Element Model

Note that a new model does not need to contain a body if BaseFormElement provides everything you need.

```kotlin
class FormCustomElement(tag: Int = -1) : BaseFormElement<String>(tag)
```

### Optional: Form Builder Extension

Create a FormBuildHelper DSL method for your custom form model.

```kotlin
/** FormBuildHelper extension to add a CustomElement */
fun FormBuildHelper.customEx(tag: Int = -1, init: FormCustomElement.() -> Unit): FormCustomElement {
    return addFormElement(FormCustomElement(tag).apply(init))
}
```

### Form Element View Binder

Create a view binder for your custom form element.

!!! note "ViewRenderer"

    * layoutID parameter - Form element layout name
    * type parameter - Form element model class (ModelName::class.java)
    * binder parameter:
        - model is a form element instance model
        - finder can find views or set fields of a view using its ID
        - payloads can be replaced with "_" as it is not used
    * viewStateProvider parameter - Form element view state provider

```kotlin
class CustomViewRenderer(private val formBuilder: FormBuildHelper, 
    @LayoutRes private val layoutID: Int?) : BaseFormViewRenderer() {
    val viewRenderer = ViewRenderer(layoutID
            ?: R.layout.form_element_custom, FormCustomElement::class.java) { model, finder: FormViewFinder, _ ->
        val textViewTitle = finder.find(R.id.formElementTitle) as AppCompatTextView
        val mainViewLayout = finder.find(R.id.formElementMainLayout) as? LinearLayout
        val textViewError = finder.find(R.id.formElementError) as AppCompatTextView
        val dividerView = finder.find(R.id.formElementDivider) as? View
        val itemView = finder.getRootView() as View
        val editTextValue = finder.find(R.id.formElementValue) as com.thejuki.kformmaster.widget.ClearableEditText
        baseSetup(model, dividerView, textViewTitle, textViewError, itemView, mainViewLayout, editTextValue)

        editTextValue.setText(model.valueAsString)
        editTextValue.hint = model.hint ?: ""

        // Initially use 4 lines
        // unless a different number was provided
        if (model.maxLines == 1) {
            model.maxLines = 4
        }

        // If an InputType is provided, use it instead
        model.inputType?.let { editTextValue.setRawInputType(it) }

        // If imeOptions are provided, use them instead of creating a new line
        model.imeOptions?.let { editTextValue.imeOptions = it }

        setEditTextFocusEnabled(editTextValue, itemView)
        setOnFocusChangeListener(context, model, formBuilder)
        addTextChangedListener(model, formBuilder)
        setOnEditorActionListener(model, formBuilder)
    }

    private fun setEditTextFocusEnabled(editTextValue: AppCompatEditText, itemView: View) {
        itemView.setOnClickListener {
            editTextValue.requestFocus()
            val imm = itemView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            editTextValue.setSelection(editTextValue.text?.length ?: 0)
            imm.showSoftInput(editTextValue, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}
```

### Use your Custom Form Element

Create a form activity for your custom form element.

!!! error "IMPORTANT"

    * Register your custom view binder or you will get a RuntimeException
    * RuntimeException: ViewRenderer not registered for this type

```kotlin
class CustomFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormBinding

    // Setup the FormBuildHelper at the class level if necessary
    private lateinit var formBuilder: FormBuildHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFormBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupForm()
    }

    private enum class Tag {
        Custom
    }

    private fun setupForm() {
        formBuilder = form(binding.recyclerView) {
            customEx(Tag.Custom.ordinal) {
                title = getString(R.string.Custom)
            }
        }

        // Required
        formBuilder.registerCustomViewRenderer(CustomViewRenderer(formBuilder).viewRenderer)
    }
}
```
