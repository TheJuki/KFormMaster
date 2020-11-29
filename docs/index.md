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
<androidx.recyclerview.widget.RecyclerView xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:orientation="vertical"
    android:id="@+id/recyclerView"
    android:divider="#b5b5b5"
    app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"
    android:descendantFocusability="beforeDescendants" />
```

* Step 2 (No DSL). Add the Form Elements programmatically in your activity
```kotlin
// Initialize variables
formBuilder = FormBuildHelper()
formBuilder.attachRecyclerView(binding.recyclerView)

val elements: MutableList<BaseFormElement<*>> = mutableListOf()

// Declare form elements
val emailElement = FormEmailEditTextElement(Email.ordinal).apply {
    title = getString(R.string.email)
}

elements.add(emailElement)

val passwordElement = FormPasswordEditTextElement(Password.ordinal).apply {
    title = getString(R.string.password)
}

elements.add(passwordElement)

// Add form elements (Form is refreshed for you)
formBuilder.addFormElements(elements)
```

* Step 2 (With DSL). Add the Form Elements programmatically in your activity
```kotlin
formBuilder = form(binding.recyclerView) {
    email(Email.ordinal) {
        title = getString(R.string.email)
    }
    password(Password.ordinal) {
        title = getString(R.string.password)
    }
}
```