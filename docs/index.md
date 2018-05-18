## Installation
Add this in your app's **build.gradle** file:
```
ext {
  kFormMasterVersion = [Latest]
}

implementation "com.thejuki:k-form-master:$kFormMasterVersion"
```

## How to use
* Step 1. Add a RecyclerView anywhere in the layout where you want your list to be shown (If confused, look at the examples in this repo).

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <android.support.v7.widget.RecyclerView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        android:id="@+id/recyclerView"
        android:descendantFocusability="beforeDescendants" />

</LinearLayout>
```

* Step 2 (No DSL). Add the Form Elements programmatically in your activity
```kotlin
// Initialize variables
formBuilder = FormBuildHelper(this)
formBuilder.attachRecyclerView(this, recyclerView)

val elements: MutableList<BaseFormElement<*>> = mutableListOf()

// Declare form elements
val emailElement = FormEmailEditTextElement(Email.ordinal)
        .setTitle(getString(R.string.email))
elements.add(emailElement)

val passwordElement = FormPasswordEditTextElement(Password.ordinal)
        .setTitle(getString(R.string.password))
elements.add(passwordElement)

// Add form elements (Form is refreshed for you)
formBuilder.addFormElements(elements)
```

* Step 2 (With DSL). Add the Form Elements programmatically in your activity
```kotlin
formBuilder = form(this, recyclerView) {
    email(Email.ordinal) {
        title = getString(R.string.email)
    }
    password(Password.ordinal) {
        title = getString(R.string.password)
    }
}
```

## Reference

### Item Definition

#### Header:
```kotlin
val header = FormHeader.createInstance(getString(R.string.HeaderString))
```

#### Regular Elements:
 
**General object format**
```kotlin
val element = Form[Type]Element<T: Serializable>(TAG_NAME: Int) // Tag is optional. It is recommended to use an Enum's ordinal.
    .setTitle("Pick your favourite fruit") // setting title
    .setValue("Banana") // setting value of the field, if any
    .setOptions(fruits) // setting pickable options, if any
    .setHint("e.g. banana, guava etc") // setting hints, if any
    .setRequired(false) // marking if the form element is required to be filled to make the form valid, include if needed
```

**Samples:**
```kotlin
// Example Enum
private enum class Tag {
    Email,
    Phone,
    Location,
    Address,
    ZipCode,
    Date,
    Time,
    DateTime,
    Password,
    SingleItem,
    MultiItems,
    AutoCompleteElement,
    AutoCompleteTokenElement,
    ButtonElement,
    TextViewElement,
    SwitchElement,
    SliderElement,
    CheckBoxElement,
}

// Example Picker object
import java.io.Serializable

data class ListItem(val id: Long? = null,
                    val name: String? = null
): Serializable {
    override fun toString(): String {
        return name.orEmpty()
    }
}

// Example Autocomplete object
import java.io.Serializable

data class ContactItem(val id: Long? = null,
                       val value: String? = null,
                       val label: String? = null
): Serializable {
    override fun toString(): String {
        return label.orEmpty()
    }
}

val listener = object : OnFormElementValueChangedListener {
    override fun onValueChanged(formElement: BaseFormElement<*>) {

    }
}

formBuilder = form(this@ActivityName, recyclerView, listener, cacheForm = true) {

    // Header
    header { title = getString(R.string.PersonalInfo) }

    // Email EditText
    email(Email.ordinal) {
        title = getString(R.string.email)
        hint = getString(R.string.email_hint)
    }

    // Password EditText
    password(Password.ordinal) {
        title = getString(R.string.password)
    }

    // Phone EditText
    phone(Phone.ordinal) {
        title = getString(R.string.Phone)
        value = "+8801712345678"
    }

    // Singleline text EditText
    text(Location.ordinal) {
        title = getString(R.string.Location)
        value = "Dhaka"
    }

    // Multiline EditText
    textArea(Address.ordinal) {
        title = getString(R.string.Address)
        value = ""
    }

    // Number EditText
    number(ZipCode.ordinal) {
        title = getString(R.string.ZipCode)
        value = "1000"
    }

    // Date
    date(Tag.Date.ordinal) {
        title = getString(R.string.Date)
        dateValue = Date()
        dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.US)
    }

    // Time
    time(Time.ordinal) {
        title = getString(R.string.Time)
        dateValue = Date()
        dateFormat = SimpleDateFormat("hh:mm a", Locale.US)
    }

    // DateTime
    dateTime(DateTime.ordinal) {
        title = getString(R.string.DateTime)
        dateValue = Date()
        dateFormat = SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.US)
    }

    // DropDown
    dropDown<ListItem>(SingleItem.ordinal) {
        title = getString(R.string.SingleItem)
        dialogTitle = getString(R.string.SingleItem)
        options = fruits
        value = ListItem(id = 1, name = "Banana")
    }

    // MultiCheckBox
    multiCheckBox<ListItem>(MultiItems.ordinal) {
        title = getString(R.string.MultiItems)
        dialogTitle = getString(R.string.MultiItems)
        options = fruits
        optionsSelected = listOf(ListItem(id = 1, name = "Banana"))
    }

    // AutoComplete
    autoComplete<ContactItem>(AutoCompleteElement.ordinal) {
        title = getString(R.string.AutoComplete)
        arrayAdapter = ContactAutoCompleteAdapter(this@FormListenerActivity,
                android.R.layout.simple_list_item_1, defaultItems =
        arrayListOf(ContactItem(id = 1, value = "", label = "Try \"Apple May\"")))
        dropdownWidth = ViewGroup.LayoutParams.MATCH_PARENT
        value = ContactItem(id = 1, value = "John Smith", label = "John Smith (Tester)")
    }

    // AutoCompleteToken
    autoCompleteToken<ArrayList<ContactItem>>(AutoCompleteTokenElement.ordinal) {
        title = getString(R.string.AutoCompleteToken)
        arrayAdapter = EmailAutoCompleteAdapter(this@FormListenerActivity,
                android.R.layout.simple_list_item_1)
        dropdownWidth = ViewGroup.LayoutParams.MATCH_PARENT
    }

    // Text View
    textView(TextViewElement.ordinal) {
        title = getString(R.string.TextView)
        value = "This is readonly!"
    }

    // Switch
    switch<String>(SwitchElement.ordinal) {
        title = getString(R.string.Switch)
        value = "Yes"
        onValue = "Yes"
        offValue = "No"
    }

    // Slider
    slider(SliderElement.ordinal) {
        title = getString(R.string.Slider)
        value = 50
        min = 0
        max = 100
        steps = 20
    }

    // CheckBox
    checkBox<Boolean>(CheckBoxElement.ordinal) {
        title = getString(R.string.CheckBox)
        value = true
        checkedValue = true
        unCheckedValue = false
    }

    // Button
    button(ButtonElement.ordinal) {
        value = getString(R.string.Button)
        valueObservers.add({ newValue, element ->
            val confirmAlert = AlertDialog.Builder(this@PartialScreenFormActivity).create()
            confirmAlert.setTitle(this@PartialScreenFormActivity.getString(R.string.Confirm))
            confirmAlert.setButton(AlertDialog.BUTTON_POSITIVE, this@PartialScreenFormActivity.getString(android.R.string.ok), { _, _ ->
                // Could be used to clear another field:
                val dateToDeleteElement = formBuilder.getFormElement<FormPickerDateElement>(Tag.Date.ordinal)
                // Display current date
                Toast.makeText(this@FullscreenFormActivity,
                        dateToDeleteElement.value?.getTime().toString(),
                        Toast.LENGTH_SHORT).show()
                dateToDeleteElement.clear()
                formBuilder.onValueChanged(dateToDeleteElement)
            })
            confirmAlert.setButton(AlertDialog.BUTTON_NEGATIVE, this@PartialScreenFormActivity.getString(android.R.string.cancel), { _, _ ->
            })
            confirmAlert.show()
        })
    }
}
```

### Cache Form Elements
By default, RecyclerView will cache 2 views. By setting cacheForm = true in the FormBuildHelper, all form elements will be cached instead of recycled.
NOTE: This is enabled by default now.

### Refresh Dropdown or MultiCheckBox options
After changing the options in the model for a Dropdown or MultiCheckBox element, call the reInitDialog function.

```kotlin
element.options = listOf()
element.reInitDialog(this /* context */, formBuilder)
```

### Set form element value change listener to get changed value instantly
While creating a new instance of FormBuildHelper, add a listener in the constructor

```kotlin
var formBuilder = FormBuildHelper(this, object : OnFormElementValueChangedListener {
    override fun onValueChanged(formElement: BaseFormElement<*>) {
         // do anything here with formElement.value
    }
}, findViewById(R.id.recyclerView), cacheForm = true)
```

### Get value for unique form elements
Use the unique tag assigned earlier to retrieve value (See examples in this repo)
```kotlin
val element = formBuilder.getFormElement<FormEmailEditTextElement>(Email.ordinal)
val value: String = element.value
```
Use the added index of the element instead if you did not assign a tag.
```kotlin
val element = formBuilder.getElementAtIndex(2)
val value: String = element?.value as String
```

### Check if form is valid
Use this variable (method in Java) if you need to check whether the required elements of the form are completed
```kotlin
formBuilder.isValidForm // returns Boolean whether the form is valid or not
```

### Form accent color change
If you want to change the colors, just override the colors in your **colors.xml** file:
```xml
<color name="colorFormMasterHeaderBackground">#DDDDDD</color>
<color name="colorFormMasterHeaderText">#000000</color>
<color name="colorFormMasterElementBackground">#FFFFFF</color>
<color name="colorFormMasterElementTextTitle">#222222</color>
<color name="colorFormMasterElementErrorTitle">#FF0000</color>
<color name="colorFormMasterElementTextValue">#000000</color>
<color name="colorFormMasterElementButtonText">#42A5F5</color>
<color name="colorFormMasterElementFocusedTitle">#0277bd</color>
<color name="colorFormMasterElementTextDisabled">#757575</color>
<color name="colorFormMasterDivider">#DDDDDD</color>
<color name="colorFormMasterElementToken">#f5f5f5</color>
```

### Form UI change
If you want to change how the forms look, just override a form_element XML in your project.

Just make sure to keep the ID name the same as it is in the library for the components.
```
android:id="@+id/formElementTitle"
android:id="@+id/formElementValue"
...
```