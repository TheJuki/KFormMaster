The DateTime form element is used for a date and then time dialog. The date dialog is displayed first and then the time dialog is displayed.

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