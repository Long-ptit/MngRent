package com.restaurant.exam.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by BeThuc on 1/14/17.
 */

public class KeyboardUtils {

    public static void hideKeyboard(Activity activity) {
        if (null != activity) {
            View view = activity.getCurrentFocus();
            if (view != null) {
                InputMethodManager inputManager =
                        (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }


    public static void hideKeyboard(View view, Context context) {
        if (null != context) {
            InputMethodManager inputManager =
                    (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void showKeyboard(Activity activity, View view) {
        if (null != view) {
            view.requestFocus();
            view.postDelayed(() -> {
                InputMethodManager imm =
                        (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
            }, 200);
        }
    }

    public static void showSoftKeyboard(Activity activity) {
        /* show keyboard */
        ((InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE)).toggleSoftInput(
                0,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }
}