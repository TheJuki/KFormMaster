package com.thejuki.kformmasterexample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.thejuki.kformmaster.helper.FormBuildHelper;
import com.thejuki.kformmaster.listener.OnFormElementValueChangedListener;
import com.thejuki.kformmaster.model.BaseFormElement;
import com.thejuki.kformmaster.model.FormCheckBoxElement;
import com.thejuki.kformmasterexample.item.ListItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kotlin.Unit;

/**
 * Form Listener Java Activity
 * <p>
 * Java version of FormListenerActivity
 * OnFormElementValueChangedListener is overridden at the activity level
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
            actionBar.setTitle(getString(R.string.form_with_listener));
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
        SwitchElement,
        SliderElement,
        CheckBoxElement,
    }

    private void setupForm() {
        formBuilder = new FormBuildHelper(this);
        formBuilder.attachRecyclerView(this, findViewById(R.id.recyclerView));

        List<BaseFormElement<?>> elements = new ArrayList<>();

        elements.add(new FormCheckBoxElement<String>().addValueObserver(x -> {
            System.out.print("hey");
            return Unit.INSTANCE;
        }));


        formBuilder.addFormElements(elements);
    }

    @Override
    public void onValueChanged(@NotNull BaseFormElement<?> formElement) {
        Toast.makeText(this,
                (formElement.getValue() != null) ? formElement.getValue().toString() :
                        (formElement.getOptionsSelected() != null) ?
                                formElement.getOptionsSelected().toString() : "",
                Toast.LENGTH_SHORT).show();
    }
}
