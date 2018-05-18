The Time form element is used for a time dialog.

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