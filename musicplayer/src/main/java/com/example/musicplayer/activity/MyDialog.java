package com.example.musicplayer.activity;

import android.app.Dialog;
import android.content.Context;
import android.view.*;
import com.example.musicplayer.util.ScreenSizeUtils;

/**
 * @author 章可政
 * @date 2020/10/19 20:03
 */
public class MyDialog extends Dialog {
    public MyDialog(View view, Context context, int themeResId) {
        super(context, themeResId);
        init(view, context);
    }

    void init(View view, Context context) {
        setContentView(view);
        setCanceledOnTouchOutside(true);
        view.setMinimumHeight((int) (ScreenSizeUtils.getInstance(context).getScreenHeight() * 0.5f));
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = ScreenSizeUtils.getInstance(context).getScreenWidth();
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        dialogWindow.setAttributes(lp);
    }
}
