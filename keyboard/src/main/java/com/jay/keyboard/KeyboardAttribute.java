package com.jay.keyboard;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;

class KeyboardAttribute {
    public ColorStateList chooserSelectedColor;
    public ColorStateList chooserUnselectedColor;
    public Drawable chooserBackground;
    public Drawable keyboardBackground;
    //是否需要预览界面
    public boolean isKeyPreview;
    //是否需要符号切换
    public boolean isSymbol;

    public KeyboardAttribute(ColorStateList chooserSelectedColor, ColorStateList chooserUnselectedColor,
                             Drawable chooserBackground, Drawable keyboardBackground,
                             boolean isKeyPreview, boolean isSymbol) {
        this.chooserSelectedColor = chooserSelectedColor;
        this.chooserUnselectedColor = chooserUnselectedColor;
        this.chooserBackground = chooserBackground;
        this.keyboardBackground = keyboardBackground;
        this.isKeyPreview = isKeyPreview;
        this.isSymbol = isSymbol;
    }
}
