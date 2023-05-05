package com.jay.keyboard;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jay.keyboard.databinding.KeyboardLayoutBinding;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * created by hj on 2023/5/4.
 */
public class KeyboardLayout extends FrameLayout implements KeyboardView.OnKeyboardActionListener, IKeyboard {

    private static final int ORDER_NUMBER = 0;
    private static final int ORDER_SYMBOL = 1;
    private static final int ORDER_LETTER = 2;

    private KeyboardLayoutBinding mBinding;
    private Keyboard mLetterKeyboard;
    private Keyboard mSymbolKeyboard;
    private Keyboard mNumberKeyboard;
    private int mCurrentOrder;
    private SparseArray<Keyboard> mOrderToKeyboard;
    private ArrayList<String> mNumberPool;

    private ColorStateList mSelectedTextColor = ColorStateList.valueOf(Color.BLUE);
    private ColorStateList mUnSelectedTextColor = ColorStateList.valueOf(Color.BLACK);

    private boolean isUpper = false;

    private WeakReference<EditText> mTargetEditText;
    private KeyboardAttribute attribute;
    private Context mContext;

    public KeyboardLayout(@NonNull Context context) {
        this(context, null);
    }

    public KeyboardLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KeyboardLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        this.mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.KeyboardLayout);
        ColorStateList chooserSelectedColor = a.getColorStateList(R.styleable.KeyboardLayout_chooserSelectedColor);
        ColorStateList chooserUnselectedColor = a.getColorStateList(R.styleable.KeyboardLayout_chooserUnselectedColor);
        Drawable chooserBackground = a.getDrawable(R.styleable.KeyboardLayout_chooserBackground);
        Drawable keyboardBackground = a.getDrawable(R.styleable.KeyboardLayout_keyboardBackground);
        boolean isKeyPreview = a.getBoolean(R.styleable.KeyboardLayout_keyPreview, false);
        boolean isSymbol = a.getBoolean(R.styleable.KeyboardLayout_isSymbol, false);
        boolean isNumberRandom = a.getBoolean(R.styleable.KeyboardLayout_isNumberRandom, false);
        attribute = new KeyboardAttribute(chooserSelectedColor, chooserUnselectedColor,
                chooserBackground, keyboardBackground, isKeyPreview, isSymbol,isNumberRandom);
        a.recycle();
        mOrderToKeyboard = new SparseArray<>();
        mNumberPool = new ArrayList<>();
        mNumberPool.add("48#0");
        mNumberPool.add("49#1");
        mNumberPool.add("50#2");
        mNumberPool.add("51#3");
        mNumberPool.add("52#4");
        mNumberPool.add("53#5");
        mNumberPool.add("54#6");
        mNumberPool.add("55#7");
        mNumberPool.add("56#8");
        mNumberPool.add("57#9");
        onCreate();
    }


    private void onCreate() {
        mBinding = KeyboardLayoutBinding.inflate(LayoutInflater.from(getContext()));
        addView(mBinding.getRoot());
    }


    public void bindEditText(EditText editText) {
        mTargetEditText = new WeakReference<>(editText);
        initKeyboards();
        initKeyboardChooser();
    }


    private void initKeyboards() {
        if (attribute.keyboardBackground != null) {
            mBinding.keyboardView.setBackground(attribute.keyboardBackground);
        }
        if (attribute.chooserBackground != null) {
            mBinding.keyboardChooser.setBackground(attribute.chooserBackground);
        }
        if (attribute.chooserSelectedColor != null) {
            mSelectedTextColor = attribute.chooserSelectedColor;
        }
        if (attribute.chooserUnselectedColor != null) {
            mUnSelectedTextColor = attribute.chooserUnselectedColor;
        }
        if (attribute.isKeyPreview) {
            mBinding.keyboardView.setPreviewEnabled(true);
        } else {
            mBinding.keyboardView.setPreviewEnabled(false);
        }
        mBinding.keyboardView.setOnKeyboardActionListener(this);
        if (isPortrait()) {
            mLetterKeyboard = new Keyboard(getContext(), R.xml.gs_keyboard_english);
            mSymbolKeyboard = new Keyboard(getContext(), R.xml.gs_keyboard_symbols_shift);
            mNumberKeyboard = new Keyboard(getContext(), R.xml.gs_keyboard_number);
        } else {
            mLetterKeyboard = new Keyboard(getContext(), R.xml.gs_keyboard_english_land);
            mSymbolKeyboard = new Keyboard(getContext(), R.xml.gs_keyboard_symbols_shift_land);
            mNumberKeyboard = new Keyboard(getContext(), R.xml.gs_keyboard_number_land);
        }
        if (attribute.isNumberRandom) {
            randomNumbers();
        }
        mOrderToKeyboard.put(ORDER_NUMBER, mNumberKeyboard);
        mOrderToKeyboard.put(ORDER_SYMBOL, mSymbolKeyboard);
        mOrderToKeyboard.put(ORDER_LETTER, mLetterKeyboard);
        mCurrentOrder = ORDER_LETTER;
        mBinding.tvSymbol.setVisibility(attribute.isSymbol ? VISIBLE : GONE);
        onCurrentKeyboardChange();
    }

    private void initKeyboardChooser() {
        mBinding.tvNumber.setOnClickListener(v -> {
            mCurrentOrder = ORDER_NUMBER;
            onCurrentKeyboardChange();
        });
        mBinding.tvSymbol.setOnClickListener(v -> {
            mCurrentOrder = ORDER_SYMBOL;
            onCurrentKeyboardChange();
        });

        mBinding.tvLetter.setOnClickListener(v -> {
            mCurrentOrder = ORDER_LETTER;
            onCurrentKeyboardChange();
        });
    }

    private void onCurrentKeyboardChange() {
        if (mCurrentOrder == ORDER_NUMBER && attribute.isNumberRandom) {
            randomNumbers();
        }
        mBinding.keyboardView.setKeyboard(mOrderToKeyboard.get(mCurrentOrder));
        switch (mCurrentOrder) {
            case ORDER_NUMBER:
                mBinding.tvNumber.setTextColor(mSelectedTextColor);
                mBinding.tvSymbol.setTextColor(mUnSelectedTextColor);
                mBinding.tvLetter.setTextColor(mUnSelectedTextColor);
                break;
            case ORDER_SYMBOL:
                mBinding.tvNumber.setTextColor(mUnSelectedTextColor);
                mBinding.tvSymbol.setTextColor(mSelectedTextColor);
                mBinding.tvLetter.setTextColor(mUnSelectedTextColor);
                break;
            case ORDER_LETTER:
                mBinding.tvNumber.setTextColor(mUnSelectedTextColor);
                mBinding.tvSymbol.setTextColor(mUnSelectedTextColor);
                mBinding.tvLetter.setTextColor(mSelectedTextColor);
                break;
            default:
                throw new IllegalStateException(getContext().getString(R.string.exception_invalid_keyboard));
        }
    }

    private boolean isPortrait() {
        return getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * 键盘数字随机切换
     */
    private void randomNumbers() {
        if (mNumberKeyboard != null) {
            ArrayList<String> source = new ArrayList<>(mNumberPool);
            List<Keyboard.Key> keys = mNumberKeyboard.getKeys();
            for (Keyboard.Key key : keys) {
                if (key.label != null && isNumber(key.label.toString())) {
                    int number = new Random().nextInt(source.size());
                    String[] text = source.get(number).split("#");
                    key.label = text[1];
                    key.codes[0] = Integer.valueOf(text[0], 10);
                    source.remove(number);
                }
            }
        }
    }

    private boolean isNumber(String str) {
        String numStr = getContext().getString(R.string.zeroToNine);
        return numStr.contains(str.toLowerCase());
    }

    /**
     * 键盘大小写切换
     */
    private void changeKey() {
        if (mLetterKeyboard != null) {
            List<Keyboard.Key> keys = mLetterKeyboard.getKeys();
            if (isUpper) {
                isUpper = false;
                for (Keyboard.Key key : keys) {
                    if (key.label != null && isLetter(key.label.toString())) {
                        key.label = key.label.toString().toLowerCase();
                        key.codes[0] = key.codes[0] + 32;
                    }
                    if (key.codes[0] == -1) {
                        key.icon = getContext().getResources().getDrawable(
                                R.drawable.keyboard_shift);
                    }
                }
            } else {// 小写切换大写
                isUpper = true;
                for (Keyboard.Key key : keys) {
                    if (key.label != null && isLetter(key.label.toString())) {
                        key.label = key.label.toString().toUpperCase();
                        key.codes[0] = key.codes[0] - 32;
                    }
                    if (key.codes[0] == -1) {
                        key.icon = getContext().getResources().getDrawable(
                                R.drawable.keyboard_shift_c);
                    }
                }
            }
        }
    }

    private boolean isLetter(String str) {
        String letterStr = getContext().getString(R.string.aToz);
        return letterStr.contains(str.toLowerCase());
    }

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        Editable editable = mTargetEditText.get().getText();
        int start = mTargetEditText.get().getSelectionStart();
        if (primaryCode == Keyboard.KEYCODE_CANCEL) {
            hideKeyboard();
        } else if (primaryCode == Keyboard.KEYCODE_DELETE) {
            if (editable != null && editable.length() > 0) {
                if (start > 0) {
                    editable.delete(start - 1, start);
                }
            }
        } else if (primaryCode == Keyboard.KEYCODE_SHIFT) {
            changeKey();
            mCurrentOrder = ORDER_LETTER;
            onCurrentKeyboardChange();
        } else {
            editable.insert(start, Character.toString((char) primaryCode));
        }
    }

    private void hideKeyboard() {
        setVisibility(GONE);
    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {
    }

    @Override
    public void swipeRight() {
    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }


    @Override
    public void show() {
        setVisibility(VISIBLE);
    }

    @Override
    public void hide() {
        setVisibility(GONE);
    }

    @Override
    public boolean isShowing() {
        return getVisibility() == VISIBLE;
    }
}
