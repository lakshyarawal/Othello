package com.example.lakshya.othello;

import android.content.Context;
import android.widget.Button;

/**
 * Created by LAKSHYA on 6/19/2017.
 */

public class MyButton extends Button {
    int a,b;
    boolean chip;
    String color;
    public MyButton(Context context) {

        super(context);
        chip = false;
        color = "noColor";
    }

}
