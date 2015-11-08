package com.questly.android.commons;

import com.questly.android.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class TypeFacedTextView extends TextView {

    public TypeFacedTextView(Context context) {
        this(context, null);
    }

    public TypeFacedTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TypeFacedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (!isInEditMode()) {
            TypedArray styledAttrs = getContext()
                    .obtainStyledAttributes(attrs, R.styleable.TypeFacedTextView);
            int fontIntValue = styledAttrs.getInt(R.styleable.TypeFacedTextView_fontName,
                    FontType.BLANCH_CONDENSED.ordinal());
            styledAttrs.recycle();

            FontType font = FontType.values()[fontIntValue];
            setTypeface(font);
        }
    }

    public void setTypeface(FontType font) {
        Typeface typeface = TypeFacedHelper
                .get(getContext(), "fonts/" + font.getFileName());
        setTypeface(typeface);
    }
}
