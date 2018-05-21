The DateTime form element is used for a date and then time dialog. The date dialog is displayed first and then the time dialog is displayed.

!!! info "Value"

    * You can set value directly by creating a new instance of FormPickerDateTimeElement.DateTimeHolder. However, dateValue and dateFormat will create the instance for you.
    * Call getTime() on value to get a Date object back.

## Date Value
A java.util.Date value.

## Date Format
A java.text.DateFormat value. By default this is SimpleDateFormat.getDateInstance(). It is advised to provide your own format.

## Example

### Kotlin
```kotlin
dateTime(1) {
    dateValue = Date()
    dateFormat = SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.US)
}
```

### Java
```java
List<BaseFormElement<?>> elements = new ArrayList<>();
DateTimeBuilder dateTime = new DateTimeBuilder(1);
dateTime.setDateValue(new Date());
dateTime.setDateFormat(new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.US));
elements.add(dateTime.build());
```