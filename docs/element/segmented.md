The Segmented form element is used for a radio group.

![Example](/images/Segmented.PNG)

!!! note "NOTICE"

    The ListItem class is provided in the example app. Your custom class just needs to override toString() to display in the segmented group.

## Horizontal
By default this is true.
Setting this to false will stack the radio buttons vertically.
```kotlin
segmented<ListItem>(1) {
    horizontal = false
}
```

## Fill Space
By default this is false.
Setting this to true will fill the whole width.
```kotlin
segmented<ListItem>(1) {
    fillSpace = true
}
```

## Example

### Kotlin
```kotlin
segmented<ListItem>(1) {
    options = listOf(ListItem(id = 1, name = "Banana"), 
                     ListItem(id = 2, name = "Orange"))
    value = ListItem(id = 1, name = "Banana")
}
```

### Java
```java
SegmentedBuilder<ListItem> segmented = new SegmentedBuilder<>(1);
segmented.setOptions(Arrays.asList(new ListItem(1L, "Banana"),
    new ListItem(2L, "Orange")));
segmented.setValue(new ListItem(1L, "Banana"));
elements.add(segmented.build());
```