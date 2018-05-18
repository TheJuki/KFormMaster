The Phone form element is used for a phone number value field with the phone keyboard layout.

## Example

### Kotlin
```kotlin
phone(1) {
    value = "(555) 555-5555"
}
```

### Java
```java
List<BaseFormElement<?>> elements = new ArrayList<>();
PhoneEditTextBuilder phone = new PhoneEditTextBuilder(1);
phone.setValue("(555) 555-5555");
elements.add(phone.build());
```