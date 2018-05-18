The Date form element is used for a date dialog.

## Example

### Kotlin
```kotlin
date(1) {
    dateValue = Date()
    dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.US)
}
```

### Java
```java
List<BaseFormElement<?>> elements = new ArrayList<>();
DateBuilder date = new DateBuilder(1);
date.setDateValue(new Date());
date.setDateFormat(new SimpleDateFormat("MM/dd/yyyy", Locale.US));
elements.add(date.build());
```