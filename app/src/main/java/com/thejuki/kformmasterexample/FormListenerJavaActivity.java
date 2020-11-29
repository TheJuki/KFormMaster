package com.thejuki.kformmasterexample;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.thejuki.kformmaster.helper.FormBuildHelper;
import com.thejuki.kformmaster.helper.FormLayouts;
import com.thejuki.kformmaster.listener.OnFormElementValueChangedListener;
import com.thejuki.kformmaster.model.BaseFormElement;
import com.thejuki.kformmaster.model.FormAutoCompleteElement;
import com.thejuki.kformmaster.model.FormButtonElement;
import com.thejuki.kformmaster.model.FormCheckBoxElement;
import com.thejuki.kformmaster.model.FormEmailEditTextElement;
import com.thejuki.kformmaster.model.FormHeader;
import com.thejuki.kformmaster.model.FormImageElement;
import com.thejuki.kformmaster.model.FormInlineDatePickerElement;
import com.thejuki.kformmaster.model.FormLabelElement;
import com.thejuki.kformmaster.model.FormMultiLineEditTextElement;
import com.thejuki.kformmaster.model.FormNumberEditTextElement;
import com.thejuki.kformmaster.model.FormPasswordEditTextElement;
import com.thejuki.kformmaster.model.FormPhoneEditTextElement;
import com.thejuki.kformmaster.model.FormPickerDateElement;
import com.thejuki.kformmaster.model.FormPickerDateTimeElement;
import com.thejuki.kformmaster.model.FormPickerDropDownElement;
import com.thejuki.kformmaster.model.FormPickerMultiCheckBoxElement;
import com.thejuki.kformmaster.model.FormPickerTimeElement;
import com.thejuki.kformmaster.model.FormProgressElement;
import com.thejuki.kformmaster.model.FormSegmentedElement;
import com.thejuki.kformmaster.model.FormSingleLineEditTextElement;
import com.thejuki.kformmaster.model.FormSliderElement;
import com.thejuki.kformmaster.model.FormSwitchElement;
import com.thejuki.kformmaster.model.FormTextViewElement;
import com.thejuki.kformmaster.model.FormTokenAutoCompleteElement;
import com.thejuki.kformmasterexample.adapter.ContactAutoCompleteAdapter;
import com.thejuki.kformmasterexample.adapter.EmailAutoCompleteAdapter;
import com.thejuki.kformmasterexample.databinding.ActivityFullscreenFormBinding;
import com.thejuki.kformmasterexample.item.ContactItem;
import com.thejuki.kformmasterexample.item.ListItem;

import org.jetbrains.annotations.NotNull;
import org.threeten.bp.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import kotlin.Unit;

/**
 * Form Listener Java Activity
 * <p>
 * Java version of FormListenerActivity
 * OnFormElementValueChangedListener is overridden at the activity level
 * </p>
 *
 * @author <strong>TheJuki</strong> <a href="https://github.com/TheJuki">GitHub</a>
 * @version 1.0
 */
public class FormListenerJavaActivity extends AppCompatActivity implements OnFormElementValueChangedListener {
    private ActivityFullscreenFormBinding binding;
    private FormBuildHelper formBuilder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFullscreenFormBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setupToolBar();

        setupForm();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupToolBar() {

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.form_with_listener_java));
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

    }

    private final List<ListItem> fruits = Arrays.asList(new ListItem(1L, "Banana"),
            new ListItem(2L, "Orange"),
            new ListItem(3L, "Mango"),
            new ListItem(4L, "Guava")
    );

    private enum Tag {
        Email,
        Phone,
        Location,
        Address,
        ZipCode,
        Date,
        Time,
        DateTime,
        InlineDatePicker,
        Password,
        SingleItem,
        MultiItems,
        AutoCompleteElement,
        AutoCompleteTokenElement,
        ButtonElement,
        TextViewElement,
        LabelElement,
        SwitchElement,
        SliderElement,
        ProgressElement,
        CheckBoxElement,
        SegmentedElement,
        ImageViewElement
    }

    private void setupForm() {
        FormLayouts formLayouts = new FormLayouts();

        // Uncomment to replace all text elements with the form_element_custom layout
        //formLayouts.setText(R.layout.form_element_custom);

        formBuilder = new FormBuildHelper(this, binding.recyclerView, true, formLayouts);

        List<BaseFormElement<?>> elements = new ArrayList<>();

        addEditTexts(elements);

        addDateAndTime(elements);

        addPickers(elements);

        addAutoComplete(elements);

        addMarkComplete(elements);

        formBuilder.addFormElements(elements);
    }

    private void addEditTexts(List<BaseFormElement<?>> elements) {
        FormImageElement imageView = new FormImageElement(Tag.ImageViewElement.ordinal());
        imageView.setOnSelectImage((file) -> {
            if (file != null) {
                Toast.makeText(this, file.getName(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error getting the image", Toast.LENGTH_LONG).show();
            }
            return Unit.INSTANCE;
        });
        elements.add(imageView);

        elements.add(new FormHeader(getString(R.string.PersonalInfo)));

        FormEmailEditTextElement email = new FormEmailEditTextElement(Tag.Email.ordinal());
        email.setTitle(getString(R.string.email));
        email.setHint(getString(R.string.email_hint));
        email.setValidityCheck(() -> {
            if (email.getValue() != null) {
                return android.util.Patterns.EMAIL_ADDRESS.matcher(email.getValue()).matches();
            } else {
                return false;
            }
        });
        elements.add(email);

        FormPasswordEditTextElement password = new FormPasswordEditTextElement(Tag.Password.ordinal());
        password.setTitle(getString(R.string.password));
        elements.add(password);

        FormPhoneEditTextElement phone = new FormPhoneEditTextElement(Tag.Phone.ordinal());
        phone.setTitle(getString(R.string.Phone));
        phone.setValue("+8801712345678");
        elements.add(phone);

        elements.add(new FormHeader(getString(R.string.FamilyInfo)));

        FormSingleLineEditTextElement text = new FormSingleLineEditTextElement(Tag.Location.ordinal());
        text.setTitle(getString(R.string.Location));
        text.setValue("Dhaka");
        elements.add(text);

        FormMultiLineEditTextElement textArea = new FormMultiLineEditTextElement(Tag.Address.ordinal());
        textArea.setTitle(getString(R.string.Address));
        textArea.setValue("");
        elements.add(textArea);

        FormNumberEditTextElement number = new FormNumberEditTextElement(Tag.ZipCode.ordinal());
        number.setTitle(getString(R.string.ZipCode));
        number.setValue("1000");
        elements.add(number);
    }

    private void addDateAndTime(List<BaseFormElement<?>> elements) {
        elements.add(new FormHeader(getString(R.string.Schedule)));

        FormPickerDateElement date = new FormPickerDateElement(Tag.Date.ordinal());
        date.setTitle(getString(R.string.Date));
        date.setDateValue(new Date());
        date.setDateFormat(new SimpleDateFormat("MM/dd/yyyy", Locale.US));
        elements.add(date);

        FormPickerTimeElement time = new FormPickerTimeElement(Tag.Time.ordinal());
        time.setTitle(getString(R.string.Time));
        time.setDateValue(new Date());
        time.setDateFormat(new SimpleDateFormat("hh:mm a", Locale.US));
        elements.add(time);

        FormPickerDateTimeElement dateTime = new FormPickerDateTimeElement(Tag.DateTime.ordinal());
        dateTime.setTitle(getString(R.string.DateTime));
        dateTime.setDateValue(new Date());
        dateTime.setDateFormat(new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.US));
        elements.add(dateTime);

        FormInlineDatePickerElement inlineDatePicker = new FormInlineDatePickerElement(Tag.InlineDatePicker.ordinal());
        inlineDatePicker.setTitle(getString(R.string.InlineDatePicker));
        inlineDatePicker.setValue(org.threeten.bp.LocalDateTime.now());
        inlineDatePicker.setDateTimeFormatter(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a", Locale.US));
        elements.add(inlineDatePicker);
    }

    private void addPickers(List<BaseFormElement<?>> elements) {
        elements.add(new FormHeader(getString(R.string.PreferredItems)));

        FormPickerDropDownElement<ListItem> dropDown = new FormPickerDropDownElement<>(Tag.SingleItem.ordinal());
        dropDown.setTitle(getString(R.string.SingleItem));
        dropDown.setDialogTitle(getString(R.string.SingleItem));
        dropDown.setOptions(fruits);
        dropDown.setValue(new ListItem(1L, "Banana"));
        elements.add(dropDown);

        FormPickerMultiCheckBoxElement<List<ListItem>> multiCheckBox = new FormPickerMultiCheckBoxElement<>(Tag.MultiItems.ordinal());
        multiCheckBox.setTitle(getString(R.string.MultiItems));
        multiCheckBox.setDialogTitle(getString(R.string.MultiItems));
        multiCheckBox.setOptions(fruits);
        multiCheckBox.setValue(Collections.singletonList(new ListItem(1L, "Banana")));
        elements.add(multiCheckBox);
    }

    private void addAutoComplete(List<BaseFormElement<?>> elements) {
        FormAutoCompleteElement<ContactItem> autoComplete = new FormAutoCompleteElement<>(Tag.AutoCompleteElement.ordinal());
        autoComplete.setTitle(getString(R.string.AutoComplete));
        autoComplete.setArrayAdapter(new ContactAutoCompleteAdapter(this,
                android.R.layout.simple_list_item_1,
                new ArrayList<>(Collections.singletonList(new ContactItem(1L, "", "Try \"Apple May\"")))));
        autoComplete.setDropdownWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        autoComplete.setValue(new ContactItem(1L, "John Smith", "John Smith (Tester)"));
        elements.add(autoComplete);

        FormTokenAutoCompleteElement<List<ContactItem>> autoCompleteToken = new FormTokenAutoCompleteElement<>(Tag.AutoCompleteTokenElement.ordinal());
        autoCompleteToken.setTitle(getString(R.string.AutoCompleteToken));
        autoCompleteToken.setArrayAdapter(new EmailAutoCompleteAdapter(this,
                android.R.layout.simple_list_item_1));
        autoCompleteToken.setDropdownWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        autoCompleteToken.setHint("Try \"Apple May\"");
        elements.add(autoCompleteToken);

        FormTextViewElement textView = new FormTextViewElement(Tag.TextViewElement.ordinal());
        textView.setTitle(getString(R.string.TextView));
        textView.setValue("This is readonly!");
        elements.add(textView);

        FormLabelElement label = new FormLabelElement(Tag.LabelElement.ordinal());
        label.setTitle(getString(R.string.Label));
        elements.add(label);
    }

    private void addMarkComplete(List<BaseFormElement<?>> elements) {
        elements.add(new FormHeader(getString(R.string.MarkComplete)));

        FormSwitchElement<String> switchElement = new FormSwitchElement<>(Tag.SwitchElement.ordinal());
        switchElement.setTitle(getString(R.string.Switch));
        switchElement.setValue("Yes");
        switchElement.setOnValue("Yes");
        switchElement.setOffValue("No");
        elements.add(switchElement);

        FormSliderElement slider = new FormSliderElement(Tag.SliderElement.ordinal());
        slider.setTitle(getString(R.string.Slider));
        slider.setValue(50);
        slider.setMin(0);
        slider.setMax(100);
        slider.setSteps(20);
        elements.add(slider);

        FormCheckBoxElement<Boolean> checkBox = new FormCheckBoxElement<>(Tag.CheckBoxElement.ordinal());
        checkBox.setTitle(getString(R.string.CheckBox));
        checkBox.setValue(true);
        checkBox.setCheckedValue(true);
        checkBox.setUnCheckedValue(false);
        elements.add(checkBox);

        FormProgressElement progress = new FormProgressElement(Tag.ProgressElement.ordinal());
        progress.setTitle(getString(R.string.Progress));
        progress.setIndeterminate(false);
        progress.setProgress(25);
        progress.setSecondaryProgress(50);
        progress.setMin(0);
        progress.setMax(100);
        elements.add(progress);

        FormSegmentedElement<ListItem> segmented = new FormSegmentedElement<>(Tag.SegmentedElement.ordinal());
        segmented.setTitle(getString(R.string.Segmented));
        segmented.setOptions(fruits);
        segmented.setValue(new ListItem(1L, "Banana"));
        elements.add(segmented);

        FormButtonElement button = new FormButtonElement(Tag.ButtonElement.ordinal());
        button.setValue(getString(R.string.Button));
        button.getValueObservers().add((newValue, element) -> {
            clearDate();
            return Unit.INSTANCE;
        });
        elements.add(button);
    }

    private void clearDate() {
        AlertDialog confirmAlert = new AlertDialog.Builder(this).create();
        confirmAlert.setTitle(this.getString(R.string.Confirm));
        confirmAlert.setButton(AlertDialog.BUTTON_POSITIVE, this.getString(android.R.string.ok), (dialog, which) -> {
            // Could be used to clear another field:
            FormPickerDateElement dateToDeleteElement = formBuilder.getFormElement(Tag.Date.ordinal());
            // Display current date
            if (dateToDeleteElement.getValue() != null) {
                Date dateToDelete = dateToDeleteElement.getValue().getTime();

                if (dateToDelete != null) {
                    Toast.makeText(this, dateToDelete.toString(), Toast.LENGTH_SHORT).show();
                    dateToDeleteElement.clear();
                    formBuilder.onValueChanged(dateToDeleteElement);
                }
            }
        });
        confirmAlert.setButton(AlertDialog.BUTTON_NEGATIVE, this.getString(android.R.string.cancel), (dialog, which) -> {
        });
        confirmAlert.show();
    }

    @Override
    public void onValueChanged(@NotNull BaseFormElement<?> formElement) {
        Toast.makeText(this, formElement.getValueAsString(), Toast.LENGTH_SHORT).show();
    }
}
