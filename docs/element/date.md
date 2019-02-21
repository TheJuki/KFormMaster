The Date form element is used for a date dialog.

![Example](../../images/Date1.PNG)

<img src="../../images/Date2.PNG" alt="Example" width="300px"/>

!!! info "Value"

    * You can set value directly by creating a new instance of FormPickerDateElement.DateHolder. However, dateValue and dateFormat will create the instance for you.
    * Call getTime() on value to get a Date object back.

## Date Value

A java.util.Date value.

## Date Format

A java.text.DateFormat value. By default this is SimpleDateFormat.getDateInstance(). It is advised to provide your own format.

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
FormPickerDateElement date = new FormPickerDateElement(1);
date.setDateValue(new Date());
date.setDateFormat(new SimpleDateFormat("MM/dd/yyyy", Locale.US));
elements.add(date);
```
