package com.questly.android.commons;

/**
 * Stores all font file names. Enums type can renamed but the values should not be editted.
 */
public enum FontType {

    BLANCH_CAPS("BLANCH_CAPS.otf"),
    BLANCH_CAPS_INLINE("BLANCH_CAPS_INLINE.otf"),
    BLANCH_CAPS_LIGHT("BLANCH_CAPS_LIGHT.otf"),
    BLANCH_CONDENSED("BLANCH_CONDENSED.otf"),
    BLANCH_CONDENSED_INLINE("BLANCH_CONDENSED_INLINE.otf"),
    BLANCH_CONDENSED_LIGHT("BLANCH_CONDENSED_LIGHT.otf");

    private String mFileName;

    FontType(String fileName) {
        mFileName = fileName;
    }

    public String getFileName() {
        return mFileName;
    }
}
