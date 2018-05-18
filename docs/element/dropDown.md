The DropDown form element is used for a single choice dialog.

## Example

### Kotlin
```kotlin
dropDown<ListItem>(1) {
    dialogTitle = "Select One"
    options = listOf(ListItem(id = 1, name = "Banana"), 
                     ListItem(id = 2, name = "Orange"))
    value = ListItem(id = 1, name = "Banana")
}
```

### Java
```java
DropDownBuilder<ListItem> dropDown = new DropDownBuilder<>(1);
dropDown.setDialogTitle("Select One");
dropDown.setOptions(Arrays.asList(new ListItem(1L, "Banana"),
    new ListItem(2L, "Orange")));
dropDown.setValue(new ListItem(1L, "Banana"));
elements.add(dropDown.build());
```