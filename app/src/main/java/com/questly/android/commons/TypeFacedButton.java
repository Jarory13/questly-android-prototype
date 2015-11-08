package com.questly.android.commons;

import com.questly.android.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

public class TypeFacedButton extends Button {

    public TypeFacedButton(Context context) {
        this(context, null);
    }

    public TypeFacedButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TypeFacedButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (!isInEditMode()) {
            TypedArray a = getContext()
                    .obtainStyledAttributes(attrs, R.styleable.TypeFacedTextView);
            int fontIntValue = a.getInt(R.styleable.TypeFacedTextView_fontName,
                    FontType.BLANCH_CONDENSED.ordinal());
            a.recycle();

            FontType font = FontType.values()[fontIntValue];
            setTypeface(font);
        }
    }

    public void setTypeface(FontType font) {
        Typeface typeface = Typeface
                .createFromAsset(getContext().getAssets(), "fonts/" + font.getFileName());
        setTypeface(typeface);
    }

}
