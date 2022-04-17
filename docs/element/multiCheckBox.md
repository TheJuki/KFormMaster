# MultiCheckBox

The MultiCheckBox form element is used for a multiple choice dialog.

![Example](../images/MultiCheckBox1.PNG)

<img src="../../images/MultiCheckBox2.PNG" alt="Example" width="300px"/>

!!! note "NOTICE"

    The ListItem class is provided in the example app. Your custom class just needs to override toString() to display in the dialog.

## Theme

Set this override the default alert dialog theme.

```kotlin
multiCheckBox<ListItem, List<ListItem>>(1) {
    theme = R.style.CustomDialogPicker
}
```

## Dialog Title

By default this is "Pick one or more".
Setting this will override the alert dialog title.

```kotlin
multiCheckBox<ListItem, List<ListItem>>(1) {
    dialogTitle = "Pick one or more"
}
```

## Dialog Empty Message

By default this is "Empty".
Setting this will override the alert dialog message when the options list is empty or null.

```kotlin
multiCheckBox<ListItem, List<ListItem>>(1) {
    dialogEmptyMessage = "Nothing to see here"
}
```

## Value as String Override

This can be used to set the text that is displayed in the field. By default, the field will display the string values of the selected objects separated by commas.

```kotlin
multiCheckBox<ListItem, List<ListItem>>(1) {
    valueAsStringOverride = {
		it?.name
    }
}
```

## Display Value For

This can be used to set the text that is displayed in the options. Your custom class can also just override toString() to display in the dialog.

```kotlin
multiCheckBox<ListItem, List<ListItem>>(1) {
    displayValueFor = {
		it?.name
    }
}
```

## Example

### Kotlin

```kotlin
multiCheckBox<ListItem, List<ListItem>>(1) {
    dialogTitle = "Pick one or more"
    options = listOf(ListItem(id = 1, name = "Banana"),
                     ListItem(id = 2, name = "Orange"))
    value = listOf(ListItem(id = 1, name = "Banana"))
}
```

### Java

```java
List<BaseFormElement<?>> elements = new ArrayList<>();
FormPickerMultiCheckBoxElement<ListItem, List<ListItem>> multiCheckBox = new FormPickerMultiCheckBoxElement<>(Tag.MultiItems.ordinal());
multiCheckBox.setDialogTitle("Pick one or more");
multiCheckBox.setOptions(Arrays.asList(new ListItem(1L, "Banana"),
    new ListItem(2L, "Orange")));
multiCheckBox.setValue(Collections.singletonList(new ListItem(1L, "Banana")));
elements.add(multiCheckBox);
```
