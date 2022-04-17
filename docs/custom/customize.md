# Customize Form

### Form accent color change
If you want to change the colors, just override the colors in your **colors.xml** file:
```xml
<color name="colorFormMasterHeaderBackground">#DDDDDD</color>
<color name="colorFormMasterHeaderText">#000000</color>
<color name="colorFormMasterElementBackground">#FFFFFF</color>
<color name="colorFormMasterElementTextTitle">#222222</color>
<color name="colorFormMasterElementErrorTitle">#FF0000</color>
<color name="colorFormMasterElementHint">#a8a8a8</color>
<color name="colorFormMasterElementTextValue">#000000</color>
<color name="colorFormMasterElementTextView">#757575</color>
<color name="colorFormMasterElementButtonText">#42A5F5</color>
<color name="colorFormMasterElementFocusedTitle">#0277bd</color>
<color name="colorFormMasterElementTextDisabled">#757575</color>
<color name="colorFormMasterDivider">#DDDDDD</color>
<color name="colorFormMasterElementToken">#f5f5f5</color>
<color name="colorFormMasterElementRadioSelected">#42A5F5</color>
<color name="colorFormMasterElementRadioUnSelected">#FFFFFF</color>
```

### Form UI change
If you want to change how the forms look, just override a form_element XML in your project.

Just make sure to keep the ID name the same as it is in the library for the components.
```
android:id="@+id/formElementTitle"
android:id="@+id/formElementValue"
...
```