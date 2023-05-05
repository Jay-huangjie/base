package com.jay.base;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import com.jay.base.databinding.ActivityMainBinding;
import com.jay.keyboard.EditUtils;
import com.jay.keyboard.KeyboardLayout;
import com.jay.keyboard.SecurityEditText;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        KeyboardLayout keyboardLayout = findViewById(R.id.keyBoard);
        SecurityEditText editText = findViewById(R.id.editText);
        EditUtils.disableShowInput(editText);
        editText.setKeyboard(keyboardLayout);
        keyboardLayout.bindEditText(editText);
    }
}