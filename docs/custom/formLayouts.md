# Form Layouts

Form Layouts override the default layouts used for all related form elements in the form.

## Example

### Kotlin
```kotlin
 formBuilder = form(binding.recyclerView, formLayouts = FormLayouts(
                text = R.layout.form_element_custom,
                textArea = R.layout.form_element_custom
        )) 
        {

        }
```

### Java
```java
FormLayouts formLayouts = new FormLayouts();

formLayouts.setText(R.layout.form_element_custom);
formLayouts.setTextArea(R.layout.form_element_custom);

formBuilder = new FormBuildHelper(this, this, findViewById(R.id.recyclerView), true, formLayouts);
```