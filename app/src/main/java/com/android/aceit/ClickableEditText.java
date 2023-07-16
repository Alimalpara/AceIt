package com.android.aceit;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class ClickableEditText extends androidx.appcompat.widget.AppCompatEditText {

    public ClickableEditText(Context context) {
        super(context);
    }

    public ClickableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClickableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }
}
