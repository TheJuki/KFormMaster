The Time form element is used for a time dialog.

!!! info "Value"

    * You can set value directly by creating a new instance of FormPickerTimeElement.TimeHolder. However, dateValue and dateFormat will create the instance for you.
    * Call getTime() on value to get a Date object back.

## Date Value
A java.util.Date value.

## Date Format
A java.text.DateFormat value. By default this is SimpleDateFormat.getDateInstance(). It is advised to provide your own format.

## Example

### Kotlin
```kotlin
time(1) {
    dateValue = Date()
    dateFormat = SimpleDateFormat("hh:mm a", Locale.US)
}
```

### Java
```java
List<BaseFormElement<?>> elements = new ArrayList<>();
TimeBuilder time = new TimeBuilder(1);
time.setDateValue(new Date());
time.setDateFormat(new SimpleDateFormat("hh:mm a", Locale.US));
elements.add(time.build());
```