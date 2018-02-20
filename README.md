# K(otlin)-Form-Master

[ ![Download](https://api.bintray.com/packages/thejuki/maven/k-form-master/images/download.svg) ](https://bintray.com/thejuki/maven/k-form-master/_latestVersion)
[![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=15)

> Easily build generic forms with minimal effort (A Kotlin port of [FormMaster](https://github.com/adib2149/FormMaster))

## Examples
| [Full Screen](https://github.com/TheJuki/KFormMaster/blob/master/app/src/main/java/com/thejuki/kformmasterexample/FullscreenFormActivity.kt) | [Partial Screen](https://github.com/TheJuki/KFormMaster/blob/master/app/src/main/java/com/thejuki/kformmasterexample/PartialScreenFormActivity.kt) | [Login](https://github.com/TheJuki/KFormMaster/blob/master/app/src/main/java/com/thejuki/kformmasterexample/LoginFormActivity.kt) |
| --- | --- | --- |
![Example](https://github.com/TheJuki/KFormMaster/blob/master/screenshots/full-screen-form.gif) | ![Example](https://github.com/TheJuki/KFormMaster/blob/master/screenshots/partial-screen-form.gif) | ![Example](https://github.com/TheJuki/KFormMaster/blob/master/screenshots/login.gif) |

This library aids in building bigger forms on-the-fly. Forms with large number of elements can easily be added programmatically within a few minutes.


## Features
- Easily build big and bigger forms with minimal effort
- Fast color change as needed
- Kotlin port of [FormMaster](https://github.com/adib2149/FormMaster)


## Installation
Add this in your app's **build.gradle** file:
```
ext {
    kFormMasterVersion = '1.2.2'
}

implementation "com.thejuki:k-form-master:$kFormMasterVersion"
```


## How to use
* Step 1. Add a Recyclerview anywhere in the layout where you want your list to be shown (If confused, look at the examples in this repo).

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
formBuilder!!.attachRecyclerView(this, recyclerView)

val elements: MutableList<BaseFormElement<*>> = mutableListOf()

// Declare form elements
val emailElement = FormEmailEditTextElement<String>(Email.ordinal)
        .setTitle(getString(R.string.email))
elements.add(emailElement)

val passwordElement = FormPasswordEditTextElement<String>(Password.ordinal)
        .setTitle(getString(R.string.password))
elements.add(passwordElement)

// Add form elements and refresh the form list
formBuilder!!.addFormElements(elements)
formBuilder!!.refreshView()
```

* Step 2 (With DSL). Add the Form Elements programmatically in your activity
```kotlin
formBuilder = form(this, recyclerView) {
    email<String>(Email.ordinal) {
        title = getString(R.string.email)
    }
    password<String>(Password.ordinal) {
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
val element = Form[Type]Element<T: Serializable>(TAG_NAME: Int) // Tag is required. It is recommended to use an Enum's ordinal.
    .setTitle("Pick your favourite fruit") // setting title
    .setValue("Banana") // setting value of the field, if any
    .setOptions(fruits) // setting pickable options, if any
    .setHint("e.g. banana, guava etc") // setting hints, if any
    .setRequired(false); // marking if the form element is required to be filled to make the form valid, include if needed
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

formBuilder = form(this@ActivityName, recyclerView, listener) {

    // Header
    header { title = getString(R.string.PersonalInfo) }

    // Email EditText
   email<String>(Email.ordinal) {
        title = getString(R.string.email)
        hint = getString(R.string.email_hint)
    }

    // Password EditText
    password<String>(Password.ordinal) {
        title = getString(R.string.password)
    }

    // Phone EditText
    phone<String>(Phone.ordinal) {
        title = getString(R.string.Phone)
        value = "+8801712345678"
    }

    // Singleline text EditText
    text<String>(Location.ordinal) {
        title = getString(R.string.Location)
        value = "Dhaka"
    }

    // Multiline EditText
    textArea<String>(Address.ordinal) {
        title = getString(R.string.Address)
        value = ""
    }

    // Number EditText
    number<String>(ZipCode.ordinal) {
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
    textView<String>(TextViewElement.ordinal) {
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

    // Button
    button<String>(ButtonElement.ordinal) {
        value = getString(R.string.Button)
        valueChanged = object : OnFormElementValueChangedListener {
            override fun onValueChanged(formElement: BaseFormElement<*>) {
                val confirmAlert = AlertDialog.Builder(this@ActivityName).create()
                confirmAlert.setTitle(this@ActivityName.getString(R.string.Confirm))
                confirmAlert.setButton(AlertDialog.BUTTON_POSITIVE, this@ActivityName.getString(android.R.string.ok), { _, _ ->
                    // Could be used to clear another field:
                    val dateToDeleteElement = formBuilder!!.getFormElement(Tag.Date.ordinal)
                    // Display current date
                    Toast.makeText(this@ActivityName,
                            (dateToDeleteElement!!.value as FormPickerDateElement.DateHolder).getTime().toString(),
                            Toast.LENGTH_SHORT).show()
                    (dateToDeleteElement.value as FormPickerDateElement.DateHolder).useCurrentDate()
                    formBuilder!!.onValueChanged(dateToDeleteElement)
                    formBuilder!!.refreshView()
                })
                confirmAlert.setButton(AlertDialog.BUTTON_NEGATIVE, this@ActivityName.getString(android.R.string.cancel), { _, _ ->
                })
                confirmAlert.show()
            }
        }
    }
}
```

### Set form element value change listener to get changed value instantly
While creating a new instance of FormBuildHelper, add a listener in the constructor

Have a look at the example code for details

```kotlin
var formBuilder = FormBuildHelper(this, object : OnFormElementValueChangedListener {
    override fun onValueChanged(formElement: BaseFormElement<*>) {
         // do anything here with formElement.value
    }
})
```

### Get value for unique form elements
Use the unique tag assigned earlier to retrieve value (See examples in this repo)
```kotlin
val element = formBuilder!!.getFormElement(Email.ordinal)
val value: String = element?.value as String
```

### Check if form is valid
Use this method if you need to check whether the required elements of the form is completed
```kotlin
formBuilder.isValidForm() // returns boolean whether the form is valid or not
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

## Contributing
You can contribute to the original Java library here: [FormMaster](https://github.com/adib2149/FormMaster)
OR
You can submit pull requests or issues to this project to make this port even better!

## Credits
* The FormMaster library from adib2149 (https://github.com/adib2149/FormMaster) is the original project this Kotlin port is based on
* The FormMaster fork from shaymargolis (https://github.com/shaymargolis/FormMaster) is used for the generics support and addition of the AutoComplete form element
* The Renderer Recycler View Adapter library from vivchar (https://github.com/vivchar/RendererRecyclerViewAdapter) is used to support several types of cells
* The TokenAutoComplete library from splitwise (https://github.com/splitwise/TokenAutoComplete) is for the AutoCompleteToken form element

License
-----------------
The library is available as open source under the terms of the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).