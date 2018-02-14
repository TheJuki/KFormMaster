# F-Form-Master

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
    kFormMasterVersion = '1.0.0'
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

* Step 2. Add the Form Elements programmatically in your activity
```kotlin
// initialize variables
 mFormBuilder = FormBuildHelper(this, object : OnFormElementValueChangedListener {
    override fun onValueChanged(formElement: BaseFormElement<*>) {

    }
})
mFormBuilder!!.attachRecyclerView(this, recyclerView)
val elements: MutableList<BaseFormElement<*>> = mutableListOf()

// declare form elements
val emailElement = FormEditTextElement<String>(Email.ordinal)
emailElement.mType = BaseFormElement.TYPE_EDITTEXT_EMAIL
emailElement.mTitle = getString(R.string.email)
emailElement.mValue = ""
elements.add(emailElement)

val passwordElement = FormEditTextElement<String>(Password.ordinal)
passwordElement.mType = BaseFormElement.TYPE_EDITTEXT_PASSWORD
passwordElement.mTitle = getString(R.string.password)
elements.add(passwordElement)

// build and display the form
mFormBuilder!!.addFormElements(elements)
mFormBuilder!!.refreshView()
```

### Changelog

#### v1.0.0
1. Just released!
2. Converted to Kotlin using the fork by [shaymargolis](https://github.com/shaymargolis/FormMaster).
3. Added DateTime, Button, Switch, and Token AutoComplete using [TokenAutoComplete](https://github.com/splitwise/TokenAutoComplete).

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
    .setType(BaseFormElement.TYPE_PICKER_MULTI_CHECKBOX) // setting input type
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
    Switch,
    Password,
    SingleItem,
    MultiItems,
    AutoCompleteElement,
    AutoCompleteTokenElement,
    ButtonElement,
    TextViewElement
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

// Email
val element = FormEditTextElement<String>(Tag.Email.ordinal)
element.mType = BaseFormElement.TYPE_EDITTEXT_EMAIL
element.mTitle = getString(R.string.email)
element.mValue = ""

// Password input
val element = FormEditTextElement<String>(Password.ordinal)
element.mType = BaseFormElement.TYPE_EDITTEXT_PASSWORD
element.mTitle = getString(R.string.password)

// Phone number input
val element = FormEditTextElement<String>(Phone.ordinal)
element.mType = BaseFormElement.TYPE_EDITTEXT_PHONE
element.mTitle = getString(R.string.Phone)
element.mValue = "+8801712345678"

// Single line text input
val element = FormEditTextElement<String>(Location.ordinal)
element.mType = BaseFormElement.TYPE_EDITTEXT_TEXT_SINGLELINE
element.mTitle = getString(R.string.Location)
element.mValue = "Dhaka"

// Multi line text input (default 4)
val element = FormEditTextElement<String>(Address.ordinal)
element.mType = BaseFormElement.TYPE_EDITTEXT_TEXT_MULTILINE
element.mTitle = getString(R.string.Address)
element.mValue = ""

// Number element input
val element = FormEditTextElement<String>(ZipCode.ordinal)
element.mType = BaseFormElement.TYPE_EDITTEXT_NUMBER
element.mTitle = getString(R.string.ZipCode)
element.mValue = "1000"

// Date picker input
val element = FormPickerDateElement(Tag.Date.ordinal)
element.mType = BaseFormElement.TYPE_EDITTEXT_NUMBER
element.mTitle = getString(R.string.Date)
element.mValue = FormPickerDateElement.DateHolder(Date(),
        SimpleDateFormat("MM/dd/yyyy", Locale.US))

// Time picker input
val element = FormPickerTimeElement(Time.ordinal)
element.mType = BaseFormElement.TYPE_EDITTEXT_NUMBER
element.mTitle = getString(R.string.Time)
element.mValue = FormPickerTimeElement.TimeHolder(Date(),
        SimpleDateFormat("hh:mm a", Locale.US))

// DateTime picker input
val element = FormPickerDateTimeElement(DateTime.ordinal)
element.mType = BaseFormElement.TYPE_EDITTEXT_NUMBER
element.mTitle = getString(R.string.DateTime)
element.mValue = FormPickerDateTimeElement.DateTimeHolder(Date(),
        SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.US))

// Switch input
val element = FormSwitchElement<String>(Switch.ordinal)
element.mTitle = getString(R.string.is_elephant)
element.mValue = "No"
element.mOnValue = "Yes"
element.mOffValue = "No"

or 

val element = FormSwitchElement<Boolean>(Switch.ordinal)
element.mTitle = getString(R.string.is_elephant)
element.mValue = false
element.mOnValue = true
element.mOffValue = false

// Single item picker input
val fruits = listOf<ListItem>(ListItem(id = 1, name = "Banana"),
        ListItem(id = 2, name = "Orange"),
        ListItem(id = 3, name = "Mango"),
        ListItem(id = 4, name = "Guava")
)
val element = singleItemElement = FormPickerDropDownElement<ListItem>(SingleItem.ordinal)
element.dialogTitle = getString(R.string.SingleItem)
element.mTitle = getString(R.string.SingleItem)
element.mOptions = fruits
element.mValue = ListItem(id = 1, name = "Banana")

// Multiple items picker input
val fruits = listOf<ListItem>(ListItem(id = 1, name = "Banana"),
        ListItem(id = 2, name = "Orange"),
        ListItem(id = 3, name = "Mango"),
        ListItem(id = 4, name = "Guava")
)
val element = FormPickerMultiCheckBoxElement<ListItem>(MultiItems.ordinal)
element.dialogTitle = getString(R.string.MultiItems)
element.mTitle = getString(R.string.MultiItems)
element.mOptions = fruits
element.mOptionsSelected = listOf(ListItem(id = 1, name = "Banana"))
elements.add(element)

// AutoComplete input (See sample for an example of ContactAutoCompleteAdapter)
val element = FormAutoCompleteElement<ContactItem>(AutoCompleteElement.ordinal)
element.arrayAdapter = ContactAutoCompleteAdapter(this,
        android.R.layout.simple_list_item_1, defaultItems =
arrayListOf(ContactItem(id = 1, value = "", label = "Try \"Apple May\"")))
element.dropdownWidth = ViewGroup.LayoutParams.MATCH_PARENT
element.mTitle = getString(R.string.AutoComplete)
element.mValue =
        ContactItem(id = 1, value = "John Smith", label = "John Smith (Tester)")

// AutoComplete input (See sample for an example of EmailAutoCompleteAdapter)
// You can use the same adapter as FormAutoCompleteElement. In my case, I used a different API call for FormTokenAutoCompleteElement.
val element = FormTokenAutoCompleteElement<ArrayList<ContactItem>>(AutoCompleteTokenElement.ordinal)
element.arrayAdapter = EmailAutoCompleteAdapter(this,
        android.R.layout.simple_list_item_1)
element.dropdownWidth = ViewGroup.LayoutParams.MATCH_PARENT
element.mTitle = getString(R.string.AutoCompleteToken)

// Text View for showing disabled/readonly text with a label
val element = FormTextViewElement<String>(TextViewElement.ordinal)
element.mTitle = getString(R.string.TextView) // label
element.mValue = "This is readonly!" // read-only text

// Button
val element = FormButtonElement<String>(ButtonElement.ordinal)
element.mValue = getString(R.string.Button)
// Listen for this element's changes. For a button, this just means the button was clicked.
element.mValueChanged = object : OnFormElementValueChangedListener {
    override fun onValueChanged(formElement: BaseFormElement<*>) {
        val confirmAlert = AlertDialog.Builder(this@FormListenerActivity).create()
        confirmAlert.setTitle(this@FormListenerActivity.getString(R.string.Confirm))
        confirmAlert.setButton(AlertDialog.BUTTON_POSITIVE, this@FormListenerActivity.getString(android.R.string.ok), { _, _ ->
            // Could be used to clear another field:
            val dateToDeleteElement = mFormBuilder!!.getFormElement(Tag.Date.ordinal)
            // Display current date
            Toast.makeText(this@FormListenerActivity,
                    (dateToDeleteElement!!.getValue() as FormPickerDateElement.DateHolder).getTime().toString(),
                    Toast.LENGTH_SHORT).show()
            (dateToDeleteElement.getValue() as FormPickerDateElement.DateHolder).useCurrentDate()
            mFormBuilder!!.onValueChanged(dateToDeleteElement)
            mFormBuilder!!.refreshView()
        })
        confirmAlert.setButton(AlertDialog.BUTTON_NEGATIVE, this@FormListenerActivity.getString(android.R.string.cancel), { _, _ ->
        })
        confirmAlert.show()
    }
}
```

### Set form element value change listener to get changed value instantly
While creating a new instance of FormBuildHelper, add a listener in the constructor

Have a look at the example code for details

```kotlin
var mFormBuilder = FormBuildHelper(this, object : OnFormElementValueChangedListener {
    override fun onValueChanged(formElement: BaseFormElement<*>) {
         // do anything here with formElement.getValue()   
    }
})
```

### Get value for unique form elements
Use the unique tag assigned earlier to retrieve value (See examples in this repo)
```kotlin
val element = mFormBuilder!!.getFormElement(Email.ordinal)
val value: String = element?.getValue() as String
```

### Check if form is valid
Use this method if you need to check whether the required elements of the form is completed
```kotlin
mFormBuilder.isValidForm() // returns boolean whether the form is valid or not
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

### Credits

* The FormMaster library from adib2149 (https://github.com/adib2149/FormMaster) is the original project this Kotlin port is based on
* The FormMaster fork from shaymargolis (https://github.com/shaymargolis/FormMaster) is used for the generics support and addition of the AutoComplete form element
* The Renderer Recycler View Adapter library from vivchar (https://github.com/vivchar/RendererRecyclerViewAdapter) is used to support several types of cells
* The TokenAutoComplete library from splitwise (https://github.com/splitwise/TokenAutoComplete) is for the AutoCompleteToken form element

License
-----------------
The library is available as open source under the terms of the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).