The MultiCheckBox form element is used for a multiple choice dialog.

## Example

### Kotlin
```kotlin
multiCheckBox<ListItem>(1) {
    dialogTitle = "Select Multiple"
    options = listOf(ListItem(id = 1, name = "Banana"), 
                     ListItem(id = 2, name = "Orange"))
    optionsSelected = listOf(ListItem(id = 1, name = "Banana"))
}
```

### Java
```java
List<BaseFormElement<?>> elements = new ArrayList<>();
MultiCheckBoxBuilder<ListItem> multiCheckBox = new MultiCheckBoxBuilder<>(Tag.MultiItems.ordinal());
multiCheckBox.setDialogTitle("Select Multiple");
multiCheckBox.setOptions(Arrays.asList(new ListItem(1L, "Banana"),
    new ListItem(2L, "Orange")));
multiCheckBox.setOptionsSelected(Collections.singletonList(new ListItem(1L, "Banana")));
elements.add(multiCheckBox.build());
```