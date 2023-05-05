package com.jay.keyboard;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import java.lang.reflect.Method;


public class SecurityEditText extends AppCompatEditText {
    private IKeyboard iKeyboard;

    public SecurityEditText(Context context) {
        this(context, null);
    }

    public SecurityEditText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.editTextStyle);
    }

    public SecurityEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setKeyboard(IKeyboard iKeyboard) {
        this.iKeyboard = iKeyboard;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused) {
            hideSystemKeyboard();
            showSoftInput();
        } else {
            hideSoftKeyboard();
        }
    }

    @Override
    public boolean performClick() {
        if (this.isFocused()) {
            hideSystemKeyboard();
            showSoftInput();
        }
        return false;
    }

    private void hideSystemKeyboard() {
        InputMethodManager manager = (InputMethodManager) this.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager != null) {
            manager.hideSoftInputFromWindow(this.getWindowToken(), 0);
        }
    }

    private void showSoftInput() {
        if (iKeyboard != null) {
            iKeyboard.show();
        }
    }

    private void hideSoftKeyboard() {
        if (iKeyboard != null) {
            iKeyboard.hide();
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.isFocused()) {
            hideSystemKeyboard();
            showSoftInput();
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.isFocused()) {
            hideSoftKeyboard();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        if (hasWindowFocus && hasFocus()) {
            this.post(() -> {
                hideSystemKeyboard();
                showSoftInput();
            });
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (iKeyboard != null && iKeyboard.isShowing()) {
                iKeyboard.hide();
                return true;
            } else {
                return false;
            }
        }
        return super.onKeyUp(keyCode, event);
    }
}
