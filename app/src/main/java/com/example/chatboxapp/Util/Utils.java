package com.example.chatboxapp.Util;

import android.content.Context;
import android.widget.Toast;

public class Utils {

    public static void ShowToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
