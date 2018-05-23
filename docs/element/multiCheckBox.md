The MultiCheckBox form element is used for a multiple choice dialog.

![Example](/images/MultiCheckBox1.PNG)

<img src="../../images/MultiCheckBox2.PNG" alt="Example" width="300px"/>

## Dialog Title
By default this is "Pick one or more".
Setting this will override the alert dialog title.
```kotlin
multiCheckBox<ListItem>(1) {
    dialogTitle = "Pick one or more"
}
```

## Example

### Kotlin
```kotlin
multiCheckBox<ListItem>(1) {
    dialogTitle = "Pick one or more"
    options = listOf(ListItem(id = 1, name = "Banana"), 
                     ListItem(id = 2, name = "Orange"))
    optionsSelected = listOf(ListItem(id = 1, name = "Banana"))
}
```

### Java
```java
List<BaseFormElement<?>> elements = new ArrayList<>();
MultiCheckBoxBuilder<ListItem> multiCheckBox = new MultiCheckBoxBuilder<>(Tag.MultiItems.ordinal());
multiCheckBox.setDialogTitle("Pick one or more");
multiCheckBox.setOptions(Arrays.asList(new ListItem(1L, "Banana"),
    new ListItem(2L, "Orange")));
multiCheckBox.setOptionsSelected(Collections.singletonList(new ListItem(1L, "Banana")));
elements.add(multiCheckBox.build());
```