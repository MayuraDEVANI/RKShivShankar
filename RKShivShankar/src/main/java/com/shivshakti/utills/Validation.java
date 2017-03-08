package com.shivshakti.utills;

import android.annotation.SuppressLint;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.EditText;

public class Validation {

    /*
    For mobile Numbers
     */
    public static InputFilter filter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            String blockCharacterSet = "~#^|$%&*!";
            if (source != null && blockCharacterSet.contains(("" + source))) {
                return "";
            }
            return null;
        }
    };

    /*
     * This methjo is to check if entered text in Edittext is Empty or not
     */
    public static boolean isEmptyEdittext(EditText edt) {
        if (edt.getText().toString().trim().equals("")) {
            return true;
        }
        return false;
    }

    /*
     * Email Validation
     */
    @SuppressLint("NewApi")
    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

}
