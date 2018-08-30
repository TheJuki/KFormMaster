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

## Tint Color
By default this is the value of R.color.colorFormMasterElementRadioSelected.
Setting this will set the tint color for each radio button.
```kotlin
segmented<ListItem>(1) {
    tintColor = Color.BLUE
}
```

## Margin (Stroke)
By default this is the value of R.dimen.elementRadioStrokeBorder.
Setting this will set the margin (stroke) for each radio button.
```kotlin
segmented<ListItem>(1) {
    marginDp = 5
}
```

## Unchecked Tint Color
By default this is the value of R.color.colorFormMasterElementRadioUnSelected.
Setting this will set the unchecked tint color for each radio button.
```kotlin
segmented<ListItem>(1) {
    unCheckedTintColor = Color.WHITE
}
```

## Checked Text Color
By default this is the value of R.color.colorFormMasterElementRadioSelected.
Setting this will set the checked text color for each radio button.
```kotlin
segmented<ListItem>(1) {
    checkedTextColor = Color.WHITE
}
```

## Corner Radius
By default this is the value of R.dimen.elementRadioCornerRadius.
Setting this will set the corner radius for each radio button.
```kotlin
segmented<ListItem>(1) {
    cornerRadius = 5f
}
```

## Text Size
By default this is the value of R.dimen.elementTextValueSize.
Setting this will set the text size (In SP) for each radio button.
```kotlin
segmented<ListItem>(1) {
    textSize = 12f
}
```

## Padding
By default this is the value of R.dimen.elementRadioPadding.
Setting this will set the padding for each radio button.
```kotlin
segmented<ListItem>(1) {
    padding = 5
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