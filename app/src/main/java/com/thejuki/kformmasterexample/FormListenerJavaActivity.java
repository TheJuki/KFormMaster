package com.thejuki.kformmasterexample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import com.thejuki.kformmaster.helper.AutoCompleteBuilder;
import com.thejuki.kformmaster.helper.AutoCompleteTokenBuilder;
import com.thejuki.kformmaster.helper.ButtonBuilder;
import com.thejuki.kformmaster.helper.CheckBoxBuilder;
import com.thejuki.kformmaster.helper.DateBuilder;
import com.thejuki.kformmaster.helper.DateTimeBuilder;
import com.thejuki.kformmaster.helper.DropDownBuilder;
import com.thejuki.kformmaster.helper.EmailEditTextBuilder;
import com.thejuki.kformmaster.helper.FormBuildHelper;
import com.thejuki.kformmaster.helper.FormLayouts;
import com.thejuki.kformmaster.helper.HeaderBuilder;
import com.thejuki.kformmaster.helper.LabelBuilder;
import com.thejuki.kformmaster.helper.MultiCheckBoxBuilder;
import com.thejuki.kformmaster.helper.MultiLineEditTextBuilder;
import com.thejuki.kformmaster.helper.NumberEditTextBuilder;
import com.thejuki.kformmaster.helper.PasswordEditTextBuilder;
import com.thejuki.kformmaster.helper.PhoneEditTextBuilder;
import com.thejuki.kformmaster.helper.SingleLineEditTextBuilder;
import com.thejuki.kformmaster.helper.SliderBuilder;
import com.thejuki.kformmaster.helper.SwitchBuilder;
import com.thejuki.kformmaster.helper.TextViewBuilder;
import com.thejuki.kformmaster.helper.TimeBuilder;
import com.thejuki.kformmaster.listener.OnFormElementValueChangedListener;
import com.thejuki.kformmaster.model.BaseFormElement;
import com.thejuki.kformmaster.model.FormPickerDateElement;
import com.thejuki.kformmasterexample.adapter.ContactAutoCompleteAdapter;
import com.thejuki.kformmasterexample.adapter.EmailAutoCompleteAdapter;
import com.thejuki.kformmasterexample.item.ContactItem;
import com.thejuki.kformmasterexample.item.ListItem;

import org.jetbrains.annotations.NotNull;

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
    private FormBuildHelper formBuilder = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_form);

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

    private List<ListItem> fruits = Arrays.asList(new ListItem(1L, "Banana"),
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
        CheckBoxElement,
    }

    private void setupForm() {
        FormLayouts formLayouts = new FormLayouts();

        // Uncomment to replace all text elements with the form_element_custom layout
        //formLayouts.setText(R.layout.form_element_custom);

        formBuilder = new FormBuildHelper(this, this, findViewById(R.id.recyclerView), true, formLayouts);

        List<BaseFormElement<?>> elements = new ArrayList<>();

        addEditTexts(elements);

        addDateAndTime(elements);

        addPickers(elements);

        addAutoComplete(elements);

        addMarkComplete(elements);

        formBuilder.addFormElements(elements);
    }

    private void addEditTexts(List<BaseFormElement<?>> elements) {
        elements.add(new HeaderBuilder(getString(R.string.PersonalInfo)).build());

        EmailEditTextBuilder email = new EmailEditTextBuilder(Tag.Email.ordinal());
        email.setTitle(getString(R.string.email));
        email.setHint(getString(R.string.email_hint));
        elements.add(email.build());

        PasswordEditTextBuilder password = new PasswordEditTextBuilder(Tag.Password.ordinal());
        password.setTitle(getString(R.string.password));
        elements.add(password.build());

        PhoneEditTextBuilder phone = new PhoneEditTextBuilder(Tag.Phone.ordinal());
        phone.setTitle(getString(R.string.Phone));
        phone.setValue("+8801712345678");
        elements.add(phone.build());

        elements.add(new HeaderBuilder(getString(R.string.FamilyInfo)).build());

        SingleLineEditTextBuilder text = new SingleLineEditTextBuilder(Tag.Location.ordinal());
        text.setTitle(getString(R.string.Location));
        text.setValue("Dhaka");
        elements.add(text.build());

        MultiLineEditTextBuilder textArea = new MultiLineEditTextBuilder(Tag.Address.ordinal());
        textArea.setTitle(getString(R.string.Address));
        textArea.setValue("");
        elements.add(textArea.build());

        NumberEditTextBuilder number = new NumberEditTextBuilder(Tag.ZipCode.ordinal());
        number.setTitle(getString(R.string.ZipCode));
        number.setValue("1000");
        elements.add(number.build());
    }

    private void addDateAndTime(List<BaseFormElement<?>> elements) {
        elements.add(new HeaderBuilder(getString(R.string.Schedule)).build());

        DateBuilder date = new DateBuilder(Tag.Date.ordinal());
        date.setTitle(getString(R.string.Date));
        date.setDateValue(new Date());
        date.setDateFormat(new SimpleDateFormat("MM/dd/yyyy", Locale.US));
        elements.add(date.build());

        TimeBuilder time = new TimeBuilder(Tag.Time.ordinal());
        time.setTitle(getString(R.string.Time));
        time.setDateValue(new Date());
        time.setDateFormat(new SimpleDateFormat("hh:mm a", Locale.US));
        elements.add(time.build());

        DateTimeBuilder dateTime = new DateTimeBuilder(Tag.DateTime.ordinal());
        dateTime.setTitle(getString(R.string.DateTime));
        dateTime.setDateValue(new Date());
        dateTime.setDateFormat(new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.US));
        elements.add(dateTime.build());
    }

    private void addPickers(List<BaseFormElement<?>> elements) {
        elements.add(new HeaderBuilder(getString(R.string.PreferredItems)).build());

        DropDownBuilder<ListItem> dropDown = new DropDownBuilder<>();
        dropDown.setTitle(getString(R.string.SingleItem));
        dropDown.setDialogTitle(getString(R.string.SingleItem));
        dropDown.setOptions(fruits);
        dropDown.setValue(new ListItem(1L, "Banana"));
        elements.add(dropDown.build());

        MultiCheckBoxBuilder<List<ListItem>> multiCheckBox = new MultiCheckBoxBuilder<>(Tag.MultiItems.ordinal());
        multiCheckBox.setTitle(getString(R.string.MultiItems));
        multiCheckBox.setDialogTitle(getString(R.string.MultiItems));
        multiCheckBox.setOptions(fruits);
        multiCheckBox.setValue(Collections.singletonList(new ListItem(1L, "Banana")));
        elements.add(multiCheckBox.build());
    }

    private void addAutoComplete(List<BaseFormElement<?>> elements) {
        AutoCompleteBuilder<ContactItem> autoComplete = new AutoCompleteBuilder<>(Tag.AutoCompleteElement.ordinal());
        autoComplete.setTitle(getString(R.string.AutoComplete));
        autoComplete.setArrayAdapter(new ContactAutoCompleteAdapter(this,
                android.R.layout.simple_list_item_1,
                new ArrayList<>(Collections.singletonList(new ContactItem(1L, "", "Try \"Apple May\"")))));
        autoComplete.setDropdownWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        autoComplete.setValue(new ContactItem(1L, "John Smith", "John Smith (Tester)"));
        elements.add(autoComplete.build());

        AutoCompleteTokenBuilder<List<ContactItem>> autoCompleteToken = new AutoCompleteTokenBuilder<>(Tag.AutoCompleteTokenElement.ordinal());
        autoCompleteToken.setTitle(getString(R.string.AutoCompleteToken));
        autoCompleteToken.setArrayAdapter(new EmailAutoCompleteAdapter(this,
                android.R.layout.simple_list_item_1));
        autoCompleteToken.setDropdownWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        autoCompleteToken.setHint("Try \"Apple May\"");
        elements.add(autoCompleteToken.build());

        TextViewBuilder textView = new TextViewBuilder(Tag.TextViewElement.ordinal());
        textView.setTitle(getString(R.string.TextView));
        textView.setValue("This is readonly!");
        elements.add(textView.build());

        LabelBuilder label = new LabelBuilder(Tag.LabelElement.ordinal());
        label.setTitle(getString(R.string.Label));
        elements.add(label.build());
    }

    private void addMarkComplete(List<BaseFormElement<?>> elements) {
        elements.add(new HeaderBuilder(getString(R.string.MarkComplete)).build());

        SwitchBuilder<String> switchElement = new SwitchBuilder<>(Tag.SwitchElement.ordinal());
        switchElement.setTitle(getString(R.string.Switch));
        switchElement.setValue("Yes");
        switchElement.setOnValue("Yes");
        switchElement.setOffValue("No");
        elements.add(switchElement.build());

        SliderBuilder slider = new SliderBuilder(Tag.SliderElement.ordinal());
        slider.setTitle(getString(R.string.Slider));
        slider.setValue(50);
        slider.setMin(0);
        slider.setMax(100);
        slider.setSteps(20);
        elements.add(slider.build());

        CheckBoxBuilder<Boolean> checkBox = new CheckBoxBuilder<>(Tag.CheckBoxElement.ordinal());
        checkBox.setTitle(getString(R.string.CheckBox));
        checkBox.setValue(true);
        checkBox.setCheckedValue(true);
        checkBox.setUnCheckedValue(false);
        elements.add(checkBox.build());

        ButtonBuilder button = new ButtonBuilder(Tag.ButtonElement.ordinal());
        button.setValue(getString(R.string.Button));
        button.getValueObservers().add((newValue, element) -> {
            clearDate();
            return Unit.INSTANCE;
        });
        elements.add(button.build());
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
