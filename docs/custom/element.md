It's possible to create your own form element. Here is what is needed.

### Form Element Layout
Create a new XML layout file. We'll name it form_element_custom.xml.
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="@android:color/black"
    android:orientation="vertical"
    android:paddingBottom="16dp">

    <View
        android:layout_width="match_parent"
        android:layout_height="0.5dp"
        android:layout_marginLeft="16dp"
        android:layout_marginRight="16dp"
        android:background="@color/colorFormMasterDivider" />

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginLeft="16dp"
        android:layout_marginRight="16dp"
        android:layout_marginTop="16dp"
        android:orientation="horizontal">

        <android.support.v7.widget.AppCompatTextView
            android:id="@+id/formElementTitle"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="2"
            android:textColor="@android:color/white"
            android:textSize="@dimen/elementTextTitleSize"
            tools:text="Test Title" />

        <android.support.v7.widget.AppCompatEditText
            android:id="@+id/formElementValue"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="3"
            android:background="@null"
            android:gravity="end"
            android:imeOptions="actionNext"
            android:inputType="textNoSuggestions"
            android:maxLines="1"
            android:textColor="@color/colorFormMasterElementFocusedTitle"
            android:textSize="20sp"
            tools:text="Test Value" />

    </LinearLayout>

    <android.support.v7.widget.AppCompatTextView
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
        tools:text="Test Error" />

</LinearLayout>
```

### Form Element Model
Note that a new model does not need to contain a body if BaseFormElement provides everything you need.
```kotlin
class FormCustomElement: BaseFormElement<String> {
    constructor() : super()
    constructor(tag: Int) : super(tag)
}
```