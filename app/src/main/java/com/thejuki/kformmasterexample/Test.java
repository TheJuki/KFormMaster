package com.thejuki.kformmasterexample;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.thejuki.kformmaster.helper.FormBuildHelper;

/**
 * Created by justin.kirk on 2/23/2018.
 */

public class Test extends Activity {
    FormBuildHelper thing = new FormBuildHelper(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thing.isValidForm();
    }
}
