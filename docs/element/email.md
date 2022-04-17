# Email

The Email form element is used for an email address value field with the email keyboard layout.

![Example](../images/Email.PNG)

## Example

### Kotlin

```kotlin
email(1) {
    value = "example@example.com"
}
```

### Java

```java
List<BaseFormElement<?>> elements = new ArrayList<>();
FormEmailEditTextElement email = new FormEmailEditTextElement(1);
email.setValue("example@example.com");
elements.add(email);
```
