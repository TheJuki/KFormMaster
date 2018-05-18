The Number element is used for a number value field with the number keyboard layout.

!!! note "NOTICE"

    The value is a String. It is up to you to convert the resulting value to a number if necessary.
```kotlin
val num: Int = newValue?.toInt()?: 0
```

## Numbers Only
By default this is false.
Setting this to true will only allow numbers to be entered in the value field.
```kotlin
number {
    numbersOnly = true
}
```

## Example

### Kotlin
```kotlin
number(1) {
    value = 1234
    numbersOnly = true
}
```

### Java
```java
List<BaseFormElement<?>> elements = new ArrayList<>();
NumberEditTextBuilder number = new NumberEditTextBuilder(1);
number.setValue(1234);
number.setNumbersOnly(true);
elements.add(number.build());
```